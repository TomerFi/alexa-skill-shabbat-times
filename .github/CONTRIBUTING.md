# Contributing to `alexa-skill-shabbat-times`

:clap: First off, thank you for taking the time to contribute. :clap:

Contributing is pretty straight-forward:

- Fork the repository
- Commit your changes
- Create a pull request against the `master` branch

Please feel free to contribute, even to this contributing guideline file, if you see fit.

- [Interaction model](#interaction-model)
  - [Intents](#intents)
  - [Slot types](#slot-types)
- [Adding a city to an existing country](#adding-a-city-to-an-existing-country)
  - [Adding a City backend](#adding-a-city-backend)
  - [Adding a City skill interface](#adding-a-city-skill-interface)
- [Adding a Locale for a new langauge support](#adding-a-locale-for-a-new-langauge-support)
  - [Adding a Locale backend](#adding-a-locale-backend)
  - [Adding a Locale skill interface](#adding-a-locale-skill-interface)
- [Adding a new country](#adding-a-new-country)
  - [Adding a Country backend](#adding-a-country-backend)
    - [Updating the text responses](#updating-the-text-responses)
    - [Adding a cities list](#adding-a-cities-list)
    - [Coding](#coding)
  - [Adding a Country skill interface](#adding-a-country-skill-interface)
- [Code of Conduct](#code-of-conduct)

## Interaction model

### Intents

- GetCityIntent - ask for the shabbat start and end time in a selected city. Uses the following slots:
  - LIST_OF_CITIES_IL
  - LIST_OF_CITIES_US
  - LIST_OF_CITIES_GB
  - LIST_OF_COUNTRIES
- CountrySelected - ask for a list of supported cities in a selected country. Uses the following slot:
  - LIST_OF_CITIES_COUNTRIES
- ThanksIntent - end the interaction with a positive tone. e.g. `no thank you`, instead of `no`.
- AMAZON.YesIntent - reply yes to the skills questions.
- AMAZON.NoIntent - reply no to the skills qeustions.
- AMAZON.HelpIntent - ask for help.
- AMAZON.StopIntent - ask to stop.
- AMAZON.CancelIntent - cancel interaction.

### Slot types

- LIST_OF_CITIES_IL - a list of cities and aliases supported in Israel.
- LIST_OF_CITIES_US - a list of cities and aliases supported in the United States.
- LIST_OF_CITIES_GB - a list of cities and aliases supported in the United Kingdom.
- LIST_OF_COUNTRIES - a list of countries and aliases supported.

## Adding a city to an existing country

### Adding a City backend

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

### Adding a City skill interface

Edit each and every [interaction model json file](models) (currently we only have one) you find the schema described [here](https://developer.amazon.com/docs/smapi/interaction-model-schema.html).</br>

Add the city name and all of its aliases to the appropriate [slot](#slot-types) so that Alexa can pick up the requested city name.

## Adding a Locale for a new langauge support

### Adding a Locale backend

Adding a locale to the skill is pretty strieght-forward and it requieres adding a `properties` file to be used as a [Resource Bundle](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html).</br>
Locale bundle files are stored in [src/main/resources/locales](src/main/resources/locales), copy an existing file, e.g. [Responses_en_US.properties](src/main/resources/locales/Responses_en_US.properties), edit the new file name with the chosen language and country abbreviations and update the values inside the new file.</br>

The bundle file is selected based on the locale property in the original request, so there's no point in adding a bundle file for a locale that is not supported by [Alexa](https://developer.amazon.com/docs/custom-skills/request-and-response-json-reference.html#request-locale).</br>

### Adding a Locale skill interface

For updating the skill interface with the new locale, add the appropriate json object to the `manifest.publishingInformation.locales` json array in [skill.json](skill.json).</br>

## Adding a new country

This is the only part in this document that requires some coding (temporarily) and editing, nothing too big though.</br>

### Adding a Country backend

#### Updating the text responses

Edit each and every [locale bundle file](src/main/resources/locales) (currently we only have one) and update the following keys:

- HELP_REPROMPT
- HELP_SPEECH
- EXC_NO_COUNTRY_PROVIDED

These are the keys holding information to be played to the customer regarding the supported countries with this skill, new countries should probably be added here.</br>

Look for the `NOT_FOUND_IN_US` key, which marks the prompt for playing to the customer when the requested city was not found for the requested country.</br>
Add a new key with appropriate ending abbreviation corresponding to the new country, and write down the new key, will use it shortly.</br>

#### Adding a cities list

Based on the previous section [Adding a city to an existing country](#adding-a-city-to-an-existing-country) add a new json array file storing the city objects in [src/main/resources/cities](src/main/resources/cities).

#### Coding

Please note, in the future, I plan to abstracting the country part of the code so no code modification will be required.

- Add the new locale key to the [BundleKeys enum class](src/main/java/info/tomfi/alexa/skills/shabbattimes/enums/BundleKeys.java).</br>
- Add a new member in the [CountryInfo enum class](src/main/java/info/tomfi/alexa/skills/shabbattimes/enums/CountryInfo.java) describing the new country name, abbreviation, bundle key, and aliases.
- Add a new member in the [Slots enum class](src/main/java/info/tomfi/alexa/skills/shabbattimes/enums/Slots.java), this of course should be the exact name of the slot that will be incorporated to the skill interface in the next section.

### Adding a Country skill interface

Edit each and every [interaction model json file](models) (currently we only have one) you find the schema described [here](https://developer.amazon.com/docs/smapi/interaction-model-schema.html).</br>

Add the country name and all of its aliases to the `LIST_OF_COUNTRIES` [slot](#slot-types) so that Alexa can pick up the requested country name.</br>
Add a new city [slot type](#slot-types) and fill it with supported cities and thier aliases.

## Code of Conduct

Please check the [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) document before contributing.
