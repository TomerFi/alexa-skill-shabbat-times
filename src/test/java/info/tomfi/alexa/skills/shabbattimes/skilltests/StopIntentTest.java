package info.tomfi.alexa.skills.shabbattimes.skilltests;

import static info.tomfi.alexa.skills.shabbattimes.assertions.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.amazon.ask.request.impl.BaseSkillRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.di.DiMockAPIConfiguration;

import lombok.Cleanup;
import lombok.val;

public final class StopIntentTest
{
    @Test
    @DisplayName("customer say 'stop'")
    public void testStopIntent() throws BeansException, IllegalAccessException, InstantiationException, IOException, URISyntaxException
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DiMockAPIConfiguration.class);
        val skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();

        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
                .getResource("skill-tests/stop_intent.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .cardIsAbsent()
            .sessionAttributesHasKeyWithValue("lastIntent", "AMAZON.StopIntent")
            .outputSpeechIs("Please tell me the requested city name.")
            .repromptSpeechIs("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
    }
}
