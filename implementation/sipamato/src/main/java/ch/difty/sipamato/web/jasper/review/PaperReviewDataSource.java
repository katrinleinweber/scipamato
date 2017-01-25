package ch.difty.sipamato.web.jasper.review;

import ch.difty.sipamato.entity.Paper;
import ch.difty.sipamato.entity.filter.PaperSlimFilter;
import ch.difty.sipamato.entity.projection.PaperSlim;
import ch.difty.sipamato.service.PaperService;
import ch.difty.sipamato.web.jasper.JasperPaperDataSource;
import ch.difty.sipamato.web.jasper.SipamatoPdfResourceHandler;
import ch.difty.sipamato.web.pages.paper.provider.SortablePaperSlimProvider;
import ch.difty.sipamato.web.resources.jasper.PaperReviewReportResourceReference;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.export.PdfExporterConfiguration;

/**
 * DataSource for the PaperReviewReport.
 *
 * The meta fields are not contained within a paper instance and make up e.g. localized labels, the brand or part of the header.
 * @author u.joss
 */
public class PaperReviewDataSource extends JasperPaperDataSource<PaperReview> {

    private static final String FILE_BASE_NAME = "paper_review";

    private static final long serialVersionUID = 1L;

    private String idLabel;
    private String authorYearLabel;
    private String locationLabel;
    private String methodStatisticsLabel;
    private String methodOutcomeLabel;
    private String exposurePollutantLabel;
    private String methodStudyDesignLabel;
    private String populationDurationLabel;
    private String populationParticipantsLabel;
    private String exposureAssessmentLabel;
    private String resultExposureRangeLabel;
    private String methodConfoundersLabel;
    private String resultEffectEstimateLabel;
    private String brand;
    private String createdBy;

    @Override
    protected JasperReport getReport() {
        return PaperReviewReportResourceReference.get().getReport();
    }

    @Override
    protected PaperReview makeEntity(final Paper p) {
        return new PaperReview(p, idLabel, authorYearLabel, locationLabel, methodOutcomeLabel, exposurePollutantLabel, methodStudyDesignLabel, populationDurationLabel, methodStatisticsLabel,
                populationParticipantsLabel, exposureAssessmentLabel, resultExposureRangeLabel, methodConfoundersLabel, resultEffectEstimateLabel, brand, createdBy);
    }

    /**
     * Using the dataProvider for the Result Panel as record source. Needs the {@link PaperService} to retrieve the papers
     * based on the ids of the {@link PaperSlim}s that are used in the dataProvider.
     */
    public PaperReviewDataSource(final SortablePaperSlimProvider<? extends PaperSlimFilter> dataProvider, final PaperService paperService, final String idLabel, final String authorYearLabel,
            final String locationLabel, final String methodOutcomeLabel, final String exposurePollutantLabel, final String methodStudyDesignLabel, final String populationDurationLabel,
            final String methodStatisticsLabel, final String populationParticipantsLabel, final String exposureAssessmentLabel, final String resultExposureRangeLabel,
            final String methodConfoundersLabel, final String resultEffectEstimateLabel, final String brand, final String createdBy, PdfExporterConfiguration config) {
        super(new SipamatoPdfResourceHandler(config), FILE_BASE_NAME, dataProvider, paperService);

        this.idLabel = idLabel;
        this.authorYearLabel = authorYearLabel;
        this.locationLabel = locationLabel;
        this.methodOutcomeLabel = methodOutcomeLabel;
        this.exposurePollutantLabel = exposurePollutantLabel;
        this.methodStudyDesignLabel = methodStudyDesignLabel;
        this.populationDurationLabel = populationDurationLabel;
        this.methodStatisticsLabel = methodStatisticsLabel;
        this.populationParticipantsLabel = populationParticipantsLabel;
        this.exposureAssessmentLabel = exposureAssessmentLabel;
        this.resultExposureRangeLabel = resultExposureRangeLabel;
        this.methodConfoundersLabel = methodConfoundersLabel;
        this.resultEffectEstimateLabel = resultEffectEstimateLabel;
        this.brand = brand;
        this.createdBy = createdBy;
    }

}
