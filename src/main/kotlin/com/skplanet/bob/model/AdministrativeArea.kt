package com.skplanet.bob.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "administrativeArea")
data class AdministrativeArea(
        @Id
        var id: String,
        var name: String = "",
        var type: String = "",
        var sdId: String = "",
        var sdName: String = "",
        var sggId: String = "",
        var sggName: String = "",
        var location: Restaurant.Location = Restaurant.Location()
)
