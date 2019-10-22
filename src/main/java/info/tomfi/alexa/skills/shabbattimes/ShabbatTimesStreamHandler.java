package info.tomfi.alexa.skills.shabbattimes;

import com.amazon.ask.SkillStreamHandler;

import info.tomfi.alexa.skills.shabbattimes.di.DiProdConfiguration;

import lombok.Cleanup;
import lombok.val;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main enterance class to be invoked as the aws lambda function.
 *
 * Spring context object is created here.
 *
 * @author Tomer Figenblat {@literal <tomer.figenblat@gmail.com>}
 */
public class ShabbatTimesStreamHandler extends SkillStreamHandler
{
    private static ShabbatTimesSkillCreator getCreator()
    {
        @Cleanup val context = new AnnotationConfigApplicationContext(DiProdConfiguration.class);
        return context.getBean(ShabbatTimesSkillCreator.class);
    }

    /**
     * Main constructor, invokes the skill creation and pass the skill to the super constructor.
     */
    public ShabbatTimesStreamHandler()
    {
        super(getCreator().getSkill());
    }
}
