package com.skplanet.bob.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.geo.Box
import org.springframework.data.geo.Point
import reactor.test.StepVerifier

@SpringBootTest
internal class PointRepositoryTest {

    @Autowired
    lateinit var pointRepository: PointRepository

    @Test
    fun findByZ() {
        val findByZ = pointRepository.findByZ(12)
        StepVerifier.create(findByZ)
                .expectNextCount(377)
                .verifyComplete()
    }

    @Test
    fun findByZAndBox() {
        val findByZAndBox = pointRepository.findByZAndBox(
                Box(Point(127.0236148, 37.4229882), Point(127.1609439, 37.6274734)), 12)
        StepVerifier.create(findByZAndBox)
                .expectNextCount(26)
                .verifyComplete()
    }


}