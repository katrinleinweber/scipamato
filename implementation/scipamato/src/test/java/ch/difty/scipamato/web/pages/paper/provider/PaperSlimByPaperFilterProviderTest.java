package ch.difty.scipamato.web.pages.paper.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ch.difty.scipamato.entity.Paper;
import ch.difty.scipamato.paging.PaginationContext;
import ch.difty.scipamato.persistance.jooq.paper.PaperFilter;

public class PaperSlimByPaperFilterProviderTest extends AbstractPaperSlimProviderTest<PaperFilter, PaperSlimByPaperFilterProvider> {

    @Mock
    private PaperFilter filterMock;

    @Override
    protected void localFixture() {
        when(serviceMock.findPageByFilter(eq(filterMock), isA(PaginationContext.class))).thenReturn(pageOfSlimPapers);
    }

    @Override
    protected PaperFilter getFilter() {
        return filterMock;
    }

    @Override
    protected PaperSlimByPaperFilterProvider newProvider() {
        return new PaperSlimByPaperFilterProvider(getFilter(), PAGE_SIZE);
    }

    @Override
    protected void verifyFilterMock(PaginationContextMatcher matcher) {
        verify(serviceMock).findPageByFilter(eq(filterMock), argThat(matcher));
    }

    @Test
    public void constructingWithNewFilter_usesEmptyFilter() {
        PaperSlimByPaperFilterProvider p = new PaperSlimByPaperFilterProvider(null, 10);
        assertThat(p.getFilterState()).isEqualToComparingFieldByField(new PaperFilter());
    }

    @Test
    public void size() {
        int size = 5;
        when(serviceMock.countByFilter(getFilter())).thenReturn(size);
        assertThat(provider.size()).isEqualTo(size);
        verify(serviceMock).countByFilter(getFilter());
    }

    @Test
    public void settingFilterState() {
        PaperFilter filterMock2 = Mockito.mock(PaperFilter.class);
        assertThat(provider.getFilterState()).isEqualTo(filterMock);
        provider.setFilterState(filterMock2);
        assertThat(provider.getFilterState()).isEqualTo(filterMock2);
    }

    @Test
    public void gettingAllPapersByFilter() {
        provider.setSort("title", SortOrder.DESCENDING);
        when(paperServiceMock.findPageByFilter(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "title: DESC")))).thenReturn(pageOfPapers);
        List<Paper> papers = provider.findAllPapersByFilter();
        assertThat(papers).hasSize(5);
        assertThat(papers).containsOnly(paperMock);
        verify(paperServiceMock).findPageByFilter(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "title: DESC")));
    }

    @Test
    public void findingAllIdsByFilter() {
        provider.setSort("title", SortOrder.DESCENDING);
        when(paperServiceMock.findPageOfIdsByFilter(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "title: DESC")))).thenReturn(Arrays.asList(5l, 3l, 17l));
        List<Long> ids = provider.findAllPaperIdsByFilter();
        assertThat(ids).containsExactly(5l, 3l, 17l);
        verify(paperServiceMock).findPageOfIdsByFilter(eq(getFilter()), argThat(new PaginationContextMatcher(0, Integer.MAX_VALUE, "title: DESC")));
    }

    // I know: The Liskov Substitution Principle ... :-(
    @Test
    public void gettingSearchOrderId_alwaysReturnsNull() {
        assertThat(provider.getSearchOrderId()).isNull();
    }

    // I know: The Liskov Substitution Principle ... :-(
    @Test
    public void gettingInvertExclusionFlag_alwaysReturnsFalse() {
        assertThat(provider.isShowExcluded()).isFalse();
        provider.setShowExcluded(true);
        assertThat(provider.isShowExcluded()).isFalse();
    }
}