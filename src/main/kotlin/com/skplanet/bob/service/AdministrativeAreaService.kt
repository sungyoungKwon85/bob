package com.skplanet.bob.service

import com.skplanet.bob.api.model.AdministrativeAreaResponse
import reactor.core.publisher.Mono

interface AdministrativeAreaService {
    fun getSds(): Mono<AdministrativeAreaResponse>
    fun getSggs(sdId: String): Mono<AdministrativeAreaResponse>
    fun getUmds(sggId: String): Mono<AdministrativeAreaResponse>
}