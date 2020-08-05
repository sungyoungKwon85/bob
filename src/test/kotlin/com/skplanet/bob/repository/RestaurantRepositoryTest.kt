package com.skplanet.bob.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
internal class RestaurantRepositoryTest {

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Test
    fun findByName() {
        val findByName = restaurantRepository.findByName("헬로우돈까스")

        StepVerifier.create(findByName)
                .assertNext {
                    assertEquals(it.name, "헬로우돈까스")
                }
                .verifyComplete()
    }
}