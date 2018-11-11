package ch.difty.scipamato.core.persistence.codeclass;

import static ch.difty.scipamato.core.entity.CodeClass.CodeClassFields.NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ch.difty.scipamato.common.persistence.paging.PaginationContext;
import ch.difty.scipamato.core.entity.CodeClass;
import ch.difty.scipamato.core.entity.code_class.CodeClassDefinition;
import ch.difty.scipamato.core.entity.code_class.CodeClassFilter;

@RunWith(MockitoJUnitRunner.class)
public class JooqCodeClassServiceTest {

    private JooqCodeClassService service;

    @Mock
    private CodeClassRepository repoMock;

    @Mock
    private CodeClassFilter filterMock;

    @Mock
    private PaginationContext paginationContextMock;

    @Mock
    private CodeClass codeMock;

    protected CodeClassRepository getRepo() {
        return repoMock;
    }

    protected CodeClass getEntity() {
        return codeMock;
    }

    private final List<CodeClassDefinition> codeClassDefinitions = new ArrayList<>();

    @Mock
    private CodeClassDefinition codeClassDefinitionMock, persistedCodeClassDefinitionMock;

    @Before
    public void setUp() {
        service = new JooqCodeClassService(repoMock);

        codeClassDefinitions.add(codeClassDefinitionMock);
        codeClassDefinitions.add(codeClassDefinitionMock);
    }

    @After
    public void specificTearDown() {
        verifyNoMoreInteractions(repoMock, filterMock, paginationContextMock, codeMock, codeClassDefinitionMock);
    }

    @Test
    public void findingCodes_delegatesToRepo() {
        String languageCodeClass = "de";

        List<CodeClass> ccs = new ArrayList<>();
        ccs.add(new CodeClass(1, "cc1", ""));
        ccs.add(new CodeClass(2, "cc2", ""));
        when(repoMock.find(languageCodeClass)).thenReturn(ccs);

        assertThat(extractProperty(NAME.getName()).from(service.find(languageCodeClass))).containsOnly("cc1", "cc2");

        verify(repoMock).find(languageCodeClass);

        verifyNoMoreInteractions(repoMock);
    }

    @Test
    public void newUnpersistedCodeClassDefinition_delegatesToRepo() {
        when(repoMock.newUnpersistedCodeClassDefinition()).thenReturn(codeClassDefinitionMock);
        assertThat(service.newUnpersistedCodeClassDefinition()).isEqualTo(codeClassDefinitionMock);
        verify(repoMock).newUnpersistedCodeClassDefinition();
    }

    @Test
    public void findingPageOfCodeClassDefinitions_delegatesToRepo() {
        when(repoMock.findPageOfCodeClassDefinitions(filterMock, paginationContextMock)).thenReturn(
            codeClassDefinitions);
        assertThat(service.findPageOfCodeClassDefinitions(filterMock, paginationContextMock)).isEqualTo(
            codeClassDefinitions);
        verify(repoMock).findPageOfCodeClassDefinitions(filterMock, paginationContextMock);
    }

    @Test
    public void gettingPageOfEntityDefinitions_delegatesToRepo() {
        when(repoMock.findPageOfCodeClassDefinitions(filterMock, paginationContextMock)).thenReturn(
            codeClassDefinitions);
        assertThat(service.findPageOfEntityDefinitions(filterMock, paginationContextMock)).hasSameElementsAs(
            codeClassDefinitions);
        verify(repoMock).findPageOfCodeClassDefinitions(filterMock, paginationContextMock);
    }

    @Test
    public void countingCodes_delegatesToRepo() {
        when(repoMock.countByFilter(filterMock)).thenReturn(3);
        assertThat(service.countByFilter(filterMock)).isEqualTo(3);
        verify(repoMock).countByFilter(filterMock);
    }

    @Test
    public void insertingCodeClassDefinition_delegatesToRepo() {
        when(repoMock.saveOrUpdate(codeClassDefinitionMock)).thenReturn(persistedCodeClassDefinitionMock);
        assertThat(service.saveOrUpdate(codeClassDefinitionMock)).isEqualTo(persistedCodeClassDefinitionMock);
        verify(repoMock).saveOrUpdate(codeClassDefinitionMock);
    }

    @Test
    public void deletingCodeClassDefinition_delegatesToRepo() {
        final Integer id = 1;
        final int version = 12;
        when(repoMock.delete(id, version)).thenReturn(persistedCodeClassDefinitionMock);
        assertThat(service.delete(id, version)).isEqualTo(persistedCodeClassDefinitionMock);
        verify(repoMock).delete(id, version);
    }

}
