package info.tomfi.alexa.shabbattimes;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Main skill handler starting point extending SkillStreamHandler. */
public final class SkillHandler extends SkillStreamHandler {
  /**
   * Start the application context and retrieve the Skill from the context.
   *
   * @return the Skill instance.
   */
  private static Skill getSkill() {
    try (var context = new AnnotationConfigApplicationContext(SkillConfig.class)) {
      return context.getBean(Skill.class);
    }
  }

  public SkillHandler() {
    super(getSkill());
  }
}
