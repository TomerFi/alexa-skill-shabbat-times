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

public final class CancelIntentTest
{
    @Test
    @DisplayName("customer says 'cancel'")
    public void testCancelIntent() throws BeansException, IllegalAccessException, InstantiationException, IOException, URISyntaxException
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DiMockAPIConfiguration.class);
        val skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();

        val input = Files.readAllBytes(Paths.get(Thread.currentThread().getContextClassLoader()
                .getResource("skill-tests/cancel_intent.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        assertThat(response)
            .isPresent()
            .sessionIsOver()
            .cardIsAbsent()
            .sessionAttributesAreAbsent()
            .repromptIsAbsent()
            .outputSpeechIs("Ok.");
    }
}
