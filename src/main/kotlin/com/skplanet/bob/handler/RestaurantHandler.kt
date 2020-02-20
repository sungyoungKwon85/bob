package com.skplanet.bob.handler

import com.skplanet.bob.service.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyToMono
import java.net.URI

@Component
class RestaurantHandler(val restaurantService: RestaurantService) {

    fun get(serverRequest: ServerRequest) =
            restaurantService.getRestaurant(serverRequest.pathVariable("id"))
                    .flatMap { ok().body(fromObject(it)) }
                    .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun create(serverRequest: ServerRequest) =
            restaurantService.createRestaurant(serverRequest.bodyToMono())
                    .flatMap {
                        created(URI.create("/functional/restaurant/${it.id}")).build()
                    }

    fun getCountGeoWithin(serverRequest: ServerRequest) = restaurantService.getCountGeoWithin(
            serverRequest.pathVariable("lon").toDouble()
            , serverRequest.pathVariable("lat").toDouble()
            , serverRequest.pathVariable("distance").toDouble())
            .flatMap { ok().body(fromObject(it)) }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

}
