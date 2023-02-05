## How to push images to remote and local docker registry

```shell
# push all images of all modules
./gradlew jib
# push particular image of module (recipient module)
./gradlew :recipient:jib
# build all images to local registry
./gradlew jibDockerBuild
```