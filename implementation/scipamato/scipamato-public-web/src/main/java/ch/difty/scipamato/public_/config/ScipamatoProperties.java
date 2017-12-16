package ch.difty.scipamato.public_.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.difty.scipamato.common.config.core.ApplicationProperties;
import ch.difty.scipamato.common.config.core.AuthorParserStrategy;

/**
 * This bean is used to evaluate all environment properties used in the application in one place and serve those as bean to wherever they are used.
 *
 * @see <a href="https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-injecting-property-values-into-configuration-beans/">https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-injecting-property-values-into-configuration-beans/</a>
 *
 * @author u.joss
 */
@Component
public class ScipamatoProperties implements ApplicationProperties {

    private final String buildVersion;
    private final String defaultLocalization;
    private final String brand;
    private final String pubmedBaseUrl;

    private static final String S = "${", E = ":n.a.}";

    public ScipamatoProperties(@Value(S + BUILD_VERSION + E) String buildVersion, @Value(S + LOCALIZATION_DEFAULT + ":en}") String defaultLocalization, @Value(S + BRAND + E) String brand,
            @Value(S + PUBMED_BASE_URL + E) String pubmedBaseUrl) {
        this.buildVersion = buildVersion;
        this.defaultLocalization = defaultLocalization;
        this.brand = brand;
        this.pubmedBaseUrl = pubmedBaseUrl;
    }

    @Override
    public String getBuildVersion() {
        return buildVersion;
    }

    @Override
    public String getDefaultLocalization() {
        return defaultLocalization;
    }

    @Override
    public AuthorParserStrategy getAuthorParserStrategy() {
        return null;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public long getMinimumPaperNumberToBeRecycled() {
        return 0;
    }

    @Override
    public String getPubmedBaseUrl() {
        return pubmedBaseUrl;
    }

}