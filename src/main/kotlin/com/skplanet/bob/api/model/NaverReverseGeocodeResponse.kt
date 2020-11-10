package com.skplanet.bob.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverReverseGeocodeResponse(

        @JsonProperty("status")
        val status: Status = Status(),
        @JsonProperty("results")
        val results: List<Results> = ArrayList()
) {
    data class Results(
            @JsonProperty("name")
            val name: String = "",
            @JsonProperty("code")
            val code: Code = Code(),
            @JsonProperty("region")
            val region: Region = Region()
    )

    data class Region(
            @JsonProperty("area0")
            val area0: Area = Area(),
            @JsonProperty("area1")
            val area1: Area = Area(),
            @JsonProperty("area2")
            val area2: Area = Area(),
            @JsonProperty("area3")
            val area3: Area = Area(),
            @JsonProperty("area4")
            val area4: Area = Area()
    )

    data class Coords(
            @JsonProperty("center")
            val center: Center = Center()
    )

    data class Center(
            @JsonProperty("crs")
            val crs: String = "",
            @JsonProperty("x")
            val x: Double = 0.0,
            @JsonProperty("y")
            val y: Double = 0.0
    )

    data class Area(
            @JsonProperty("name")
            val name: String = "",
            @JsonProperty("coords")
            val coords: Coords = Coords(),
            @JsonProperty("alias")
            val alias: String = ""
    )


    data class Code(
            @JsonProperty("id")
            val id: String = "",
            @JsonProperty("type")
            val type: String = "",
            @JsonProperty("mappingId")
            val mappingId: String = ""
    )

    data class Status(
            @JsonProperty("code")
            val code: Int = 0,
            @JsonProperty("name")
            val name: String = "",
            @JsonProperty("message")
            val message: String = ""
    )
}

