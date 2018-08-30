package ch.difty.scipamato.publ.web.font;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ch.difty.scipamato.common.TestUtils;
import ch.difty.scipamato.publ.config.ApplicationPublicProperties;
import ch.difty.scipamato.publ.web.resources.IcoMoonCssResourceReference;

@RunWith(MockitoJUnitRunner.class)
public class IcoMoonFontResourceProviderTest {

    private IcoMoonFontResourceProvider provider;

    @Mock
    private ApplicationPublicProperties applicationProperties;

    @Test
    public void degenerateConstruction_withNullArgument() {
        TestUtils.assertDegenerateSupplierParameter(() -> new IcoMoonFontResourceProvider(null),
            "applicationProperties");
    }

    @Test
    public void withNoCommercialFontPresentSetting_getsNull() {
        when(applicationProperties.isCommercialFontPresent()).thenReturn(false);

        provider = new IcoMoonFontResourceProvider(applicationProperties);
        assertThat(provider.getCssResourceReference()).isNull();
        assertThat(provider.isCommercialFontPresent()).isFalse();

        verify(applicationProperties).isCommercialFontPresent();
    }

    @Test
    public void withCommercialFontPresentSetting_getsReference() {
        when(applicationProperties.isCommercialFontPresent()).thenReturn(true);

        provider = new IcoMoonFontResourceProvider(applicationProperties);
        assertThat(provider.getCssResourceReference())
            .isNotNull()
            .isInstanceOf(IcoMoonCssResourceReference.class);
        assertThat(provider.isCommercialFontPresent()).isTrue();

        verify(applicationProperties).isCommercialFontPresent();
    }
}