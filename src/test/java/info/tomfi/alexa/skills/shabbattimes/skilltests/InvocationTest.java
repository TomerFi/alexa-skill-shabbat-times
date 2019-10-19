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

public final class InvocationTest {
    private static Skill skillInTest;

    @BeforeAll
    public static void initialize() throws BeansException, IllegalAccessException, InstantiationException {
        @Cleanup
        val context = new AnnotationConfigApplicationContext(DITestingConfiguration.class);
        skillInTest = context.getBean(ShabbatTimesSkillCreator.class).getSkill();
    }

    @Test
    @DisplayName("test simple invocation of the skill")
    public void test() throws IOException, URISyntaxException {
        val input = Files.readAllBytes(Paths.get(InvocationTest.class.getClassLoader()
                .getResource("skill-tests/invocation_simple.json").toURI())
        );
        val response = skillInTest.execute(new BaseSkillRequest(input));

        SkillResponseAssert.assertThat(response)
            .isPresent()
            .sessionIsStillOn()
            .sessionAttributesAreAbsent()
            .cardIsAbsent()
            .outputSpeechIs("Welcome to shabbat times! What is your city name?")
            .repromptSpeechIs("Please tell me the requested city name. For a list of all the possible city names, just ask me for help.");
    }
}
