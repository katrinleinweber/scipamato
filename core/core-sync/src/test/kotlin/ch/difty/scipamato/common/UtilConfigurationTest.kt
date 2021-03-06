package ch.difty.scipamato.common

import org.assertj.core.api.AssertionsForClassTypes.assertThat

import org.junit.jupiter.api.Test

internal class UtilConfigurationTest {

    @Test
    fun dateTimeService() {
        assertThat(UtilConfiguration().dateTimeService()).isInstanceOf(CurrentDateTimeService::class.java)
    }
}
