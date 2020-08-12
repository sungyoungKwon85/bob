package com.skplanet.bob.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
internal class NaverCloudPlatformServiceTest {

    @Autowired
    lateinit var naverCloudPlatformService: NaverCloudPlatformService

    @Test
    fun geocode() {
//        val geocode = naverCloudPlatformService.geocode("모현읍")
//        assertNotNull(geocode)
//        assertNotNull(geocode?.addresses)
//        assertNotNull(geocode?.addresses!![0])
//        assertEquals(geocode?.addresses!![0].x, "127.2423688")
    }
}