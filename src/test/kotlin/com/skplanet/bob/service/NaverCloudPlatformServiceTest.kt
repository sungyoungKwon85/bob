package com.skplanet.bob.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
internal class NaverCloudPlatformServiceTest {

    @Autowired
    lateinit var naverCloudPlatformService: NaverCloudPlatformService

    @Test
    fun geocode() {
        val geocode = naverCloudPlatformService.geocode("모현읍")
        StepVerifier.create(geocode)
                .assertNext {
                    it.status == "OK"
                }.expectComplete()
    }

    @Test
    fun reverseGeocode() {
        val reverseGeocode = naverCloudPlatformService.reverseGeocode(127.055413, 37.501192)
        StepVerifier.create(reverseGeocode)
                .assertNext {
                    it.status.code == 0
                }.expectComplete()
    }
}