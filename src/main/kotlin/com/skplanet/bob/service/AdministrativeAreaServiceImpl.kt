package com.skplanet.bob.service

import com.skplanet.bob.api.model.AdministrativeAreaResponse
import com.skplanet.bob.model.AdministrativeArea
import com.skplanet.bob.repository.AdministrativeAreaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AdministrativeAreaServiceImpl : AdministrativeAreaService {

    @Autowired
    lateinit var administrativeAreaRepository: AdministrativeAreaRepository

    override fun getSds(): Mono<AdministrativeAreaResponse> {
        val result = AdministrativeAreaResponse()
        val findByType = administrativeAreaRepository.findByType("sd")
        findByType.subscribe {
            result.areas.add(it)
            result.count++
        }
        return Mono.just(result)
    }

    override fun getSggs(sdId: String): Mono<AdministrativeAreaResponse> {
        val result = AdministrativeAreaResponse()
        val findByTypeAndSdId = administrativeAreaRepository.findByTypeAndSdId("sgg", sdId)
        findByTypeAndSdId.subscribe {
            result.areas.add(it)
            result.count++
        }
        return Mono.just(result)
    }

    override fun getUmds(sggId: String): Mono<AdministrativeAreaResponse> {
        val result = AdministrativeAreaResponse()
        val findByTypeAndSggId = administrativeAreaRepository.findByTypeAndSggId("umd", sggId)
        findByTypeAndSggId.subscribe {
            result.areas.add(it)
            result.count++
        }
        return Mono.just(result)
    }

    override fun getByUmdId(umdId: String): Mono<AdministrativeArea> {
        return administrativeAreaRepository.findByUmdId(umdId)
    }
}