package ch.difty.scipamato.persistance.paper.slim;

import static ch.difty.scipamato.persistence.TestDbConstants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ch.difty.scipamato.entity.SearchOrder;
import ch.difty.scipamato.entity.filter.SearchCondition;
import ch.difty.scipamato.entity.projection.PaperSlim;
import ch.difty.scipamato.persistance.JooqTransactionalIntegrationTest;
import ch.difty.scipamato.persistence.paper.slim.JooqPaperSlimRepo;

public class JooqPaperSlimRepoIntegrationTest extends JooqTransactionalIntegrationTest {

    @Autowired
    private JooqPaperSlimRepo repo;

    @Test
    public void findingAll() {
        List<PaperSlim> papers = repo.findAll();
        assertThat(papers).hasSize(RECORD_COUNT_PREPOPULATED);
    }

    @Test
    public void findingById_withExistingId_returnsEntity() {
        PaperSlim paper = repo.findById((long) MAX_ID_PREPOPULATED);
        if (MAX_ID_PREPOPULATED > 0)
            assertThat(paper.getId()).isEqualTo(MAX_ID_PREPOPULATED);
        else
            assertThat(paper).isNull();
    }

    @Test
    public void findingById_withNonExistingId_returnsNull() {
        assertThat(repo.findById(-1l)).isNull();
    }

    @Test
    public void bug21_queryingAllFieldsShouldNotThrowAnException() {
        final SearchOrder so = new SearchOrder();
        final SearchCondition sc = new SearchCondition();
        so.add(sc);

        final String x = "";
        sc.setId("1");
        sc.setDoi(x);
        sc.setPmId(x);
        sc.setAuthors(x);
        sc.setFirstAuthor(x);
        sc.setFirstAuthorOverridden(true);
        sc.setTitle(x);
        sc.setLocation(x);
        sc.setPublicationYear("1984");

        sc.setGoals(x);
        sc.setPopulation(x);
        sc.setMethods(x);

        sc.setPopulationPlace(x);
        sc.setPopulationParticipants(x);
        sc.setPopulationDuration(x);
        sc.setExposurePollutant(x);
        sc.setExposureAssessment(x);
        sc.setMethodStudyDesign(x);
        sc.setMethodOutcome(x);
        sc.setMethodStatistics(x);
        sc.setMethodConfounders(x);

        sc.setResult(x);
        sc.setComment(x);
        sc.setIntern(x);

        sc.setResultExposureRange(x);
        sc.setResultEffectEstimate(x);

        sc.setOriginalAbstract(x);

        assertThat(repo.findBySearchOrder(so)).isEmpty();
    }

}
