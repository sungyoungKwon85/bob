package com.skplanet.bob.repository

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class PointsRepository(private val template: ReactiveMongoTemplate) {
    fun getPointsByRange() {
        val query = Query();

    }
}