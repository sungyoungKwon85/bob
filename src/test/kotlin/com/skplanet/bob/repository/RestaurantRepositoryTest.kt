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

    @Test
    fun getListByIds() {
        val ids = listOf("5f97c7d9fefa7831e5dc05f1",
                "5f97c7d9fefa7831e5dc05f3",
                "5f97c7d9fefa7831e5dc05f4",
                "5f97c7d9fefa7831e5dc05f5",
                "5f97c7d9fefa7831e5dc05f6");
        val listByIds = restaurantRepository.getListByIds(ids)
        StepVerifier.create(listByIds)
                .expectNextCount(5)
                .verifyComplete()
    }
}