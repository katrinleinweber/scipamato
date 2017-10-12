package ch.difty.scipamato.web.pages.paper.provider;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.junit.Test;
import org.mockito.Mock;

import ch.difty.scipamato.entity.Paper;
import ch.difty.scipamato.entity.SearchOrder;
import ch.difty.scipamato.persistence.paging.PaginationContext;

public class PaperSlimBySearchOrderProviderTest extends AbstractPaperSlimProviderTest<SearchOrder, PaperSlimBySearchOrderProvider> {

    private static final String LC = "de";

    @Mock
    private SearchOrder searchOrder;

    @Override
    protected void localFixture() {
        when(serviceMock.findPageBySearchOrder(eq(searchOrder), isA(PaginationContext.class))).thenReturn(pageOfSlimPapers);
    }

    @Override
    protected SearchOrder getFilter() {
        return searchOrder;
    }

    @Override
    protected PaperSlimBySearchOrderProvider newProvider() {
        return new PaperSlimBySearchOrderProvider(getFilter(), PAGE_SIZE) {
            private static final long serialVersionUID = 1L;

            @Override
            protected String getLanguageCode() {
                return LC;
            }
        };
    }

    @Override
    protected void verifyFilterMock(PaginationContextMatcher matcher) {
        verify(serviceMock).findPageBySearchOrder(eq(searchOrder), argThat(matcher));
    }

    @Test
    public void constructingWithNewFilter_usesEmptyFilter() {
        PaperSlimBySearchOrderProvider p = new PaperSlimBySearchOrderProvider(null, 10);
        assertThat(p.getFilterState()).isEqualToComparingFieldByField(new SearchOrder());
    }

    @Test
    public void size() {
        int size = 5;
        when(serviceMock.countBySearchOrder(getFilter())).thenReturn(size);
        assertThat(provider.size()).isEqualTo(size);
        verify(serviceMock).countBySearchOrder(getFilter());
    }

    @Test
    public void settingFilterState() {
        SearchOrder searchOrder2 = mock(SearchOrder.class);
        assertThat(provider.getFilterState()).isEqualTo(searchOrder);
        provider.setFilterState(searchOrder2);
        assertThat(provider.getFilterState()).isEqualTo(searchOrder2);
    }

    @Test
    public void gettingAllPapersByFilter() {
        provider.setSort("authors", SortOrder.ASCENDING);
        when(paperServiceMock.findPageBySearchOrder(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "authors: ASC")), eq(LC))).thenReturn(pageOfPapers);
        List<Paper> papers = provider.findAllPapersByFilter();
        assertThat(papers).hasSize(5);
        assertThat(papers).containsOnly(paperMock);
        verify(paperServiceMock).findPageBySearchOrder(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "authors: ASC")), eq(LC));
    }

    @Test
    public void findingAllPaperIds() {
        final List<Long> ids = Arrays.asList(3l, 18l, 6l);
        provider.setSort("authors", SortOrder.ASCENDING);
        when(paperServiceMock.findPageOfIdsBySearchOrder(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "authors: ASC")))).thenReturn(ids);
        List<Long> papers = provider.findAllPaperIdsByFilter();
        assertThat(papers).isEqualTo(ids);
        verify(paperServiceMock).findPageOfIdsBySearchOrder(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "authors: ASC")));
    }

    @Test
    public void gettingSearchOrderId_passesModelId() {
        when(searchOrder.getId()).thenReturn(55l);
        assertThat(provider.getSearchOrderId()).isEqualTo(55l);
        verify(searchOrder).getId();
    }

    @Test
    public void isShowingExclusion_ifTrueInFilter_returnsTrue() {
        assertShowExcluded(true);
    }

    @Test
    public void isShowingExclusion_ifFalseInFilter_returnsFalse() {
        assertShowExcluded(false);
    }

    private void assertShowExcluded(boolean result) {
        when(searchOrder.isShowExcluded()).thenReturn(result);
        assertThat(provider.isShowExcluded()).isEqualTo(result);
        verify(searchOrder).isShowExcluded();
    }
}
