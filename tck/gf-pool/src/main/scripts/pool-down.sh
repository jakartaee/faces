#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Stops every slot found in the pool. Inputs: POOL_DIR.
# Slot count is self-discovered by globbing slot-* directories so it
# works regardless of how many were provisioned.

set -euo pipefail

script_dir="$(cd "$(dirname "$0")" && pwd)"

shopt -s nullglob
for slot_dir in "${POOL_DIR}"/slot-*; do
    idx="${slot_dir##*/slot-}"
    SLOT_IDX="${idx}" POOL_DIR="${POOL_DIR}" \
        bash "${script_dir}/stop-slot.sh"
done
