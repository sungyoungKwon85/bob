package com.skplanet.bob.api.model

import com.skplanet.bob.model.AdministrativeArea

data class AdministrativeAreaResponse(
        var areas: MutableList<AdministrativeArea> = ArrayList(),
        var count: Int = 0
)