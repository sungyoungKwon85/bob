package com.skplanet.bob.handler

import com.skplanet.bob.service.RestaurantService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyToMono
import java.net.URI

@Component
class RestaurantHandler(val restaurantService: RestaurantService) {

    fun get(serverRequest: ServerRequest) =
            restaurantService.getRestaurant(serverRequest.pathVariable("id"))
                    .flatMap { ok().body(BodyInserters.fromValue(it)) }
                    .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun create(serverRequest: ServerRequest) =
            restaurantService.createRestaurant(serverRequest.bodyToMono())
                    .flatMap {
                        created(URI.create("/functional/restaurant/${it.id}")).build()
                    }

    suspend fun getList(serverRequest: ServerRequest): ServerResponse {
        val type = serverRequest.queryParam("type").orElse("")

        return when (type) {
            "point" -> {
                val lon = serverRequest.queryParam("lon").get().toDouble()
                val lat = serverRequest.queryParam("lat").get().toDouble()
                val level = serverRequest.queryParam("level").get().toInt()
                restaurantService.searchRestaurants(lon, lat, level)
                        .flatMap { ok().body(BodyInserters.fromValue(it)) }
                        .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                        .awaitFirst()
            }
            else -> {
                restaurantService.searchRestaurants(
                        serverRequest.queryParam("lat_bl").get().toDouble(),
                        serverRequest.queryParam("lon_bl").get().toDouble(),
                        serverRequest.queryParam("lat_tr").get().toDouble(),
                        serverRequest.queryParam("lon_tr").get().toDouble())
                        .flatMap { ok().body(BodyInserters.fromValue(it)) }
                        .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                        .awaitFirst()
            }

        }
    }

    fun getCountGeoWithin(serverRequest: ServerRequest) = restaurantService.getCountGeoWithin(
            serverRequest.pathVariable("lon").toDouble()
            , serverRequest.pathVariable("lat").toDouble()
            , serverRequest.pathVariable("distance").toDouble())
            .flatMap { ok().body(BodyInserters.fromValue(it)) }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

}
