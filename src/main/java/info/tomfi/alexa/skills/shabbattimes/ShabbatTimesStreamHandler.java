package info.tomfi.alexa.skills.shabbattimes;

import com.amazon.ask.SkillStreamHandler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import info.tomfi.alexa.skills.shabbattimes.tools.DIConfiguration;
import lombok.Cleanup;
import lombok.var;

public class ShabbatTimesStreamHandler extends SkillStreamHandler
{
    public static ShabbatTimesSkillCreator getCreator()
    {
        @Cleanup var context = new AnnotationConfigApplicationContext(DIConfiguration.class);
        return context.getBean(ShabbatTimesSkillCreator.class);
    }

    public ShabbatTimesStreamHandler() throws IllegalAccessException, InstantiationException
    {
        super(getCreator().getSkill());
    }
}
