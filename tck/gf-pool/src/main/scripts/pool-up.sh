#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Launches the initial set of slots' provisioners in the background and
# returns immediately, then writes ${POOL_DIR}/config.properties so
# SlotLeaserAgent can grow the pool on demand later. Inputs:
#   POOL_SIZE   - initial slot count (default 1)
#   POOL_DIR    - pool root (.gf-pool)
#   SOURCE_HOME - GlassFish install template (must contain glassfish/bin/asadmin)
#   ADMIN_BASE  - base admin port (e.g. 14848)
#   PORT_STRIDE - per-slot stride (e.g. 100)
#   SCRIPT_DIR  - directory containing provision-slot.sh (the agent uses
#                 this to grow the pool)
#
# Bootstrap is non-blocking by design: each provision-slot.sh runs in a
# detached subshell with stdout+stderr teed to ${POOL_DIR}/slot-N-bootstrap.log,
# so the antrun phase costs ~milliseconds instead of blocking on N parallel
# asadmin start-domain JVMs (~5-20s wall on -Tlarge). SlotLeaserAgent is
# bootstrap-aware via ${POOL_DIR}/slot-N/.bootstrap.pid: it waits for
# ports.properties (written last by provision-slot.sh on success) before
# leasing, and skips tryGrow while a bootstrap is still in flight.

set -euo pipefail

if [[ ! -x "${SOURCE_HOME}/glassfish/bin/asadmin" ]]; then
    echo "[gf-pool] SOURCE_HOME does not look like a GlassFish install: ${SOURCE_HOME}" >&2
    exit 1
fi

if [[ -z "${POOL_SIZE:-}" ]]; then
    POOL_SIZE=1
fi

mkdir -p "${POOL_DIR}"

# Write the pool config so the agent can read it when growing on demand.
# Overwrite each build so changes to source/ports flow through.
cat > "${POOL_DIR}/config.properties" <<EOF
source=${SOURCE_HOME}
adminBase=${ADMIN_BASE}
portStride=${PORT_STRIDE}
scriptDir=${SCRIPT_DIR}
EOF

echo "[gf-pool] launching ${POOL_SIZE} bootstrap(s) in background (grows on demand to match -T)"

for ((i = 1; i <= POOL_SIZE; i++)); do
    log="${POOL_DIR}/slot-${i}-bootstrap.log"
    SLOT_IDX="$i" \
    POOL_DIR="${POOL_DIR}" \
    SOURCE_HOME="${SOURCE_HOME}" \
    ADMIN_BASE="${ADMIN_BASE}" \
    PORT_STRIDE="${PORT_STRIDE}" \
        nohup bash "${SCRIPT_DIR}/provision-slot.sh" >"${log}" 2>&1 < /dev/null &
    disown
done

echo "[gf-pool] bootstrap launched (per-slot logs: ${POOL_DIR}/slot-N-bootstrap.log)"
