package com.skplanet.bob.api.model

data class PointsResponse(
        var points: MutableList<Point> = ArrayList(),
        var totalCount: Long = 0
) {
    data class Point(
            var latitude: Double? = 0.0,
            var longitude: Double? = 0.0,
            var count: Long = 0,
            var ids: List<String> = ArrayList()
    )
}