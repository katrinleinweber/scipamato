package ch.difty.sipamato.persistance.jooq.paper;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ch.difty.sipamato.entity.Paper;
import ch.difty.sipamato.entity.PaperFilter;

@RunWith(MockitoJUnitRunner.class)
public class JooqPaperServiceTest {

    private final JooqPaperService service = new JooqPaperService();

    @Mock
    private PaperRepository repoMock;
    @Mock
    private PaperFilter filterMock;
    @Mock
    private Pageable pageableMock;
    @Mock
    private Page<Paper> paperPageMock;
    @Mock
    private Paper paperMock;

    private final List<Paper> papers = new ArrayList<>();

    @Before
    public void setUp() {
        service.setRepository(repoMock);

        papers.add(paperMock);
        papers.add(paperMock);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(repoMock, filterMock, pageableMock, paperPageMock, paperMock);
    }

    @Test
    public void findingByFilter_delegatesToRepo() {
        when(repoMock.findByFilter(filterMock, pageableMock)).thenReturn(paperPageMock);
        when(paperPageMock.getContent()).thenReturn(papers);

        assertThat(service.findByFilter(filterMock, pageableMock)).isEqualTo(papers);

        verify(repoMock).findByFilter(filterMock, pageableMock);
        verify(paperPageMock).getContent();
    }

    @Test
    public void countingByFilter_delegatesToRepo() {
        when(repoMock.countByFilter(filterMock)).thenReturn(3);
        assertThat(service.countByFilter(filterMock)).isEqualTo(3);
        verify(repoMock).countByFilter(filterMock);
    }

    @Test
    public void updating_delgatesToRepo() {
        when(repoMock.update(paperMock)).thenReturn(paperMock);
        assertThat(service.update(paperMock)).isEqualTo(paperMock);
        verify(repoMock).update(paperMock);
    }

}