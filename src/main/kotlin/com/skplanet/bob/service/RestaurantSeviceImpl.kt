package com.skplanet.bob.service

import com.skplanet.bob.api.model.RestaurantsResponse
import com.skplanet.bob.model.Restaurant
import com.skplanet.bob.repository.RestaurantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class RestaurantSeviceImpl : RestaurantService {

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    override fun getRestaurant(id: String): Mono<Restaurant> = restaurantRepository.findById(id)

    override fun createRestaurant(restaurant: Mono<Restaurant>): Mono<Restaurant> = restaurantRepository.create(restaurant)

    override fun getCountGeoWithin(lon: Double, lat: Double, distance: Double): Mono<Long> = restaurantRepository.getCountGeoWithin(lon, lat, distance)

    override suspend fun searchRestaurants(lon: Double, lat: Double, level: Int): Mono<RestaurantsResponse> {
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

    override suspend fun searchRestaurants(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double): Mono<RestaurantsResponse> {
        val result = RestaurantsResponse()
        val latValue: Double = (latTr - latBl) / 3
        val lonValue: Double = (lonTr - lonBl) / 3

        for (i in 0..2) {
            for (j in 0..2) {
                val blLat: Double = latBl + latValue * i
                val blLon: Double = lonBl + lonValue * j
                val bl = Point(blLon, blLat)
                val trLat = latBl + latValue * (i + 1)
                val trLon = lonBl + lonValue * (j + 1)
                val tr = Point(trLon, trLat)
                val restaurants = restaurantRepository.getGeoWithinBySquare(bl, tr)
                restaurants.subscribe() {
                    result.count++
                    result.restaurants.add(it)
                }
            }
        }

        return Mono.just(result)
    }
}
