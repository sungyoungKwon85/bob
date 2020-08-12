package com.skplanet.bob.handler

import com.skplanet.bob.service.AdministrativeAreaService
import com.skplanet.bob.service.NaverCloudPlatformService
import com.skplanet.bob.service.PointsService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status

@Component
class PointsHandler(
        val pointsService: PointsService,
        val administrativeAreaService: AdministrativeAreaService,
        val naverCloudPlatformService: NaverCloudPlatformService
) {
    suspend fun getPointsByRange(serverRequest: ServerRequest): ServerResponse {
        return when (serverRequest.queryParam("type").orElse("")) {
            "area" -> {
                val umdName = serverRequest.queryParam("umdName").get()
                val geocode = naverCloudPlatformService.geocode(umdName)
                pointsService.getPointsByCenter(
                        geocode?.addresses!![0].x.toDouble(), geocode?.addresses!![0].y.toDouble())
                        .flatMap { ok().body(BodyInserters.fromValue(it)) }
                        .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                        .awaitFirst()
            }
            else -> {
                pointsService.getPointsByRange(
                        serverRequest.queryParam("lat_bl").get().toDouble(),
                        serverRequest.queryParam("lon_bl").get().toDouble(),
                        serverRequest.queryParam("lat_tr").get().toDouble(),
                        serverRequest.queryParam("lon_tr").get().toDouble(),
                        serverRequest.queryParam("level").get().toInt())
                        .flatMap { ok().body(fromValue(it)) }
                        .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                        .awaitFirst()
            }
        }
    }

}