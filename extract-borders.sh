#!/usr/bin/env bash
set -euo pipefail

INPUT="countries.json"
OUTPUT="borders.json"

if [[ ! -f "$INPUT" ]]; then
    echo "Usage: $0"
    echo "Expects '$INPUT' in the current directory; writes '$OUTPUT'."
    exit 1
fi

jq '[.[] | {cca3, borders}]' "$INPUT" > "$OUTPUT"
