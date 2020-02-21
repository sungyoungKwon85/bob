package com.skplanet.bob.router

import com.skplanet.bob.handler.RestaurantHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class RestaurantRouter(private val restaurantHandler: RestaurantHandler) {

    @Bean
    fun restaurantRoutes() = router {
        "/restaurant".nest {
            GET("/{id}", restaurantHandler::get)
            POST("/", restaurantHandler::create)
            GET("/{lon}/{lat}/{distance}", restaurantHandler::getCountGeoWithin)
        }
    }
}