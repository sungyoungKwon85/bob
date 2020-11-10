package com.skplanet.bob.service

import com.skplanet.bob.api.model.PointsResponse
import com.skplanet.bob.repository.AdministrativeAreaRepository
import com.skplanet.bob.repository.PointRepository
import kotlinx.coroutines.reactive.awaitFirst
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.geo.Box
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Slf4j
@Service
class PointsServiceImpl : PointsService {

    @Autowired
    lateinit var pointRepository: PointRepository

    @Autowired
    lateinit var administrativeAreaRepository: AdministrativeAreaRepository

    @Autowired
    lateinit var naverCloudPlatformService: NaverCloudPlatformService


    override suspend fun getPointsByRange(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double, z: Int): Mono<PointsResponse> {
        val result = PointsResponse()

        val center = getCenter(lonBl, latBl, lonTr, latTr)
        val reverseGeocode = naverCloudPlatformService.reverseGeocode(center.x, center.y).awaitFirst()
        when (z) {
            in 12..13 -> { // by sgg
                val area2 = reverseGeocode.results[0].region.area2
                result.center.areaName = area2.name
                result.center.longitude = area2.coords.center.x
                result.center.latitude = area2.coords.center.y
            }
            else -> { // by umd
                val area3 = reverseGeocode.results[0].region.area3
                result.center.areaName = area3.name
                result.center.longitude = area3.coords.center.x
                result.center.latitude = area3.coords.center.y
            }
        }

        return pointRepository.findByZAndBox(Box(Point(lonBl, latBl), Point(lonTr, latTr)), z).doOnNext {
            result.totalCount += it.count
            result.points.add(buildPoint(it))
        }.then(Mono.justOrEmpty(result))
    }

    private fun getCenter(lonBl: Double, latBl: Double, lonTr: Double, latTr: Double): Point {
        val horizontal = (lonTr - lonBl) / 2.0
        val vertical = (latTr - latBl) / 2.0
        return Point(lonBl + horizontal, latBl + vertical)
    }


    override suspend fun getPointsByArea(sggId: String, umdId: String, z: Int): Mono<PointsResponse> {
        var result = PointsResponse()
        val area = administrativeAreaRepository.findByUmdId(umdId).awaitFirst()
        result.center.areaName = area.name
        result.center.longitude = area.location.coordinates[0]
        result.center.latitude = area.location.coordinates[1]

        when (z) {
            in 12..13 -> {
                return pointRepository.findByZAndAreaId(z, sggId).doOnNext {
                    result.totalCount += it.count
                    result.points.add(buildPoint(it))
                }.then(Mono.justOrEmpty(result))
            }
            else -> {
                return pointRepository.findByZAndParentAreaId(z, sggId).doOnNext {
                    result.totalCount += it.count
                    result.points.add(buildPoint(it))
                }.then(Mono.justOrEmpty(result))
            }
        }

    }

    private fun buildPoint(it: com.skplanet.bob.model.Point): PointsResponse.Point {
        return PointsResponse.Point(
                it.id,
                it.z,
                it.areaName,
                it.areaId,
                it.location.coordinates[1],
                it.location.coordinates[0],
                it.count
        )
    }
}
