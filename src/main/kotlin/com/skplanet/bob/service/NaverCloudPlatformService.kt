package com.skplanet.bob.service

import com.skplanet.bob.api.model.NaverGeocodeResponse
import com.skplanet.bob.api.model.NaverReverseGeocodeResponse
import com.skplanet.bob.config.NaverProperties
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient

@Slf4j
@Service
class NaverCloudPlatformService(
        val naverProperties: NaverProperties,
        val builder: WebClient.Builder
) {

    companion object {
        const val GEO_URL = "https://naveropenapi.apigw.ntruss.com"
    }

    init {
        builder.baseUrl(GEO_URL)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", naverProperties.clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", naverProperties.clientSecret)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .clientConnector(ReactorClientHttpConnector(
                        HttpClient.create().wiretap(true)
                ))
    }

    fun geocode(query: String): Mono<NaverGeocodeResponse> {
        return builder
                .build().get()
                .uri { uriBuilder ->
                    uriBuilder
                            .path("/map-geocode/v2/geocode")
                            .queryParam("query", query)
                            .build()
                }
                .retrieve()
                .bodyToMono(NaverGeocodeResponse::class.java)
    }

    fun reverseGeocode(lon: Double, lat: Double): Mono<NaverReverseGeocodeResponse> {
        val coords = "$lon,$lat"
        return builder
                .build().get()
                .uri { uriBuilder ->
                    uriBuilder
                            .path("/map-reversegeocode/v2/gc")
                            .queryParam("output", "json")
                            .queryParam("orders", "admcode")
                            .queryParam("coords", coords)
                            .build()
                }
                .retrieve()
                .bodyToMono(NaverReverseGeocodeResponse::class.java)
    }
}