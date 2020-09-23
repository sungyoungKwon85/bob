package com.skplanet.bob.repository

import com.skplanet.bob.model.Restaurant
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.geo.Box
import org.springframework.data.geo.Circle
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class RestaurantRepository(private val template: ReactiveMongoTemplate) {

    fun create(restaurant: Mono<Restaurant>) = template.save(restaurant)

    fun findById(id: String) = template.findById<Restaurant>(id)

    fun findByName(name: String): Mono<Restaurant> {
        val query = Query()
        query.addCriteria(Criteria.where("name").`is`(name))
        return template.findOne(query, Restaurant::class.java)
    }

    fun getCountGeoWithin(latitude: Double, longitude: Double, distance: Double): Mono<Long> {
        val query = Query()
        val circle = Circle(latitude, longitude, distance / 6378.1)
        query.addCriteria(Criteria.where("location").`withinSphere`(circle))
        return template.count(query, "restaurant")
    }

    fun getGeoWithinBySquare(bl: Point, tr: Point, pageable: Pageable): Flux<Restaurant> {
        val query = Query().with(pageable)
        val box = Box(bl, tr)
        query.addCriteria(Criteria.where("location").`within`(box))
        return template.find(query, Restaurant::class.java)
    }

    fun getCountGeoWithinBySquare(bl: Point, tr: Point): Mono<Long> {
        val query = Query()
        val box = Box(bl, tr)
        query.addCriteria(Criteria.where("location").`within`(box))
        return template.count(query, "restaurant")
    }

}

