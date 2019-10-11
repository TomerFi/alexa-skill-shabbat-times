package info.tomfi.alexa.skills.shabbattimes.api.response.items;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public final class ResponseItemAssert extends AbstractAssert<ResponseItemAssert, ResponseItem>
{
    public ResponseItemAssert(final ResponseItem actual)
    {
        super(actual, ResponseItemAssert.class);
    }

    public static ResponseItemAssert assertThat(final ResponseItem actual)
    {
        return new ResponseItemAssert(actual);
    }

    public ResponseItemAssert hebrewIs(final String testHebrew)
    {
        isNotNull();
        Assertions.assertThat(actual.getHebrew()).isEqualTo(testHebrew);
        return this;
    }

    public ResponseItemAssert dateIs(final String testDate)
    {
        isNotNull();
        Assertions.assertThat(actual.getDate()).isEqualTo(testDate);
        return this;
    }

    public ResponseItemAssert titleIs(final String testTitle)
    {
        isNotNull();
        Assertions.assertThat(actual.getTitle()).isEqualTo(testTitle);
        return this;
    }

    public ResponseItemAssert categoryIs(final String testCategory)
    {
        isNotNull();
        Assertions.assertThat(actual.getCategory()).isEqualTo(testCategory);
        return this;
    }

    public ResponseItemAssert linkIs(final String testLink)
    {
        isNotNull();
        Assertions.assertThat(actual.getLink().get()).isEqualTo(testLink);
        return this;
    }

    public ResponseItemAssert linkIsEmpty()
    {
        isNotNull();
        Assertions.assertThat(actual.getLink().isPresent()).isFalse();
        return this;
    }

    public ResponseItemAssert memoIs(final String testMemo)
    {
        isNotNull();
        Assertions.assertThat(actual.getMemo().get()).isEqualTo(testMemo);
        return this;
    }

    public ResponseItemAssert memoIsEmpty()
    {
        isNotNull();
        Assertions.assertThat(actual.getMemo().isPresent()).isFalse();
        return this;
    }

    public ResponseItemAssert subcatIs(final String testSubcat)
    {
        isNotNull();
        Assertions.assertThat(actual.getSubcat().get()).isEqualTo(testSubcat);
        return this;
    }

    public ResponseItemAssert subcatIsEmpty()
    {
        isNotNull();
        Assertions.assertThat(actual.getSubcat().isPresent()).isFalse();
        return this;
    }

    public ResponseItemAssert isYomtov()
    {
        isNotNull();
        Assertions.assertThat(actual.isYomtov()).isTrue();
        return this;
    }

    public ResponseItemAssert isNotYomtov()
    {
        isNotNull();
        Assertions.assertThat(actual.isYomtov()).isFalse();
        return this;
    }
}
