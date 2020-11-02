package com.skplanet.bob.api.model

import com.skplanet.bob.model.Restaurant

data class RestaurantsResponse(
        var restaurants: MutableList<Restaurant> = ArrayList(),
        var count: Int = 0,
        var totalCount: Int = 0
)
