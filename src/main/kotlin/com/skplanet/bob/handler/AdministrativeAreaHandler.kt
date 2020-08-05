package com.skplanet.bob.handler

import com.skplanet.bob.service.AdministrativeAreaService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status

@Component
class AdministrativeAreaHandler(val administrativeAreaService: AdministrativeAreaService) {

    fun getSds(serverRequest: ServerRequest) =
            administrativeAreaService.getSds()
                    .flatMap { ok().body(BodyInserters.fromValue(it)) }
                    .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun getSggs(serverRequest: ServerRequest) =
            administrativeAreaService.getSggs(serverRequest.pathVariable("sdId"))
                    .flatMap { ok().body(BodyInserters.fromValue(it)) }
                    .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
//                    .awaitFirst()

    fun getUmds(serverRequest: ServerRequest) =
            administrativeAreaService.getUmds(serverRequest.pathVariable("sggId"))
                    .flatMap { ok().body(BodyInserters.fromValue(it)) }
                    .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
//                    .awaitFirst()

}