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
docker run --rm -e RUN_LOCAL=true -e IGNORE_GITIGNORED_FILES=true -e IGNORE_GENERATED_FILES=true \
-e VALIDATE_EDITORCONFIG=true -e VALIDATE_GITHUB_ACTIONS=true -e VALIDATE_JAVA=true -e \
VALIDATE_JSON=true -e VALIDATE_MARKDOWN=true -e VALIDATE_XML=true -e VALIDATE_YAML=true \
-v ${PWD}:/tmp/lint ghcr.io/github/super-linter:slim-v4
```

## Metadata and infrastructure deployment

The ci workflows in this repository will handle the skill infrastructure part only, i.e. the lambda
function:

- [stage](workflows/stage.yml) will deploy the lambda function.
- [release](workflows/release.yml) will deploy the lambda function, publish it, and direct the
  *Live* alias to it.

If any changes were made to the skill metadata,</br>
we can use the UI or [ask cli][1] to deploy the metadata part manually.</br>
Here are some useful commands for the process:

```shell
ask deploy --ignore-hash --target skill-metadata

ask smapi submit-skill-validation --locales en-US,en-AU,en-CA,en-IN,en-GB --skill-id <skill-id>
ask smapi get-skill-validations --skill-id <skill-id> --validation-id <validation-id>

ask smapi submit-skill-for-certification --skill-id <skill-id>
ask smapi get-certification-review --skill-id <skill-id>
ask smapi withdraw-skill-from-certification --skill-id <skill-id>

```

> I tried incorporating the skill metadata part with the ci workflows,</br>
> unfortunately, it feels like *ask-cli* is not quite mature enough for automated work.</br>
> If anything changes, I would like to try this again.

