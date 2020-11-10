package com.skplanet.bob.service

import com.skplanet.bob.Log
import com.skplanet.bob.api.model.PointsResponse
import com.skplanet.bob.repository.PointRepository
import com.skplanet.bob.repository.RestaurantRepository
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.geo.Box
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Slf4j
@Service
class PointsServiceImpl : PointsService {

    companion object : Log {
        private const val CALCULATION = 8
    }

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var pointRepository: PointRepository


    override suspend fun getPointsByRange(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double, z: Int): Mono<PointsResponse> {
        val result = PointsResponse()

        return pointRepository.findByZAndBox(Box(Point(lonBl, latBl), Point(lonTr, latTr)), z).doOnNext {
            result.totalCount += it.count
            result.points.add(PointsResponse.Point(
                    it.id,
                    it.z,
                    it.areaName,
                    it.areaId,
                    it.location.coordinates[1],
                    it.location.coordinates[0],
                    it.count
            ))
        }.then(Mono.justOrEmpty(result))
    }


    override suspend fun getPointsByUmdId(umdId: String): Mono<PointsResponse> {
        var result = PointsResponse()
        return pointRepository.findByZAndUmdId(14, umdId).doOnNext {
            result.totalCount += it.count
            result.points.add(PointsResponse.Point(
                    it.id,
                    it.z,
                    it.areaName,
                    it.areaId,
                    it.location.coordinates[1],
                    it.location.coordinates[0],
                    it.count
            ))
        }.then(Mono.justOrEmpty(result))
    }
}
