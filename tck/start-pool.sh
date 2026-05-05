#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Provisions and starts the GlassFish pool independently of a full Maven build.
# Pass through any -D options accepted by the gf-pool module, e.g.
#   ./start-pool.sh -Dgf.pool.size=8
#   ./start-pool.sh -Dglassfish.home=/path/to/glassfish9

set -euo pipefail

cd "$(dirname "$0")"
exec mvn -pl gf-pool -am process-classes "$@"
