package com.skplanet.bob.router

import com.skplanet.bob.handler.AdministrativeAreaHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class AdministrativeAreaRouter(private val administrativeAreaHandler: AdministrativeAreaHandler) {

    @Bean
    fun administrativeAreaRoutes() = router {
        "/areas".nest {
            GET("/sd", administrativeAreaHandler::getSds)
            GET("/sgg/{sdId}", administrativeAreaHandler::getSggs)
            GET("/umd/{sggId}", administrativeAreaHandler::getUmds)
        }
    }
}