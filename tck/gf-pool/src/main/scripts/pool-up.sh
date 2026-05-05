#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Provisions and starts the initial set of slots, then writes
# ${POOL_DIR}/config.properties so SlotLeaserAgent can grow the pool on
# demand later. Inputs:
#   POOL_SIZE   - initial slot count (default 1)
#   MAX_SIZE    - hard cap; empty = max(4, cores/2)
#   POOL_DIR    - pool root (.gf-pool)
#   SOURCE_HOME - GlassFish install template (must contain glassfish/bin/asadmin)
#   ADMIN_BASE  - base admin port (e.g. 14848)
#   PORT_STRIDE - per-slot stride (e.g. 100)
#   SCRIPT_DIR  - directory containing provision-slot.sh (the agent uses
#                 this to grow the pool)

set -euo pipefail

if [[ ! -x "${SOURCE_HOME}/glassfish/bin/asadmin" ]]; then
    echo "[gf-pool] SOURCE_HOME does not look like a GlassFish install: ${SOURCE_HOME}" >&2
    exit 1
fi

if [[ -z "${MAX_SIZE:-}" ]]; then
    cores="$(nproc 2>/dev/null || getconf _NPROCESSORS_ONLN 2>/dev/null || echo 4)"
    # Half-cores leaves headroom for the Maven master, surefire forks,
    # browsers, and the OS. Floor of 4 keeps low-core machines usable.
    floor=4
    MAX_SIZE=$(( cores / 2 ))
    if (( MAX_SIZE < floor )); then
        MAX_SIZE=${floor}
    fi
fi

if [[ -z "${POOL_SIZE:-}" ]]; then
    POOL_SIZE=1
fi

# If the user explicitly asked for more initial slots than the cap, they
# clearly want headroom for at least that many — raise the cap to match
# instead of clamping POOL_SIZE down. So `-Dgf.pool.size=16` alone is
# enough; no need to also pass -Dgf.pool.maxSize=16.
if (( POOL_SIZE > MAX_SIZE )); then
    MAX_SIZE="${POOL_SIZE}"
fi

mkdir -p "${POOL_DIR}"

# Write the pool config so the agent can read it when growing on demand.
# Overwrite each build so changes to source/ports flow through.
cat > "${POOL_DIR}/config.properties" <<EOF
source=${SOURCE_HOME}
adminBase=${ADMIN_BASE}
portStride=${PORT_STRIDE}
maxSize=${MAX_SIZE}
scriptDir=${SCRIPT_DIR}
EOF

echo "[gf-pool] initial=${POOL_SIZE} max=${MAX_SIZE} (cores: ${cores:-n/a})"

for ((i = 1; i <= POOL_SIZE; i++)); do
    SLOT_IDX="$i" \
    POOL_DIR="${POOL_DIR}" \
    SOURCE_HOME="${SOURCE_HOME}" \
    ADMIN_BASE="${ADMIN_BASE}" \
    PORT_STRIDE="${PORT_STRIDE}" \
        bash "${SCRIPT_DIR}/provision-slot.sh"
done
