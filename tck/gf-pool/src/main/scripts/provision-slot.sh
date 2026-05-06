#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Provisions and starts a single GlassFish slot in the pool.
#
# Inputs (env):
#   SLOT_IDX     - integer slot index
#   POOL_DIR     - pool root directory (.gf-pool)
#   SOURCE_HOME  - GlassFish install used as the clone template (must contain
#                  glassfish/bin/asadmin); typically the unpacked zip dist or
#                  a user-supplied -Dglassfish.home
#   ADMIN_BASE   - base admin port (e.g. 14848)
#   PORT_STRIDE  - per-slot port stride (e.g. 10000)
#
# Idempotent: if the domain is already running on the slot's admin port the
# function returns early.

set -euo pipefail

slot_dir="${POOL_DIR}/slot-${SLOT_IDX}"
slot_home="${slot_dir}/glassfish9"
asadmin="${slot_home}/glassfish/bin/asadmin"
domain_xml="${slot_home}/glassfish/domains/domain1/config/domain.xml"

admin_port=$((ADMIN_BASE + (SLOT_IDX - 1) * PORT_STRIDE))
http_port=$((admin_port + 1))
https_port=$((admin_port + 2))
iiop_port=$((admin_port + 3))
iiop_ssl_port=$((admin_port + 4))
iiop_mutual_port=$((admin_port + 5))
jmx_port=$((admin_port + 6))
jms_port=$((admin_port + 7))
osgi_port=$((admin_port + 8))
derby_port=$((admin_port + 9))

# Already running? (TCP probe on admin port)
if [[ -x "${asadmin}" ]] && (echo > "/dev/tcp/127.0.0.1/${admin_port}") 2>/dev/null; then
    echo "[gf-pool] slot ${SLOT_IDX} already running on admin=${admin_port} (skip)"
    exit 0
fi

echo "[gf-pool] provisioning slot ${SLOT_IDX} from ${SOURCE_HOME} (admin=${admin_port} http=${http_port})"

rm -rf "${slot_dir}"
mkdir -p "${slot_dir}"

# Bootstrap-in-flight sentinel. SlotLeaserAgent reads this to know a
# provision is still running and waits for it (rather than racing tryGrow
# and double-provisioning the same slot index). The EXIT trap removes the
# pid on any exit — success, error, or signal — so a stale pid never
# survives this script (modulo SIGKILL, which is rare in practice).
echo "$$" > "${slot_dir}/.bootstrap.pid"
trap 'rm -f "${slot_dir}/.bootstrap.pid"' EXIT

# Hardlink-clone the entire install (~free, sub-second on local fs).
cp -al "${SOURCE_HOME}" "${slot_dir}/glassfish9"

# Replace domains/ with a real copy because port-patch rewrites domain.xml in
# place; a hardlink would propagate edits back to the source install and
# corrupt sibling slots / the user-supplied install.
rm -rf "${slot_home}/glassfish/domains"
cp -a "${SOURCE_HOME}/glassfish/domains" "${slot_home}/glassfish/"

# Discard inherited deployments / generated artefacts / logs from the source
# install so each slot starts from a known-empty domain state.
rm -rf "${slot_home}/glassfish/domains/domain1/applications"/* \
       "${slot_home}/glassfish/domains/domain1/generated"/* \
       "${slot_home}/glassfish/domains/domain1/logs"/* 2>/dev/null || true

patch_pair() {
    local match="$1"
    local key="$2"
    local val="$3"
    sed -i -E \
        -e "s/${key}=\"[0-9]+\"([^/]*${match})/${key}=\"${val}\"\\1/g" \
        -e "s/(${match}[^/]*)${key}=\"[0-9]+\"/\\1${key}=\"${val}\"/g" \
        "${domain_xml}"
}

# Inject JVM options into every <java-config> block (server-config and
# default-config). Locale pinning matches what the legacy managed Arquillian
# container set via glassfish.systemProperties so tests that assert on locale-
# sensitive output (numbers, dates) stay stable across host environments.
sed -i 's|</java-config>|<jvm-options>-Duser.language=en</jvm-options><jvm-options>-Duser.country=US</jvm-options></java-config>|g' "${domain_xml}"

patch_pair 'name="admin-listener"'    'port'  "${admin_port}"
patch_pair 'name="http-listener-1"'   'port'  "${http_port}"
patch_pair 'name="http-listener-2"'   'port'  "${https_port}"
patch_pair 'id="orb-listener-1"'      'port'  "${iiop_port}"
patch_pair 'id="SSL"'                 'port'  "${iiop_ssl_port}"
patch_pair 'id="SSL_MUTUALAUTH"'      'port'  "${iiop_mutual_port}"
patch_pair 'name="system"'            'port'  "${jmx_port}"
patch_pair 'name="JMS_PROVIDER_PORT"' 'value' "${jms_port}"
patch_pair 'name="PortNumber"'        'value' "${derby_port}"
sed -i -E "s/-Dosgi\\.shell\\.telnet\\.port=[0-9]+/-Dosgi.shell.telnet.port=${osgi_port}/g" "${domain_xml}"

# Pre-create the lock file so the JVM shutdown hook can always FileChannel.open
# + tryLock it. Without this, builds that fail before any test JVM agent has
# run leave no lock file behind, the hook's existence check returns early,
# and the started GF gets orphaned.
: > "${slot_dir}/lock"

echo "[gf-pool] starting slot ${SLOT_IDX}"
"${asadmin}" start-domain domain1

# Write ports.properties LAST. SlotLeaserAgent uses its existence as the
# "slot is fully provisioned and the GF admin port should be listening"
# signal. Writing it earlier would let the agent observe a slot whose
# admin port is not yet up — the health probe would skip it, but only
# after wasting a poll cycle and possibly triggering a redundant tryGrow.
cat > "${slot_dir}/ports.properties" <<EOF
arquillian.adminPort=${admin_port}
arquillian.httpPort=${http_port}
arquillian.httpsPort=${https_port}
glassfish.home=${slot_home}
EOF
