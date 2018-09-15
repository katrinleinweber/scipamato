package ch.difty.scipamato.core.web.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.difty.scipamato.common.persistence.paging.PaginationContext;
import ch.difty.scipamato.core.ScipamatoCoreApplication;
import ch.difty.scipamato.core.entity.User;
import ch.difty.scipamato.core.entity.search.UserFilter;
import ch.difty.scipamato.core.persistence.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProviderTest {

    private UserProvider provider;

    @Mock
    private UserService serviceMock;

    @Mock
    private UserFilter filterMock;

    @Mock
    private User entityMock;

    @Autowired
    private ScipamatoCoreApplication application;

    private List<User> papers;

    @Before
    public void setUp() {
        new WicketTester(application);
        provider = new UserProvider(filterMock);
        provider.setService(serviceMock);

        papers = Arrays.asList(entityMock, entityMock, entityMock);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(serviceMock, entityMock);
    }

    @Test
    public void defaultFilterIsNewUserFilter() {
        provider = new UserProvider();
        assertThat(provider.getFilterState()).isEqualToComparingFieldByField(new UserFilter());
    }

    @Test
    public void nullFilterResultsInNewUserFilter() {
        UserProvider p = new UserProvider(null);
        assertThat(p.getFilterState()).isEqualToComparingFieldByField(new UserFilter());
    }

    @Test
    public void size() {
        int size = 5;
        when(serviceMock.countByFilter(filterMock)).thenReturn(size);
        assertThat(provider.size()).isEqualTo(size);
        verify(serviceMock).countByFilter(filterMock);
    }

    @Test
    public void gettingModel_wrapsEntity() {
        IModel<User> model = provider.model(entityMock);
        assertThat(model.getObject()).isEqualTo(entityMock);
    }

    @Test
    public void gettingFilterState_returnsFilter() {
        assertThat(provider.getFilterState()).isEqualTo(filterMock);
    }

    @Test
    public void settingFilterState() {
        provider = new UserProvider();
        assertThat(provider.getFilterState()).isNotEqualTo(filterMock);
        provider.setFilterState(filterMock);
        assertThat(provider.getFilterState()).isEqualTo(filterMock);
    }

    private class PaginationContextMatcher implements ArgumentMatcher<PaginationContext> {

        private final int    pageSize;
        private final String sort;

        @SuppressWarnings("SameParameterValue")
        PaginationContextMatcher(int pageSize, String sort) {
            this.pageSize = pageSize;
            this.sort = sort;
        }

        @Override
        public boolean matches(PaginationContext p) {
            return p != null && p.getPageSize() == pageSize && sort.equals(p
                .getSort()
                .toString());
        }
    }

    @Test
    public void iterating_withNoRecords_returnsNoRecords() {
        papers = Collections.emptyList();
        when(serviceMock.findPageByFilter(eq(filterMock), isA(PaginationContext.class))).thenReturn(papers);
        Iterator<User> it = provider.iterator(0, 3);
        assertThat(it.hasNext()).isFalse();
        verify(serviceMock).findPageByFilter(eq(filterMock),
            argThat(new UserProviderTest.PaginationContextMatcher(3, "userName: ASC")));
    }

    @Test
    public void iterating_throughFirst() {
        when(serviceMock.findPageByFilter(eq(filterMock), isA(PaginationContext.class))).thenReturn(papers);
        Iterator<User> it = provider.iterator(0, 3);
        assertRecordsIn(it);
        verify(serviceMock).findPageByFilter(eq(filterMock),
            argThat(new UserProviderTest.PaginationContextMatcher(3, "userName: ASC")));
    }

    private void assertRecordsIn(Iterator<User> it) {
        int i = 0;
        while (i++ < 3) {
            assertThat(it.hasNext()).isTrue();
            it.next();
        }
        assertThat(it.hasNext()).isFalse();
    }

    @Test
    public void iterating_throughSecondPage() {
        when(serviceMock.findPageByFilter(eq(filterMock), isA(PaginationContext.class))).thenReturn(papers);
        Iterator<User> it = provider.iterator(3, 3);
        assertRecordsIn(it);
        verify(serviceMock).findPageByFilter(eq(filterMock),
            argThat(new UserProviderTest.PaginationContextMatcher(3, "userName: ASC")));
    }

    @Test
    public void iterating_throughThirdPage() {
        provider.setSort("title", SortOrder.DESCENDING);
        when(serviceMock.findPageByFilter(eq(filterMock), isA(PaginationContext.class))).thenReturn(papers);
        Iterator<User> it = provider.iterator(6, 3);
        assertRecordsIn(it);
        verify(serviceMock).findPageByFilter(eq(filterMock),
            argThat(new UserProviderTest.PaginationContextMatcher(3, "title: DESC")));
    }

}