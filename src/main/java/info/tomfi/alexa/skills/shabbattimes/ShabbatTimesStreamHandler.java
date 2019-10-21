package info.tomfi.alexa.skills.shabbattimes;

import com.amazon.ask.SkillStreamHandler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.tomfi.alexa.skills.shabbattimes.di.DIProdConfiguration;
import lombok.Cleanup;
import lombok.val;

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
        @Cleanup val context = new AnnotationConfigApplicationContext(DIProdConfiguration.class);
        return context.getBean(ShabbatTimesSkillCreator.class);
    }

    /**
     * Main and only constructor, invokes the skill creation object and pass it to the super constructor.
     */
    public ShabbatTimesStreamHandler()
    {
        super(getCreator().getSkill());
    }
}
