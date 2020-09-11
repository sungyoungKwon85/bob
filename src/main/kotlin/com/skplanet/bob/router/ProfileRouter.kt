package com.skplanet.bob.router

import com.skplanet.bob.handler.PointsHandler
import com.skplanet.bob.handler.ProfileHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Component
class ProfileRouter(private val profileHandler: ProfileHandler) {

    @Bean
    fun profileRoutes() = router {
        "/profile".nest {
            GET("/", profileHandler::profile)
        }
    }

}