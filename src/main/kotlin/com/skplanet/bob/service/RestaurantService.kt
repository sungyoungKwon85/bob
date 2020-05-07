package com.skplanet.bob.service

import com.skplanet.bob.api.model.RestaurantsResponse
import com.skplanet.bob.model.Restaurant
import reactor.core.publisher.Mono

interface RestaurantService {
    fun getRestaurant(id: String): Mono<Restaurant>
    fun createRestaurant(restaurant: Mono<Restaurant>): Mono<Restaurant>
    fun getCountGeoWithin(lon: Double, lat: Double, distance: Double): Mono<Long>
    suspend fun searchPoint(lon: Double, lat: Double, level: Int): Mono<RestaurantsResponse>
}
