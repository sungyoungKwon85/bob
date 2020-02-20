package com.skplanet.bob.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.LocalTime

@Document(collection = "Restaurants")
data class Restaurant(
        var id: String?,
        var name: String = "",
        var type: String? = "RESTAURANT",
        var address: Address? = null,
        var location: Location? = null,
        var phone: String = "",
        var schedule: Schedule? = null,
        var government: Government? = null,
        var updatedAt: LocalDateTime? = null,
        var registrationType: String = "NULLBOB",
        var status: String = "OPEN"

) {
    data class Address(
            var state: String = "",
            var city: String = "",
            var zipcode: String = "",
            var asRoad: String = "",
            var asArea: String = ""
    )

    data class Location(
            var type: String = "",
            var coordinates: List<Double>? = null
    )

    data class Schedule(
            var open_at: LocalTime? = null,
            var close_at: LocalTime? = null
    )

    data class Government(
            var name: String = "",
            var source: String = "",
            var phone: String = "",
            var code: String = ""
    )
}

