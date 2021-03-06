package ch.difty.scipamato.core.sync.jobs.paper;

import static ch.difty.scipamato.core.db.Tables.PAPER;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.TableField;
import org.springframework.stereotype.Component;

import ch.difty.scipamato.common.paper.AbstractShortFieldConcatenator;
import ch.difty.scipamato.core.db.tables.Paper;
import ch.difty.scipamato.core.db.tables.records.PaperRecord;

/**
 * Gathers the content for the the fields methods, population and result.
 * <p>
 * There are some main fields (result, population and method) that could alternatively
 * be represented with a number of Short field (Kurzerfassung). If the main field is populated, it will always
 * have precedence, regardless of whether there's content in the respective short fields. If the main field is null,
 * all respective short fields with content are concatenated into the respective field in SciPaMaTo-Public. Note
 * that there's a known deficiency: The labels that are included with the short fields are always in english, they
 * will not adapt to the browser locale of a viewer.
 */
@Component
@Slf4j
class SyncShortFieldWithEmptyMainFieldConcatenator extends AbstractShortFieldConcatenator
    implements SyncShortFieldConcatenator {

    private static final String UNABLE_MSG = "Unable to evaluate recordset";

    SyncShortFieldWithEmptyMainFieldConcatenator() {
        super(false);
    }

    @Nullable
    @Override
    public String methodsFrom(@NotNull final ResultSet rs) {
        try {
            return methodsFrom(rs, PAPER.METHODS, PAPER.METHOD_STUDY_DESIGN, PAPER.METHOD_OUTCOME,
                PAPER.POPULATION_PLACE, PAPER.EXPOSURE_POLLUTANT, PAPER.EXPOSURE_ASSESSMENT, PAPER.METHOD_STATISTICS,
                PAPER.METHOD_CONFOUNDERS);
        } catch (SQLException se) {
            log.error(UNABLE_MSG, se);
            return null;
        }
    }

    // package-private for stubbing purposes
    @NotNull
    String methodsFrom(@NotNull final ResultSet rs, @NotNull final TableField<PaperRecord, String> methodField,
        @NotNull final TableField<PaperRecord, String> methodStudyDesignField,
        @NotNull final TableField<PaperRecord, String> methodOutcomeField,
        @NotNull final TableField<PaperRecord, String> populationPlaceField,
        @NotNull final TableField<PaperRecord, String> exposurePollutantField,
        @NotNull final TableField<PaperRecord, String> exposureAssessmentField,
        @NotNull final TableField<PaperRecord, String> methodStatisticsField,
        @NotNull final TableField<PaperRecord, String> methodConfoundersField) throws SQLException {
        return methodsFrom(rs.getString(methodField.getName()), rs.getString(methodStudyDesignField.getName()),
            rs.getString(methodOutcomeField.getName()), rs.getString(populationPlaceField.getName()),
            rs.getString(exposurePollutantField.getName()), rs.getString(exposureAssessmentField.getName()),
            rs.getString(methodStatisticsField.getName()), rs.getString(methodConfoundersField.getName()));
    }

    @Nullable
    @Override
    public String populationFrom(@NotNull final ResultSet rs) {
        try {
            return populationFrom(rs, PAPER.POPULATION, PAPER.POPULATION_PLACE, PAPER.POPULATION_PARTICIPANTS,
                PAPER.POPULATION_DURATION);
        } catch (SQLException se) {
            log.error(UNABLE_MSG, se);
            return null;
        }
    }

    // package-private for stubbing purposes
    @NotNull
    String populationFrom(@NotNull final ResultSet rs, @NotNull final TableField<PaperRecord, String> populationField,
        @NotNull final TableField<PaperRecord, String> populationPlaceField,
        @NotNull final TableField<PaperRecord, String> populationParticipantsField,
        @NotNull final TableField<PaperRecord, String> populationDurationField) throws SQLException {
        return populationFrom(rs.getString(populationField.getName()), rs.getString(populationPlaceField.getName()),
            rs.getString(populationParticipantsField.getName()), rs.getString(populationDurationField.getName()));
    }

    @Nullable
    @Override
    public String resultFrom(@NotNull final ResultSet rs) {
        try {
            return resultFrom(rs, Paper.PAPER.RESULT, Paper.PAPER.RESULT_MEASURED_OUTCOME,
                Paper.PAPER.RESULT_EXPOSURE_RANGE, Paper.PAPER.RESULT_EFFECT_ESTIMATE, Paper.PAPER.CONCLUSION);
        } catch (SQLException se) {
            log.error(UNABLE_MSG, se);
            return null;
        }
    }

    // package-private for stubbing purposes
    @NotNull
    String resultFrom(@NotNull final ResultSet rs, @NotNull final TableField<PaperRecord, String> resultField,
        @NotNull final TableField<PaperRecord, String> resultMeasuredOutcomeField,
        @NotNull final TableField<PaperRecord, String> resultExposureRangeField,
        @NotNull final TableField<PaperRecord, String> resultEffectEstimateField,
        @NotNull final TableField<PaperRecord, String> conclusionField) throws SQLException {
        return resultFrom(rs.getString(resultField.getName()), rs.getString(resultMeasuredOutcomeField.getName()),
            rs.getString(resultExposureRangeField.getName()), rs.getString(resultEffectEstimateField.getName()),
            rs.getString(conclusionField.getName()));
    }
}
