package com.skplanet.bob.repository

import com.skplanet.bob.model.Point
import org.springframework.data.geo.Box
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class PointRepository(private val template: ReactiveMongoTemplate) {

    fun findById(id: String) = template.findById<Point>(id)
    fun findByAreaName(areaName: String): Flux<Point> {
        val query = Query()
        query.addCriteria(Criteria.where("areaName").`is`(areaName))
        return template.find(query, Point::class.java)
    }

    fun findByZ(z: Int): Flux<Point> {
        val query = Query()
        query.addCriteria(Criteria.where("z").`is`(z))
        return template.find(query, Point::class.java)
    }

    fun findByZAndBox(box: Box, z: Int): Flux<Point> {
        val query = Query()
        query.addCriteria(Criteria.where("z").`is`(z).and("location").within(box))
        return template.find(query, Point::class.java)
    }

    fun findByZAndAreaId(z: Int, areaId: String): Flux<Point> {
        val query = Query()
        query.addCriteria(Criteria.where("z").`is`(z).and("areaId").`is`(areaId))
        return template.find(query, Point::class.java)
    }
}

