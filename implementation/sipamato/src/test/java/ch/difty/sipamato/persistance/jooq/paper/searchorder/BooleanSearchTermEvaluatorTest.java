package ch.difty.sipamato.persistance.jooq.paper.searchorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.jooq.Condition;
import org.junit.Test;
import org.mockito.Mock;

import ch.difty.sipamato.entity.filter.BooleanSearchTerm;

public class BooleanSearchTermEvaluatorTest extends SearchTermEvaluatorTest<BooleanSearchTerm> {

    private final BooleanSearchTermEvaluator e = new BooleanSearchTermEvaluator();

    @Mock
    private BooleanSearchTerm stMock;

    @Override
    protected BooleanSearchTermEvaluator getEvaluator() {
        return e;
    }

    private void expectSearchTerm(boolean v) {
        when(stMock.getFieldName()).thenReturn("fieldX");
        when(stMock.getValue()).thenReturn(v);
    }

    @Test
    public void buildingCondition_whenTrue() {
        expectSearchTerm(true);
        Condition c = e.evaluate(stMock);
        assertThat(c.toString()).isEqualTo("fieldX = true");
    }

    @Test
    public void buildingCondition_whenFalse() {
        expectSearchTerm(false);
        Condition c = e.evaluate(stMock);
        assertThat(c.toString()).isEqualTo("fieldX = false");
    }
}
