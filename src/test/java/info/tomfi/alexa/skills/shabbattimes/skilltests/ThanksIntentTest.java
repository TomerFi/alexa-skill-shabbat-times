package info.tomfi.alexa.skills.shabbattimes.skilltests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.amazon.ask.Skill;
import com.amazon.ask.request.impl.BaseSkillRequest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.tomfi.alexa.skills.shabbattimes.ShabbatTimesSkillCreator;
import info.tomfi.alexa.skills.shabbattimes.tools.DITestingConfiguration;
import lombok.Cleanup;
import lombok.val;

public final class ThanksIntentTest
{
    private static Skill skillInTest;

    @BeforeAll
    public static void initialize() throws BeansException, IllegalAccessException, InstantiationException
    {
        @Cleanup
        val context = new AnnotationConfigApplicationContext(DITestingConfiguration.class);
        skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
    }

    @Test
    @DisplayName("customer reply 'no thank you'")
    public void testThanksIntent() throws IOException, URISyntaxException
    {
        val input = Files.readAllBytes(Paths.get(ThanksIntentTest.class.getClassLoader()
                .getResource("skill-tests/thanks_intent.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        SkillResponseAssert.assertThat(response)
            .isPresent()
            .sessionIsOver()
            .sessionAttributesAreAbsent()
            .cardIsAbsent()
            .repromptIsAbsent()
            .outputSpeechIs("Happy to assist you. Have a nice day.");
    }
}
