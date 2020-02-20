package com.skplanet.bob.api.model

data class PointsResponse(
        val points: MutableList<Point> = ArrayList(),
        var totalCount: Long? = 0
) {
    data class Point(
            var latitude: Double? = 0.0,
            var longitude: Double? = 0.0,
            var count: Long? = 0
    )
}