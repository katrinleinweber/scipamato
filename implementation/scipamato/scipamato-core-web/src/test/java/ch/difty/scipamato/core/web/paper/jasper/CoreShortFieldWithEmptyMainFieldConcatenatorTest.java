package ch.difty.scipamato.core.web.paper.jasper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;

import ch.difty.scipamato.common.TestUtils;
import ch.difty.scipamato.core.entity.Paper;

public class CoreShortFieldWithEmptyMainFieldConcatenatorTest {

    private final CoreShortFieldConcatenator sfc = new CoreShortFieldWithEmptyMainFieldConcatenator();

    private final Paper              p   = new Paper();
    private final ReportHeaderFields rhf = ReportHeaderFields
        .builder("hp", "b")
        .methodStudyDesignLabel("msdl")
        .methodOutcomeLabel("mol")
        .populationPlaceLabel("ppll")
        .exposurePollutantLabel("epl")
        .exposureAssessmentLabel("eal")
        .methodStatisticsLabel("msl")
        .methodConfoundersLabel("mcl")
        .populationDurationLabel("pdl")
        .populationParticipantsLabel("ppal")
        .resultEffectEstimateLabel("reel")
        .resultExposureRangeLabel("rerl")
        .resultMeasuredOutcomeLabel("rmol")
        .conclusionLabel("ccl")
        .build();

    @Test
    public void methods_withNullRecordset_throws() {
        TestUtils.assertDegenerateSupplierParameter(() -> sfc.methodsFrom(null, rhf), "paper");
    }

    @Test
    public void population_withNullRecordset_throws() {
        TestUtils.assertDegenerateSupplierParameter(() -> sfc.populationFrom(null, rhf), "paper");
    }

    @Test
    public void result_withNullRecordset_throws() {
        TestUtils.assertDegenerateSupplierParameter(() -> sfc.resultFrom(null, rhf), "paper");
    }

    @Test
    public void methods_withNonNullMethod_returnsMethod() {
        p.setMethods("method");
        p.setMethodStudyDesign("msd");
        p.setMethodOutcome("mo");
        p.setPopulationPlace("pp");
        p.setExposurePollutant("ep");
        p.setExposureAssessment("ea");
        p.setMethodStatistics("ms");
        p.setMethodConfounders("mc");

        assertThat(sfc.methodsFrom(p, rhf)).isEqualTo("method");
    }

    @Test
    public void methods_withNullMethod_returnsConcatenatedShortMethodFieldsConcatenated() {
        p.setMethods(null);
        p.setMethodStudyDesign("msd");
        p.setMethodOutcome("mo");
        p.setPopulationPlace("pp");
        p.setExposurePollutant("ep");
        p.setExposureAssessment("ea");
        p.setMethodStatistics("ms");
        p.setMethodConfounders("mc");

        assertThat(sfc.methodsFrom(p, rhf)).isEqualTo(
            "msdl: msd / mol: mo / ppll: pp / epl: ep / eal: ea / msl: ms / mcl: mc");
    }

    @Test
    public void population_withNonNullPopulation_returnsPopulation() {
        p.setPopulation("population");
        p.setPopulationPlace("ppl");
        p.setPopulationParticipants("ppa");
        p.setPopulationDuration("pd");

        assertThat(sfc.populationFrom(p, rhf)).isEqualTo("population");
    }

    @Test
    public void population_withNullPopulation_returnsPopulationShortFieldsConcatenated() {
        p.setPopulation(null);
        p.setPopulationPlace("ppl");
        p.setPopulationParticipants("ppa");
        p.setPopulationDuration("pd");

        assertThat(sfc.populationFrom(p, rhf)).isEqualTo("ppll: ppl / ppal: ppa / pdl: pd");
    }

    @Test
    public void result_withNonNullResult_returnsResult() {
        p.setResult("result");
        p.setResultExposureRange("rer");
        p.setResultEffectEstimate("ree");
        p.setResultMeasuredOutcome("rmo");
        p.setConclusion("cc");

        assertThat(sfc.resultFrom(p, rhf)).isEqualTo("result");
    }

    @Test
    public void result_withNullResult_returnsResultShortFieldsConcatenated() {
        p.setResult(null);
        p.setResultExposureRange("rer");
        p.setResultEffectEstimate("ree");
        p.setResultMeasuredOutcome("rmo");
        p.setConclusion("cc");

        assertThat(sfc.resultFrom(p, rhf)).isEqualTo("rerl: rer / reel: ree / rmol: rmo / ccl: cc");
    }

}