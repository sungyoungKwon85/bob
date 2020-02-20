package com.skplanet.bob.router

import com.skplanet.bob.handler.PointsHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class PointsRouter(private val pointsHandler: PointsHandler) {

    @Bean
    fun pointsRoutes() = router {
        "points".nest {
            GET("/range", pointsHandler::getPointsByRange)
        }
    }

}