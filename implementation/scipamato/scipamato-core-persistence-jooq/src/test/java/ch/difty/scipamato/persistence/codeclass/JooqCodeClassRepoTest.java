package ch.difty.scipamato.persistence.codeclass;

import static ch.difty.scipamato.TestUtils.*;

import org.jooq.DSLContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class JooqCodeClassRepoTest {

    @Mock
    private DSLContext dslContextMock;

    private JooqCodeClassRepo repo;

    @Before
    public void setUp() {
        repo = new JooqCodeClassRepo(dslContextMock);
    }

    @Test
    public void findingCodesOfClass_withNullLanguageId_throws() {
        assertDegenerateSupplierParameter(() -> repo.find(null), "languageCode");
    }
}
