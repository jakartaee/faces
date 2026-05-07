#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Stops every slot found in the pool. Inputs: POOL_DIR.
# Slot count is self-discovered by globbing slot-* directories so it
# works regardless of how many were provisioned. Stops run concurrently
# because per-slot asadmin stop-domain takes ~500 ms wall and the calls
# are independent. Each slot's output is buffered into a temp file and
# dumped as one block so concurrent lines don't interleave mid-line.

set -euo pipefail

script_dir="$(cd "$(dirname "$0")" && pwd)"

shopt -s nullglob
pids=()
outs=()
for slot_dir in "${POOL_DIR}"/slot-*; do
    idx="${slot_dir##*/slot-}"
    tmp=$(mktemp)
    outs+=("${tmp}")
    SLOT_IDX="${idx}" POOL_DIR="${POOL_DIR}" \
        bash "${script_dir}/stop-slot.sh" >"${tmp}" 2>&1 &
    pids+=($!)
done

for i in "${!pids[@]}"; do
    wait "${pids[$i]}" || true
    cat "${outs[$i]}"
    rm -f "${outs[$i]}"
done

# Belt-and-suspenders: asadmin stop-domain only works when the slot's admin
# port is still reachable. If a slot's domain JVM is half-dead (admin
# listener gone but PID still alive) or the launching Maven session was
# interrupted before the shutdown hook could fire, the JVM survives this
# loop and keeps its ports — which then collides with the next bootstrap.
# pkill anything whose --module-path points into this pool dir to make
# the cleanup unconditional.
if compgen -G "${POOL_DIR}/slot-*" > /dev/null; then
    pkill -f -- "module-path .*${POOL_DIR}/slot-" 2>/dev/null || true
fi
