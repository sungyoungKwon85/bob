package com.skplanet.bob.repository

import com.skplanet.bob.model.Restaurant
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import javax.annotation.PostConstruct

@Repository
class RestaurantRepository(private val template: ReactiveMongoTemplate) {

    companion object {
        val initRestaurants = listOf(
                Restaurant(null, "김밥천국CAFE", null, null, 36.41580788, 128.1574469),
                Restaurant(null, "김밥친구", null, null, 36.28353135, 128.0929339),
                Restaurant(null, "놀러와김밥", null, null, 36.41275108, 128.1582132)
        )
    }

    @PostConstruct
    fun init() =
            initRestaurants.map(Restaurant::toMono).map(this::create).map(Mono<Restaurant>::subscribe)

    fun create(restaurant: Mono<Restaurant>) = template.save(restaurant)
    fun findById(id: String) = template.findById<Restaurant>(id)

}