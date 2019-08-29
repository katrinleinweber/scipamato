package ch.difty.scipamato.publ.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import ch.difty.scipamato.common.entity.CodeClassId;
import ch.difty.scipamato.publ.entity.Code;
import ch.difty.scipamato.publ.persistence.code.JooqCodeRepo;

@Slf4j
@JooqTest
@Testcontainers
class JooqCodeRepoIntegrationTest {

    @Autowired
    private JooqCodeRepo repo;

    @Test
    void findingAllCodes1InGerman() {
        List<Code> codesOfClass1 = repo.findCodesOfClass(CodeClassId.CC1, "de");
        assertThat(codesOfClass1)
            .isNotEmpty()
            .hasSize(18);
        codesOfClass1.forEach((c) -> log.debug(c.toString()));
    }

    @Test
    void findingAllCodes2InEnglish() {
        List<Code> codesOfClass1 = repo.findCodesOfClass(CodeClassId.CC2, "en");
        assertThat(codesOfClass1)
            .isNotEmpty()
            .hasSize(2);
        codesOfClass1.forEach((c) -> log.debug(c.toString()));
    }

    @Test
    void findingAllCodes3InEnglish() {
        List<Code> codesOfClass1 = repo.findCodesOfClass(CodeClassId.CC3, "fr");
        assertThat(codesOfClass1)
            .isNotEmpty()
            .hasSize(12);
        codesOfClass1.forEach((c) -> log.debug(c.toString()));
    }

}