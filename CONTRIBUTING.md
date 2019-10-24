# Contributing to `alexa-skill-shabbat-times`

:clap: First off, thank you for taking the time to contribute. :clap:

Contributing is pretty straight-forward:

- Fork the repository
- Commit your changes
- Create a pull request against the `master` branch

Please feel free to contribute, even to this contributing guideline file, if you see fit.

## Adding a city to an existing country

Cities can be added to the backend json files in [src/main/resources/cities](src/main/resources/cities) folder.</br>
The naming convention of the json files is `[Country-Abbreviation]_Cities.json`.</br>
e.g. `US_Cities.json`.</br>
For Convenience, a list of the possible country abbreviation with Alexa, can be found [here](https://developer.amazon.com/docs/custom-skills/request-and-response-json-reference.html#request-locale).</br>

As of the time of the writing of this document, this skill supports:

- Israel (IL)
- The United States (US)
- Great Britain (GB)

Each file is a json array which is basically contains a list of supported cities for a specific country by abbreviation. Each object in that array looks like this:

``` json
    {
        "cityName": "new york",
        "geoName": "US-New York-NY",
        "geoId" : "5128581",
        "countryAbbreviation": "US",
        "aliases": ["the big apple"]
    }
```

The object keys are pretty straight-forward.</br>
I just want to note the `aliases` key, this is just a list of aliases that could be left empty if needed.</br>
For this example, either `new york` or `the big apple` can be called to retrieve New York city's Shabbat Times.

## Adding a locale

### Backend

Adding a locale to the skill is pretty strieght-forward and it requieres adding a `properties` file to be used as a [Resource Bundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html).</br>
Locale bundle files are stored in [src/main/resources/locales](src/main/resources/locales), copy an existing file, e.g. [Responses_en_US.properties](src/main/resources/locales/Responses_en_US.properties), edit the new file name with the chosen language and country abbreviations and update the values inside the new file.</br>

The bundle file is selected based on the locale property in the original request, so there's no point in adding a bundle file for a locale that is not supported by [Alexa](https://developer.amazon.com/docs/custom-skills/request-and-response-json-reference.html#request-locale).</br>

### Skill interface

For updating the skill interface with the new locale, add the appropriate json object to the `manifest.publishingInformation.locales` json array in [skill.json](skill.json).</br>

## Adding a new country

This is the only part in this document that requires some coding (temporarily) and editing, nothing too big though.</br>

### Updating the current text responses

Open **each and every [locale bundle file](src/main/resources/locales)** (currently we only have one) and update the following keys:

- HELP_REPROMPT
- HELP_SPEECH
- EXC_NO_COUNTRY_PROVIDED

These are the keys holding information to be played to the customer regarding the supported countries with this skill, new countries should probably be added here.</br>

Look for the `NOT_FOUND_IN_US` key, which marks the prompt for playing to the customer when the requested city was not found for the requested country.</br>
Add a new key with appropriate ending abbreviation corresponding to the new country, and write down the new key, will use it shortly.</br>

### Adding the cities list

Based on the previous section [Adding a city to an existing country](#adding-a-city-to-an-existing-country) add a new json array file storing the city objects in [src/main/resources/cities](src/main/resources/cities).

### Coding

Please note, in the future, I plan to abstracting the country part of the code so no code modification will be required.

- Add the new locale key to the [BundleKeys enum class](src/main/java/info/tomfi/alexa/skills/shabbattimes/enums/BundleKeys.java).</br>
- Add a new member in the [CountryInfo enum class](src/main/java/info/tomfi/alexa/skills/shabbattimes/enums/CountryInfo.java) describing the new country name, abbreviation, bundle key, and aliases.

## Code of Conduct

Please check the [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before contributing.
