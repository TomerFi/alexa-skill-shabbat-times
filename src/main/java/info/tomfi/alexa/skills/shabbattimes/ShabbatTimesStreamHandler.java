package info.tomfi.alexa.skills.shabbattimes;

import com.amazon.ask.SkillStreamHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import info.tomfi.alexa.skills.shabbattimes.tools.DIConfiguration;

public class ShabbatTimesStreamHandler extends SkillStreamHandler
{
    public static ShabbatTimesSkillCreator getCreator()
    {
        try
        (
            AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(DIConfiguration.class)
        )
        {
            return context.getBean(ShabbatTimesSkillCreator.class);
        }
    }

    public ShabbatTimesStreamHandler() throws IllegalAccessException, InstantiationException
    {
        super(getCreator().getSkill());
    }
}
