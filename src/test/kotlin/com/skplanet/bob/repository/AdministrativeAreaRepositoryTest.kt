package com.skplanet.bob.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
internal class AdministrativeAreaRepositoryTest {
    @Autowired
    lateinit var administrativeAreaRepository: AdministrativeAreaRepository

    @Test
    fun `findByType with sd then size is 17`() {
        val findByType = administrativeAreaRepository.findByType("sd")
        StepVerifier.create(findByType)
                .expectNextCount(17)
                .verifyComplete()
    }

    @Test
    fun `findByUmdId`() {
        val findByUmdId = administrativeAreaRepository.findByUmdId("1116067")
        StepVerifier.create(findByUmdId)
                .expectNextMatches { it.name == "발산1동" }
                .verifyComplete()
    }
}