# OpenPages Custom Action Template

## Deployment

```shell
oc patch sts ${OPENPAGES_STS_NAME} --patch-file ${PWD}/patch/openpages-patch.yaml
```

where:
- `OPENPAGES_STS_NAME` is the name of the stateful set in the namespace, e.g. `openpages-openpagesinstance-cr-sts`

## Version update

```shell
oc edit sts openpages-openpagesinstance-cr-sts
```

Update the version number for the `openpages-actions` image

## Logs

```shell
oc logs openpages-openpagesinstance-cr-sts-0
```

## Development

### Java8 environment

```shell
podman run --interactive --tty -v $PWD:/workspace openjdk:8 bash
```

```shell
cd /workspace
./gradlew jar
```

The container image is pushed to this location in Quay - https://quay.io/repository/ibm_ecosystem_engineering/customaction-helloworld

## Acknowledgements

Thanks to Randy Phoa for the provided roadmap - https://randyphoa.com/ai-governance-on-cloud-pak-for-data-with-openpages-custom-workflow-actions-555473c060c1