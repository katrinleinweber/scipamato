package ch.difty.scipamato.core.entity.keyword;

import static org.assertj.core.api.Assertions.assertThat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class KeywordFilterTest {

    private final KeywordFilter f = new KeywordFilter();

    @Test
    public void getAndSet() {
        f.setNameMask("nameMask");

        assertThat(f.getNameMask()).isEqualTo("nameMask");

        assertThat(f.toString()).isEqualTo("KeywordFilter(nameMask=nameMask)");
    }

    @Test
    public void equals() {
        EqualsVerifier
            .forClass(KeywordFilter.class)
            .withRedefinedSuperclass()
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify();
    }

    @Test
    public void assertEnumFields() {
        assertThat(KeywordFilter.KeywordFilterFields.values())
            .extracting("name")
            .containsExactly("nameMask");
    }

}