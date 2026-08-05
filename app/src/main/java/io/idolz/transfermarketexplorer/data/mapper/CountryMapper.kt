package io.idolz.transfermarketexplorer.data.mapper

import io.idolz.transfermarketexplorer.data.local.entity.CountryEntity
import io.idolz.transfermarketexplorer.data.remote.dto.CountryDto
import io.idolz.transfermarketexplorer.domain.model.Country

fun CountryDto.toCountryEntity(): CountryEntity {
    return CountryEntity(
        id = id,
        name = name,
        flagUrl = flag
    )
}

fun CountryEntity.toCountry(): Country {
    return Country(
        id = id,
        name = name,
        flagUrl = flagUrl
    )
}