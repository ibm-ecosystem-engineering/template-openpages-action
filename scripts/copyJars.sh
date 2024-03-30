#!/bin/sh

if [ -n "${1}" ]; then
  TARGET="${1}"
fi

if [ -z "${TARGET}" ]; then
  echo "TARGET must be set"
  exit 1
fi

mkdir -p "${TARGET}"

cp /op-ext-lib/* "${TARGET}"
