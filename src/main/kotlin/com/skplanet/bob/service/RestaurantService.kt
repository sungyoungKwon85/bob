package com.skplanet.bob.service

import com.skplanet.bob.api.model.RestaurantsResponse
import com.skplanet.bob.model.Restaurant
import org.springframework.data.domain.Pageable
import reactor.core.publisher.Mono

interface RestaurantService {
    fun getRestaurant(id: String): Mono<Restaurant>
    fun createRestaurant(restaurant: Mono<Restaurant>): Mono<Restaurant>
    fun getCountGeoWithin(lon: Double, lat: Double, distance: Double): Mono<Long>
    suspend fun searchRestaurants(lon: Double, lat: Double, level: Int, pageable: Pageable): Mono<RestaurantsResponse>
    suspend fun searchRestaurants(pointId: String, pageable: Pageable): Mono<RestaurantsResponse>
    suspend fun searchRestaurants(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double, pageable: Pageable): Mono<RestaurantsResponse>
}
