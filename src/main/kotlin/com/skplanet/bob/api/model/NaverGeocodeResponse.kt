package com.skplanet.bob.api.model

data class NaverGeocodeResponse(
        var status: String,
        var meta: Meta,
        var addresses: List<Address> = ArrayList(),
        var errorMessage: String
) {
    class Address {
        lateinit var roadAddress: String
        lateinit var jibunAddress: String
        lateinit var englishAddress: String
        var addressElements: List<AddressElement> = ArrayList()
        lateinit var x: String
        lateinit var y: String
        var distance: Double = 0.0

    }

    class AddressElement {
        var types: List<String> = ArrayList()
        lateinit var longName: String
        lateinit var shortName: String
        lateinit var code: String
    }

    class Meta {
        var totalCount: Int = 0
        var page: Int = 0
        var count: Int = 0
    }
}