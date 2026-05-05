#!/usr/bin/env bash
#
# Copyright (c) 2026 Contributors to Eclipse Foundation.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#
# Stops every running GlassFish pool slot.

set -euo pipefail

cd "$(dirname "$0")"
exec mvn -pl gf-pool -am antrun:run@stop "$@"
