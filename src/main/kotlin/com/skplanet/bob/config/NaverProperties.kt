package com.skplanet.bob.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "naver")
class NaverProperties {
    lateinit var clientId: String
    lateinit var clientSecret: String
}
