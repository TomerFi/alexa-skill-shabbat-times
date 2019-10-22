/**
 * Copyright 2019 Tomer Figenblat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.tomfi.alexa.skills.shabbattimes.assertions;

import java.util.Optional;

import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.model.ui.Card;
import com.amazon.ask.model.ui.OutputSpeech;
import com.amazon.ask.model.ui.PlainTextOutputSpeech;
import com.amazon.ask.model.ui.SimpleCard;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import com.amazon.ask.model.ui.StandardCard;
import com.amazon.ask.response.SkillResponse;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import lombok.val;

public final class SkillResponseAssert extends AbstractAssert<SkillResponseAssert, SkillResponse<ResponseEnvelope>>
{
    protected SkillResponseAssert(final SkillResponse<ResponseEnvelope> actual)
    {
        super(actual, SkillResponseAssert.class);
    }

    public SkillResponseAssert isPresent()
    {
        isNotNull();
        Assertions.assertThat(actual.isPresent()).isTrue();
        return this;
    }

    public SkillResponseAssert outputSpeechIs(final String testSpeech)
    {
        isNotNull();
        val optText = getTextFromOutputSpeech(actual.getResponse().getResponse().getOutputSpeech());
        if (optText.isPresent())
        {
            Assertions.assertThat(optText.get()).isEqualTo(testSpeech);
        } else
        {
            Assertions.fail("Unknown text or speech object type.");
        }
        return this;
    }

    public SkillResponseAssert outputSpeechStartsWith(final String testSpeech)
    {
        isNotNull();
        val optText = getTextFromOutputSpeech(actual.getResponse().getResponse().getOutputSpeech());
        if (optText.isPresent())
        {
            Assertions.assertThat(optText.get()).startsWith(testSpeech);
        } else
        {
            Assertions.fail("Unknown text or speech object type.");
        }
        return this;
    }

    public SkillResponseAssert outputSpeechEndsWith(final String testSpeech)
    {
        isNotNull();
        val optText = getTextFromOutputSpeech(actual.getResponse().getResponse().getOutputSpeech());
        if (optText.isPresent())
        {
            Assertions.assertThat(optText.get()).endsWith(testSpeech);
        } else
        {
            Assertions.fail("Unknown text or speech object type.");
        }
        return this;
    }

    public SkillResponseAssert repromptSpeechIs(final String testSpeech)
    {
        isNotNull();
        val repromptObject = actual.getResponse().getResponse().getReprompt();
        Assertions.assertThat(repromptObject).isNotNull();
        val optText = getTextFromOutputSpeech(repromptObject.getOutputSpeech());
        if (optText.isPresent())
        {
            Assertions.assertThat(optText.get()).isEqualTo(testSpeech);
        } else
        {
            Assertions.fail("Unknown text or speech object type.");
        }
        return this;
    }

    public SkillResponseAssert sessionIsOver()
    {
        isNotNull();
        Assertions.assertThat(actual.getResponse().getResponse().getShouldEndSession()).isTrue();
        return this;
    }

    public SkillResponseAssert sessionIsStillOn()
    {
        isNotNull();
        Assertions.assertThat(actual.getResponse().getResponse().getShouldEndSession()).isFalse();
        return this;
    }

    public SkillResponseAssert responseIsAbsent()
    {
        isNotNull();
        Assertions.assertThat(actual.getResponse().getResponse()).isNull();
        return this;
    }

    public SkillResponseAssert repromptIsAbsent()
    {
        isNotNull();
        Assertions.assertThat(actual.getResponse().getResponse().getReprompt()).isNull();
        return this;
    }

    public SkillResponseAssert cardIsAbsent()
    {
        isNotNull();
        Assertions.assertThat(actual.getResponse().getResponse().getCard()).isNull();
        return this;
    }

    public SkillResponseAssert cardTitleIs(final String testTitle)
    {
        isNotNull();
        val card = actual.getResponse().getResponse().getCard();
        Assertions.assertThat(card).isNotNull();
        val optTitle = getCardTitle(card);
        if (optTitle.isPresent())
        {
            Assertions.assertThat(optTitle.get()).isEqualTo(testTitle);
        }
        else
        {
            Assertions.fail("Unknown title or card object type.");
        }
        return this;
    }

    public SkillResponseAssert cardTextIs(final String testText)
    {
        isNotNull();
        val card = actual.getResponse().getResponse().getCard();
        Assertions.assertThat(card).isNotNull();
        val optText = getCardText(card);
        if (optText.isPresent())
        {
            Assertions.assertThat(optText.get()).isEqualTo(testText);
        }
        else
        {
            Assertions.fail("Unknown text or card object type.");
        }
        return this;
    }

    public SkillResponseAssert sessionAttributesHasKeyWithValue(final String key, final String value)
    {
        isNotNull();
        val attribs = actual.getResponse().getSessionAttributes();
        Assertions.assertThat(attribs).isNotEmpty();
        Assertions.assertThat(attribs).containsKey(key);
        Assertions.assertThat(attribs.get(key)).isEqualTo(value);
        return this;
    }

    public SkillResponseAssert sessionAttributesAreAbsent()
    {
        isNotNull();
        Assertions.assertThat(actual.getResponse().getSessionAttributes()).isEmpty();
        return this;
    }

    private Optional<String> getTextFromOutputSpeech(final OutputSpeech speechObject)
    {
        String retText = null;
        if (speechObject instanceof SsmlOutputSpeech)
        {
            retText = ((SsmlOutputSpeech) speechObject).getSsml().replaceAll("\\<.*?\\>", "");
        }
        if (speechObject instanceof PlainTextOutputSpeech)
        {
            retText = ((PlainTextOutputSpeech) speechObject).getText();
        }
        return Optional.ofNullable(retText);
    }

    private Optional<String> getCardTitle(final Card responseCard)
    {
        String retText = null;
        if (responseCard instanceof SimpleCard)
        {
            retText = ((SimpleCard) responseCard).getTitle();
        }
        else if (responseCard instanceof StandardCard)
        {
            retText = ((StandardCard) responseCard).getTitle();
        }
        return Optional.ofNullable(retText);
    }

    private Optional<String> getCardText(final Card responseCard)
    {
        String retText = null;
        if (responseCard instanceof SimpleCard)
        {
            retText = ((SimpleCard) responseCard).getContent();
        }
        else if (responseCard instanceof StandardCard)
        {
            retText = ((StandardCard) responseCard).getText();
        }
        return Optional.ofNullable(retText);
    }
}
