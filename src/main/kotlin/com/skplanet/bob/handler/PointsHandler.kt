package com.skplanet.bob.handler

import com.skplanet.bob.service.PointsService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status

@Component
class PointsHandler(val pointsService: PointsService) {
    suspend fun getPointsByRange(serverRequest: ServerRequest) {
        pointsService.getPointsByRange(
                serverRequest.queryParam("lat_bl").get().toDouble(),
                serverRequest.queryParam("lon_bl").get().toDouble(),
                serverRequest.queryParam("lat_tr").get().toDouble(),
                serverRequest.queryParam("lonTr").get().toDouble(),
                serverRequest.queryParam("level").get().toInt())
                .flatMap { ok().body(fromObject(it)) }
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                .awaitFirstOrNull()
    }
}