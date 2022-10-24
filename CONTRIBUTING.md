# Contributing to `alexa-skill-shabbat-times`

:clap: First off, thank you for taking the time to contribute. :clap:

- Fork the repository
- Create a new branch on your fork
- Commit your changes
- Create a pull request against the `master` branch

> NOTE: This document (and the base code) requires some work, i.e. redesigning, I hope to find the time to update it soon.

## Metadata and infrastructure deployment

The ci workflows in this repository will handle the skill infrastructure part only, i.e. the lambda
function:

- [stage](https://github.com/TomerFi/alexa-skill-shabbat-times/blob/master/.github/workflows/release.yml) will deploy the lambda function.
- [release](https://github.com/TomerFi/alexa-skill-shabbat-times/blob/master/.github/workflows/stage.yml) will deploy the lambda function, publish it, and direct the
  *Live* alias to it.

If any changes were made to the skill metadata,</br>
we can use the UI or
[ask cli](https://developer.amazon.com/en-US/docs/alexa/smapi/ask-cli-command-reference.html)
to deploy the metadata part manually.</br>
Here are some useful commands for the process:

```shell
ask deploy --ignore-hash --target skill-metadata

ask smapi submit-skill-validation --locales en-US,en-AU,en-CA,en-IN,en-GB --skill-id <skill-id>
ask smapi get-skill-validations --skill-id <skill-id> --validation-id <validation-id>

ask smapi submit-skill-for-certification --skill-id <skill-id>
ask smapi get-certification-review --skill-id <skill-id>
ask smapi withdraw-skill-from-certification --skill-id <skill-id>

```
