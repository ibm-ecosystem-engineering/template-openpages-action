#!/usr/bin/env bash

SCRIPT_DIR=$(cd $(dirname "$0"); pwd -P)

# Test for jq and logged in oc cli

IMAGE_NAME=$(oc get sts openpages-op-wxgov-instance-sts -o json | jq -r '.spec.template.spec.containers[] | select(.name == "openpages-opapp").image')

echo "Replacing image in template: ${IMAGE_NAME}"

cat "${SCRIPT_DIR}/openpages-patch-template.yaml" | sed "s~OP_IMAGE~${IMAGE_NAME}~g" > "${SCRIPT_DIR}/openpages-patch.yaml"

