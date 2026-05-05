#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Stops a single pool slot's GlassFish domain. Inputs:
#   SLOT_IDX, POOL_DIR
#
# This script does NOT check whether the slot is currently leased — that
# check happens in the caller. ShutdownHookInstaller iterates slots in
# Java and holds a FileLock on slot-N/lock for the duration of the call,
# which interoperates with the agent's lock (Java's FileLock and bash's
# flock(1) use different OS lock spaces on Linux, so the check has to be
# on the same side as the agent).

set -uo pipefail

asadmin="${POOL_DIR}/slot-${SLOT_IDX}/glassfish9/glassfish/bin/asadmin"
if [[ -x "${asadmin}" ]]; then
    echo "[gf-pool] stopping slot ${SLOT_IDX}"
    "${asadmin}" stop-domain domain1 || true
fi
