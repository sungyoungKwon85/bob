package com.skplanet.bob.router

import com.skplanet.bob.handler.PointsHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class PointsRouter(private val pointsHandler: PointsHandler) {

    @Bean
    fun pointsRoutes() = coRouter {
        "/points".nest {
            GET("/", pointsHandler::getPointsBy)
        }
    }

}