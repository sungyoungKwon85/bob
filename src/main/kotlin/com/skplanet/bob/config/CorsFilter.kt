package com.skplanet.bob.config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Component
class CorsFilter {
    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val config = CorsConfiguration()
        config.addAllowedOrigin("*")
        config.maxAge = 8000L
        config.addAllowedMethod(HttpMethod.GET)
        config.addAllowedMethod(HttpMethod.POST)
        config.addAllowedMethod(HttpMethod.PUT)
        config.addAllowedHeader("*")

        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }

        return CorsWebFilter(source)
    }
}