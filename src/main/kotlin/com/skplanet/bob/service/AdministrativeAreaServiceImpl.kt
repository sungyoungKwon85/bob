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

        return findByType.doOnNext {
            result.areas.add(it)
            result.count++
        }.then(Mono.just(result))
    }

    override fun getSggs(sdId: String): Mono<AdministrativeAreaResponse> {
        val result = AdministrativeAreaResponse()
        val findByTypeAndSdId = administrativeAreaRepository.findByTypeAndSdId("sgg", sdId)

        return findByTypeAndSdId.doOnNext {
            result.areas.add(it)
            result.count++
        }.then(Mono.just(result))
    }

    override fun getUmds(sggId: String): Mono<AdministrativeAreaResponse> {
        val result = AdministrativeAreaResponse()
        val findByTypeAndSggId = administrativeAreaRepository.findByTypeAndSggId("umd", sggId)
        return findByTypeAndSggId.doOnNext {
            result.areas.add(it)
            result.count++
        }.then(Mono.just(result))
    }

    override fun getByUmdId(umdId: String): Mono<AdministrativeArea> {
        return administrativeAreaRepository.findByUmdId(umdId)
    }
}