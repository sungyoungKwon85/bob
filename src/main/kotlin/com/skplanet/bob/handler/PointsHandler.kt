package com.skplanet.bob.handler

import com.skplanet.bob.service.PointsService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status

@Component
class PointsHandler(val pointsService: PointsService) {

    suspend fun getPointsBy(serverRequest: ServerRequest): ServerResponse {
        return when (serverRequest.queryParam("type").orElse("")) {
            "area" -> {
                pointsService.getPointsByUmdId(serverRequest.queryParam("umdId").get())
                        .flatMap { ok().body(fromValue(it)) }
                        .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                        .awaitFirst()
            }
            else -> {
                pointsService.getPointsByRange(
                        serverRequest.queryParam("lat_bl").get().toDouble(),
                        serverRequest.queryParam("lon_bl").get().toDouble(),
                        serverRequest.queryParam("lat_tr").get().toDouble(),
                        serverRequest.queryParam("lon_tr").get().toDouble(),
                        serverRequest.queryParam("z").get().toInt())
                        .flatMap { ok().body(fromValue(it)) }
                        .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                        .awaitFirst()
            }
        }
    }

}