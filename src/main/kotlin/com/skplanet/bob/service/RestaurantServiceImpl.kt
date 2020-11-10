package com.skplanet.bob.service

import com.skplanet.bob.api.model.RestaurantsResponse
import com.skplanet.bob.model.Restaurant
import com.skplanet.bob.repository.PointRepository
import com.skplanet.bob.repository.RestaurantRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class RestaurantServiceImpl : RestaurantService {

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var pointRepository: PointRepository

    override fun getRestaurant(id: String): Mono<Restaurant> = restaurantRepository.findById(id)

    override fun createRestaurant(restaurant: Mono<Restaurant>): Mono<Restaurant> = restaurantRepository.create(restaurant)

    override fun getCountGeoWithin(lon: Double, lat: Double, distance: Double): Mono<Long> = restaurantRepository.getCountGeoWithin(lon, lat, distance)

    override suspend fun searchRestaurants(lon: Double, lat: Double, level: Int, pageable: Pageable): Mono<RestaurantsResponse> {
        val lonSize: Double = 0.5 * level
        val latSize: Double = 0.5 * level

        val blLon: Double = lon - lonSize
        val blLat: Double = lat - latSize
        val bl = Point(blLon, blLat)

        val trLon: Double = lon + lonSize
        val trLat: Double = lat + latSize
        val tr = Point(trLon, trLat)
        val result = RestaurantsResponse()
        val totalCount = restaurantRepository.getCountGeoWithinBySquare(bl, tr).awaitFirst()
        return restaurantRepository.getListGeoWithinBySquare(bl, tr, pageable)
                .doOnNext {
                    result.count++
                    result.restaurants.add(it)
                    result.totalCount = totalCount.toInt()
                }.then(Mono.just(result))
    }

    override suspend fun searchRestaurants(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double, pageable: Pageable): Mono<RestaurantsResponse> {
        val result = RestaurantsResponse()
        val bl = Point(lonBl, latBl)
        val tr = Point(lonTr, latTr)
        val totalCount = restaurantRepository.getCountGeoWithinBySquare(bl, tr).awaitFirst()
        return restaurantRepository.getListGeoWithinBySquare(bl, tr, pageable)
                .doOnNext {
                    result.count++
                    result.restaurants.add(it)
                    result.totalCount = totalCount.toInt()
                }.then(Mono.just(result))
    }

    override suspend fun searchRestaurants(pointId: String, pageable: Pageable): Mono<RestaurantsResponse> {
        val result = RestaurantsResponse()

        val findById = pointRepository.findById(pointId).awaitFirst()
        return restaurantRepository.getListByIds(findById.ids).doOnNext {
            result.count++
            result.totalCount++
            result.restaurants.add(it)
        }.then(Mono.justOrEmpty(result))
    }
}
