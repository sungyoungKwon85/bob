package com.skplanet.bob.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "point")
data class Point(
        var id: String,
        @Indexed
        var z: Int = 0,
        @Indexed
        var areaName: String = "",
        @Indexed
        var areaId: String = "",
        var count: Int = 0,
        @Indexed
        var location: Restaurant.Location = Restaurant.Location(),
        var ids: List<String> = ArrayList()
) {
}