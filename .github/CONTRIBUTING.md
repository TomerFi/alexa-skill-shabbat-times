# Contributing to `alexa-skill-shabbat-times`

:clap: First off, thank you for taking the time to contribute. :clap:

Contributing is pretty straight-forward:

- Fork the repository
- Commit your changes
- Create a pull request against the `master` branch

Please feel free to contribute, even to this contributing guideline file, if you see fit.

- [Run superlinter locally](#run-superlinter-locally)
- [Code of Conduct](#code-of-conduct)

## Run superlinter locally

```shell
docker run --rm -e RUN_LOCAL=true -e OUTPUT_FORMAT=tap -e OUTPUT_DETAILS=detailed `
-e LINTER_RULES_PATH=. -e VALIDATE_EDITORCONFIG=true -e VALIDATE_JAVA=true -e VALIDATE_JSON=true `
-e VALIDATE_MARKDOWN=true -e VALIDATE_XML=true -e VALIDATE_YAML=true `
-e FILTER_REGEX_EXCLUDE="(.git|.*.tap|/target/)" -v ${PWD}:/tmp/lint ghcr.io/github/super-linter:slim-v4
```

## Code of Conduct

Please check the [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) document before contributing.
