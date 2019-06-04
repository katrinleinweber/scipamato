package ch.difty.scipamato.core.entity.code;

import java.util.Arrays;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;

import ch.difty.scipamato.common.entity.AbstractDefinitionEntity;
import ch.difty.scipamato.common.entity.FieldEnumType;
import ch.difty.scipamato.core.entity.CodeClass;

/**
 * Entity used for managing the codes in all defined languages.
 * This aggregate encapsulates the individual translations for all languages,
 * each captured in a {@link CodeTranslation}.
 * <p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CodeDefinition extends AbstractDefinitionEntity<CodeTranslation, String> {
    private static final long serialVersionUID = 1L;

    private String    code;
    private CodeClass codeClass;
    private int       sort;
    private boolean   internal;

    public CodeDefinition(final String code, final String mainLanguageCode, final CodeClass codeClass, final int sort,
        final boolean internal, final Integer version, final CodeTranslation... translations) {
        super(mainLanguageCode, Arrays
            .stream(translations)
            .filter(tr -> mainLanguageCode.equals(tr.getLangCode()))
            .map(CodeTranslation::getName)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse("n.a."), version, translations);

        this.code = code;
        this.codeClass = codeClass;
        this.sort = sort;
        this.internal = internal;
    }

    @Override
    public String getNullSafeId() {
        return code;
    }

    public enum CodeDefinitionFields implements FieldEnumType {
        CODE("code"),
        MAIN_LANG_CODE("mainLanguageCode"),
        CODE_CLASS("codeClass"),
        SORT("sort"),
        INTERNAL("internal"),
        NAME("name");

        private final String name;

        CodeDefinitionFields(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

    }

}