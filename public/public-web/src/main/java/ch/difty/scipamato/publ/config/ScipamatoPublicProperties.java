package ch.difty.scipamato.publ.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import ch.difty.scipamato.common.config.AbstractScipamatoProperties;
import ch.difty.scipamato.common.config.MavenProperties;

/**
 * This bean is used to evaluate all environment properties used in the
 * application in one place and serve those as bean to wherever they are used.
 *
 * @author u.joss
 * @see <a href=
 *     "https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-injecting-property-values-into-configuration-beans/">https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-injecting-property-values-into-configuration-beans/</a>
 */
@Component
public class ScipamatoPublicProperties extends AbstractScipamatoProperties<ScipamatoProperties>
    implements ApplicationPublicProperties {

    private static final long serialVersionUID = 1L;

    public ScipamatoPublicProperties(@NotNull ScipamatoProperties scipamatoProperties,
        @NotNull MavenProperties mavenProperties) {
        super(scipamatoProperties, mavenProperties);
    }

    @Override
    public boolean isCommercialFontPresent() {
        return getScipamatoProperties().isCommercialFontPresent();
    }

    @Override
    public boolean isLessUsedOverCss() {
        return getScipamatoProperties().isLessUsedOverCss();
    }

    @Override
    public boolean isNavbarVisibleByDefault() {
        return getScipamatoProperties().isNavbarVisibleByDefault();
    }

    @Nullable
    @Override
    public String getCmsUrlNewStudyPage() {
        return getScipamatoProperties().getCmsUrlNewStudyPage();
    }

    @Override
    public int getAuthorsAbbreviatedMaxLength() {
        return getScipamatoProperties().getAuthorsAbbreviatedMaxLength();
    }

    @Override
    public boolean isResponsiveIframeSupportEnabled() {
        return getScipamatoProperties().isResponsiveIframeSupportEnabled();
    }

    @NotNull
    @Override
    public String getManagementUserName() {
        return getScipamatoProperties().getManagementUserName();
    }

    @Nullable
    @Override
    public String getManagementUserPassword() {
        return getScipamatoProperties().getManagementUserPassword();
    }

    @Override
    public int getNumberOfPreviousNewslettersInArchive() {
        return getScipamatoProperties().getNumberOfPreviousNewslettersInArchive();
    }
}
