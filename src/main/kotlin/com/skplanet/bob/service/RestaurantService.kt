package com.skplanet.bob.service

import com.skplanet.bob.model.Restaurant
import reactor.core.publisher.Mono

interface RestaurantService {
    fun getRestaurant(id: String): Mono<Restaurant>
    fun createRestaurant(restaurant: Mono<Restaurant>): Mono<Restaurant>
}