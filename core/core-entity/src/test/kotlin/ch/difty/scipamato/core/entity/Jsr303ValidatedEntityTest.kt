package ch.difty.scipamato.core.entity

import ch.difty.scipamato.common.entity.FieldEnumType
import ch.difty.scipamato.common.entity.ScipamatoEntity.ScipamatoEntityFields.CREATED
import ch.difty.scipamato.common.entity.ScipamatoEntity.ScipamatoEntityFields.MODIFIED
import ch.difty.scipamato.core.entity.CoreEntity.CoreEntityFields.CREATOR_ID
import ch.difty.scipamato.core.entity.CoreEntity.CoreEntityFields.MODIFIER_ID
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.validator.HibernateValidator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import javax.validation.ConstraintViolation

@Suppress("SpellCheckingInspection")
abstract class Jsr303ValidatedEntityTest<T : CoreEntity> protected constructor(private val clazz: Class<T>) {

    private val validatorFactoryBean: LocalValidatorFactoryBean = LocalValidatorFactoryBean()
    private var violations: Set<ConstraintViolation<T>>? = null

    /**
     * @return toString value of the valid entity
     */
    protected abstract val toString: String

    /**
     * @return the display value of the entity
     */
    protected abstract val displayValue: String

    @BeforeEach
    internal fun setUp() {
        validatorFactoryBean.setProviderClass(HibernateValidator::class.java)
        validatorFactoryBean.afterPropertiesSet()
    }

    @Test
    internal fun assertCompleteEntityPassesValidation() {
        verifySuccessfulValidation(newValidEntity())
    }

    /**
     * Implement to return a fully valid entity of type T
     *
     * @return entity
     */
    protected abstract fun newValidEntity(): T

    private fun validate(validateable: T) {
        violations = validatorFactoryBean.validate(validateable)
    }

    /**
     * Validates the entity that is assumed to be complete and valid. Asserts there are not violations
     */
    protected fun verifySuccessfulValidation(validateable: T) {
        validate(validateable)
        assertThat(violations).isEmpty()
    }

    /**
     * Validates the passed in entity that is assumed to have exactly one validation issue. Asserts the validation message.
     */
    protected fun validateAndAssertFailure(validateable: T, fieldType: FieldEnumType, invalidValue: Any?, msg: String) {
        validate(validateable)

        assertThat(violations).isNotEmpty
        val violation = violations!!.first()
        assertThat(violation.messageTemplate).isEqualTo(msg)
        assertThat(violation.invalidValue).isEqualTo(invalidValue)
        assertThat(violation.propertyPath.toString()).isEqualTo(fieldType.fieldName)
    }

    @Test
    @Disabled("TODO")
    internal fun toString_isMinimal() {
        val entity = newValidEntity()
        assertThat(entity.toString()).isEqualTo(toString)
    }

    @Test
    internal fun displayValue_isEqualTo() {
        val entity = newValidEntity()
        assertThat(entity.displayValue).isEqualTo(displayValue)
    }

    @Test
    internal open fun verifyEquals() {
        EqualsVerifier
            .forClass(clazz)
            .usingGetClass()
            .withRedefinedSuperclass()
            .withIgnoredFields(CREATED.fieldName, CREATOR_ID.fieldName, MODIFIED.fieldName, MODIFIER_ID.fieldName)
            .suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
            .verify()
    }
}