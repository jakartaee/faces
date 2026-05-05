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
