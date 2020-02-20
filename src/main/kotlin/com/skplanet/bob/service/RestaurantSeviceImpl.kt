package com.skplanet.bob.service

import com.skplanet.bob.model.Restaurant
import com.skplanet.bob.repository.RestaurantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RestaurantSeviceImpl : RestaurantService {

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    override fun getRestaurant(id: String): Mono<Restaurant> = restaurantRepository.findById(id)

    override fun createRestaurant(restaurant: Mono<Restaurant>): Mono<Restaurant> = restaurantRepository.create(restaurant)

    override fun getCountGeoWithin(lon: Double, lat: Double, distance: Double): Mono<Long> = restaurantRepository.getCountGeoWithin(lon, lat, distance)

}