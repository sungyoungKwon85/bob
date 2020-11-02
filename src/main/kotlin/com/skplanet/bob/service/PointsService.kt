package com.skplanet.bob.service

import com.skplanet.bob.api.model.PointsResponse
import reactor.core.publisher.Mono

interface PointsService {
    suspend fun getPointsByRange(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double): Mono<PointsResponse>
    suspend fun getPointsByCenter(x: Double, y: Double): Mono<PointsResponse>
}