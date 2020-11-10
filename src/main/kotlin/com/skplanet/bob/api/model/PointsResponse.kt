package com.skplanet.bob.api.model

data class PointsResponse(
        var points: MutableList<Point> = ArrayList(),
        var totalCount: Long = 0
) {
    data class Point(
            var id: String,
            var z: Int = 0,
            var areaName: String = "",
            var areaId: String = "",
            var latitude: Double = 0.0,
            var longitude: Double = 0.0,
            var count: Int = 0
    )
}