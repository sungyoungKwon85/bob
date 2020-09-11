package com.skplanet.bob.handler

import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class ProfileHandler(val env: Environment) {

    fun profile(serverRequest: ServerRequest): Mono<ServerResponse> {
        val profiles = env.activeProfiles.toList()
        val devs = arrayListOf("dev", "dev1", "dev2")
        val defaultProfile:String = if (profiles.isEmpty()) {
            "default"
        } else {
            profiles[0]
        }
        val profile = profiles.stream().filter(devs::contains).findAny().orElse(defaultProfile)
        return Mono.just(profile)
                .flatMap { ok().body(BodyInserters.fromValue(it)) }
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())
    }
}