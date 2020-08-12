package com.skplanet.bob.repository

import com.skplanet.bob.model.AdministrativeArea
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class AdministrativeAreaRepository(private val template: ReactiveMongoTemplate) {
    fun findByType(type: String): Flux<AdministrativeArea> {
        val query = Query()
        query.addCriteria(Criteria.where("type").`is`(type))
        return template.find(query, AdministrativeArea::class.java)
    }

    fun findByTypeAndSdId(type: String, sdId: String): Flux<AdministrativeArea> {
        val query = Query()
        query.addCriteria(Criteria.where("type").`is`(type).and("sdId").`is`(sdId))
        return template.find(query, AdministrativeArea::class.java)
    }

    fun findByTypeAndSggId(type: String, sggId: String): Flux<AdministrativeArea> {
        val query = Query()
        query.addCriteria(Criteria.where("type").`is`(type).and("sggId").`is`(sggId))
        return template.find(query, AdministrativeArea::class.java)
    }

    fun findByUmdId(umdId: String): Mono<AdministrativeArea> {
        val query = Query()
        query.addCriteria(Criteria.where("type").`is`("umd").and("id").`is`(umdId))
        return template.findOne(query, AdministrativeArea::class.java)
    }
}