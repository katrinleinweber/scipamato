package ch.difty.scipamato.persistence.paper.searchorder;

import static ch.difty.scipamato.TestUtils.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ch.difty.scipamato.entity.filter.SearchTerm;

@RunWith(MockitoJUnitRunner.class)
public abstract class SearchTermEvaluatorTest<T extends SearchTerm> {

    // needs to match the newlines used in JOOQ Condition.toString()
    private static final String NL = "\n";

    protected abstract SearchTermEvaluator<T> getEvaluator();

    @Test
    public void evaluating_withNullParameter_throws() {
        assertDegenerateSupplierParameter(() -> getEvaluator().evaluate(null), "searchTerm");
    }

    protected String concat(String... strings) {
        final StringBuilder sb = new StringBuilder();
        for (final String s : strings) {
            sb.append(s).append(NL);
        }
        if (sb.length() > NL.length()) {
            return sb.substring(0, sb.length() - NL.length());
        } else {
            return sb.toString();
        }
    }
}
