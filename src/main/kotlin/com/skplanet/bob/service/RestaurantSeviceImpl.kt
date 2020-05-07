package com.skplanet.bob.service

import com.skplanet.bob.api.model.PointsResponse
import com.skplanet.bob.api.model.RestaurantsResponse
import com.skplanet.bob.model.Restaurant
import com.skplanet.bob.repository.RestaurantRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RestaurantSeviceImpl : RestaurantService {

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    override fun getRestaurant(id: String): Mono<Restaurant> = restaurantRepository.findById(id)

    override fun createRestaurant(restaurant: Mono<Restaurant>): Mono<Restaurant> = restaurantRepository.create(restaurant)

    override fun getCountGeoWithin(lon: Double, lat: Double, distance: Double): Mono<Long> = restaurantRepository.getCountGeoWithin(lon, lat, distance)

    override suspend fun searchPoint(lon: Double, lat: Double, level: Int): Mono<RestaurantsResponse> {
        val lonSize: Double = 0.5 * level
        val latSize: Double = 0.5 * level

        val blLon: Double = lon - lonSize
        val blLat: Double = lat - latSize
        val bl = Point(blLon, blLat)

        val trLon: Double = lon + lonSize
        val trLat:Double = lat + latSize
        val tr = Point(trLon, trLat)
        val restaurants: Flux<Restaurant> = restaurantRepository.getGeoWithinBySquare(bl, tr)

        val result = RestaurantsResponse()
        restaurants.subscribe() {
            result.count++
            result.restaurants.add(it)
        }

        return Mono.just(result)
    }
}
