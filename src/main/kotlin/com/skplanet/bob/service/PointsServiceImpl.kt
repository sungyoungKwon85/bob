package com.skplanet.bob.service

import com.skplanet.bob.api.model.PointsResponse
import com.skplanet.bob.repository.RestaurantRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PointsServiceImpl : PointsService {
    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    override suspend fun getPointsByRange(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double, level: Int): Mono<PointsResponse> {
        var result = PointsResponse()
        var latValue: Double = (latTr - latBl) / 3;
        var lonValue: Double = (lonTr - lonBl) / 3;
        var totalCount: Long = 0;
        for (i in 0..2) {
            for (j in 0..2) {
                val blLat: Double = latBl + latValue * i
                val blLon: Double = lonBl + lonValue * j
                val bl = Point(blLat, blLon)
                val trLat = latBl + latValue * (i + 1)
                val trLon = lonBl + lonValue * (j + 1)
                val tr = Point(trLat, trLon)
                val count = restaurantRepository.getCountGeoWithinBySquare(bl, tr).awaitFirst()
                if (count > 0) {
                    totalCount += count
                    result.points.add(
                            PointsResponse.Point((blLat + trLat) / 2, (blLon + trLon) / 2, count))
                }
            }
        }
        result.totalCount = totalCount
        return Mono.just(result)
    }
}
