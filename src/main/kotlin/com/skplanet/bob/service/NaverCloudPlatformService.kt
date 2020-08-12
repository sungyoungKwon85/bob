package com.skplanet.bob.service

import com.skplanet.bob.api.model.NaverGeocodeResponse
import com.skplanet.bob.config.NaverProperties
import kotlinx.coroutines.reactive.awaitFirst
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.Duration

@Slf4j
@Service
class NaverCloudPlatformService(
        val naverProperties: NaverProperties,
        val builder: WebClient.Builder
) {

    suspend fun geocode(query: String): NaverGeocodeResponse? {
        return builder.baseUrl("https://naveropenapi.apigw.ntruss.com")
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", naverProperties.clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", naverProperties.clientSecret)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build().get()
                .uri { uriBuilder ->
                    uriBuilder
                            .path("/map-geocode/v2/geocode")
                            .queryParam("query", query)
                            .build()
                }
                .retrieve()
                .bodyToMono(NaverGeocodeResponse::class.java)
                .awaitFirst()

    }
}