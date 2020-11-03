package com.skplanet.bob.service

import com.skplanet.bob.Log
import com.skplanet.bob.api.model.PointsResponse
import com.skplanet.bob.repository.RestaurantRepository
import kotlinx.coroutines.reactive.awaitFirst
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
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


    // todo 중심점을 어떻게 줄 것인가?
    // calculation늘리면 성능 문제 있음
    override suspend fun getPointsByRange(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double): Mono<PointsResponse> {
        val result = PointsResponse()
        val bl = Point(lonBl, latBl)
        val tr = Point(lonTr, latTr)

        val zoneMap = HashMap<Point, MutableList<String>>()
        initZoneMap(lonBl, latBl, lonTr, latTr, zoneMap)

        result.totalCount = restaurantRepository.getCountGeoWithinBySquare(bl, tr).awaitFirst()
        logger.debug("restaurants count is {}", result.totalCount)

        return restaurantRepository.getGeoWithinBySquare(bl, tr).doOnNext {
            val zone: Point = findZone(it.location?.coordinates, lonBl, latBl, lonTr, latTr)
            it.id?.let { it1 -> zoneMap[zone]?.add(it1) }
        }.doOnComplete {
            val horizontal = getHorizontal(lonTr, lonBl) / 2
            val vertical = getVertical(latTr, latBl) / 2
            result.points = zoneMap.filter { zone -> zone.value.size != 0 }
                    .map { zone ->
                        PointsResponse.Point(zone.key.y - vertical, zone.key.x - horizontal, zone.value.size.toLong(), zone.value) }
                    .toMutableList()
            result.totalCount++
        }.then(Mono.just(result))
    }

    private fun getVertical(latTr: Double, latBl: Double) = (latTr - latBl) / CALCULATION

    private fun getHorizontal(lonTr: Double, lonBl: Double) = (lonTr - lonBl) / CALCULATION

    private fun initZoneMap(lonBl: Double, latBl: Double, lonTr: Double, latTr: Double, zoneMap: HashMap<Point, MutableList<String>>) {
        val horizontal = getHorizontal(lonTr, lonBl)
        val vertical = getVertical(latTr, latBl)
        for (i in 1..CALCULATION) {
            for (j in 1..CALCULATION) {
                val lon = lonBl + horizontal * j
                val lat = latBl + vertical * i
                zoneMap[Point(lon, lat)] = ArrayList()
            }
        }
    }

    private fun findZone(coordinates: List<Double>?, lonBl: Double, latBl: Double, lonTr: Double, latTr: Double): Point {
        if (coordinates == null || coordinates.isEmpty()) {
            return Point(0.0, 0.0)
        }
        val horizontal = getHorizontal(lonTr, lonBl)
        val vertical = getVertical(latTr, latBl)

        val lon = coordinates[0]
        val lat = coordinates[1]
        for (i in 1..CALCULATION) {
            for (j in 1..CALCULATION) {
                val zoneLon = lonBl + horizontal * j
                val zoneLat = latBl + vertical * i
                if (zoneLon > lon && zoneLat > lat) {
                    return Point(zoneLon, zoneLat)
                }
            }
        }
        return Point(0.0, 0.0)
    }

//    private fun getComparableDistance(lonTr: Double, lonBl: Double, latTr: Double, latBl: Double): Double {
//        val calculation = 8.0
//        val horizontal = (lonTr - lonBl) / calculation
//        val vertical = (latTr - latBl) / calculation
//        return sqrt(horizontal * horizontal + vertical * vertical)
//    }

    override suspend fun getPointsByCenter(lat: Double, lon: Double): Mono<PointsResponse> {
        var respone = PointsResponse()
        return restaurantRepository.getCountGeoWithin(lat, lon, 3.0).doOnNext {
            respone.totalCount = it
            respone.points.add(PointsResponse.Point(lat, lon, respone.totalCount))
        }.then(Mono.justOrEmpty(respone))
    }
}
