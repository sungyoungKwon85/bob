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
        val result = PointsResponse()
        val latValue: Double = (latTr - latBl) / 3
        val lonValue: Double = (lonTr - lonBl) / 3
        var totalCount: Long = 0
        for (i in 0..2) {
            for (j in 0..2) {
                val blLat: Double = latBl + latValue * i
                val blLon: Double = lonBl + lonValue * j
                val bl = Point(blLon, blLat)
                val trLat = latBl + latValue * (i + 1)
                val trLon = lonBl + lonValue * (j + 1)
                val tr = Point(trLon, trLat)
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

    // todo
    // 읍면동 지역의 중심좌표는 알 수 있으나 범위를 알 수가 없다
    // 중심좌표를 주면 클라에서 그려서 범위 api를 주도록?
    override suspend fun getPointsByCenter(lat: Double, lon: Double): Mono<PointsResponse> {
        var respone = PointsResponse()
        respone.totalCount = restaurantRepository.getCountGeoWithin(lat, lon, 3.0).awaitFirst()
        respone.points.add(PointsResponse.Point(lat, lon, respone.totalCount))

        return Mono.justOrEmpty(respone)
    }
}
