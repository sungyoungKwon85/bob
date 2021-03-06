package com.skplanet.bob.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.LocalTime

@Document(collection = "restaurant")
data class Restaurant(
        var id: String,
        var name: String = "",
        var type: String = "00",
        var address: Address = Address(),
        var location: Location = Location(),
        var phone: String = "",
        var schedule: Schedule = Schedule(),
        var updatedAt: LocalDateTime?,
        var registrationType: String = "UNBOB",
        var status: String = "CLOSED"

) {
    data class Address(
            var sg: String = "",
            var sgg: String = "",
            var zipcode: String = "",
            var asRoad: String = "",
            var asArea: String = ""
    )

    data class Location(
            var type: String = "",
            var coordinates: List<Double> = ArrayList()
    )

    data class Schedule(
            var weekdayStartAt: LocalTime? = null,
            var weekdayEndAt: LocalTime? = null,
            var saturdayStartAt: LocalTime? = null,
            var saturdayEndAt: LocalTime? = null,
            var holidayStartAt: LocalTime? = null,
            var holidayEndAt: LocalTime? = null,
            var deliveryStartAt: LocalTime? = null,
            var deliveryEndAt: LocalTime? = null
    )
}

