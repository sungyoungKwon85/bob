package com.skplanet.bob.service

import com.skplanet.bob.api.model.PointsResponse
import reactor.core.publisher.Mono

interface PointsService {
    suspend fun getPointsByRange(latBl: Double, lonBl: Double, latTr: Double, lonTr: Double, z: Int): Mono<PointsResponse>
    suspend fun getPointsByArea(sggId: String, umdId: String, z: Int): Mono<PointsResponse>
}