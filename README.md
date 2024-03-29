# Confidence Index - Custom OpenPages Actions

## Deployment

```shell
oc cp test-custom-action.jar ${NS}/${OPENPAGES_POD_NAME}:/opt/ibm/OpenPages/aurora/op-ext-lib/test-custom-action.jar
```

where:
- `NS` is the namespace where OpenPages has been deployed
- `OPENPAGES_POD_NAME` is the name of the pod in the namespace, e.g. `openpages-openpages-1-sts-0`

## Development

### Java8 environment

```shell
podman run --interactive --tty -v $PWD:/workspace openjdk:8 bash
```

```shell
cd /workspace
./gradlew jar
```
