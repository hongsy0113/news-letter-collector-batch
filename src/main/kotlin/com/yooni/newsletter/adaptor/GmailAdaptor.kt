package com.yooni.newsletter.adaptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder


@Component
class GmailAdaptor(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    @Value("\${gmail.domain.gmail-auth}")
    private val gmailAuthHost: String,
    @Value("\${gmail.domain.gmail-api}")
    private val gmailApiHost: String,
    @Value("\${gmail.client-id}")
    private val clientId: String,
    @Value("\${gmail.user-id}")
    private val userId: String,
    @Value("\${gmail.access-token}")
    private val accessToken: String,
) {
//    fun callTest() {
//        try {
//            val response: ResponseEntity<String> = restTemplate.getForEntity(
//                "$gmailAuthHost/o/oauth2/v2/auth" +
//                    "?scope=https://www.googleapis.com/auth/gmail.readonly" +
//                    "&access_type=offline" +
//                    "&include_grant_scopes=true" +
//                    "&state=state_parameter_passthrough_value" +
//                    "&redirect_url=http://localhost:8080/receivablecode.html" +
//                    "&client_id=$clientId",
//                String::class.java
//            )
//            println(response)
//        } catch (e: Exception) {
//            println(e)
//            throw e
//        }
//    }

    private val authorizationHeader: HttpHeaders
        get() = HttpHeaders().also { it.set("Authorization", "Bearer $accessToken") }

    fun callGetMailListAPI(labelIds: List<String> = emptyList()): GetMailListResponseDto {
        try {
            val requestEntity = HttpEntity<String>(authorizationHeader)

            val url = UriComponentsBuilder
                .fromHttpUrl("$gmailApiHost/gmail/v1/users/$userId/messages")
                .labelIdQueryParams(labelIds)
                .build()
                .toString()

            val response: ResponseEntity<GetMailListResponseDto> =
                restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    GetMailListResponseDto::class.java,
                    null
                )
            return response.body ?: GetMailListResponseDto()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    fun callGetMailAPI(mailId: String): GetMailResponseDto {
        try {
            val requestEntity = HttpEntity<String>(authorizationHeader)

            val response: ResponseEntity<GetMailResponseDto> =
                restTemplate.exchange(
                    "$gmailApiHost/gmail/v1/users/$userId/messages/$mailId",
                    HttpMethod.GET,
                    requestEntity,
                    GetMailResponseDto::class.java,
                    null
                )
            return response.body ?: GetMailResponseDto()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    fun callGetMailLabelIdsAPI(mailId: String): GetMailLabelIdsResponseDto {
        try {
            val requestEntity = HttpEntity<String>(authorizationHeader)

            val response: ResponseEntity<GetMailLabelIdsResponseDto> =
                restTemplate.exchange(
                    "$gmailApiHost/gmail/v1/users/$userId/messages/$mailId?format=${GetMailAPIFormat.MINIMAL}",
                    HttpMethod.GET,
                    requestEntity,
                    GetMailLabelIdsResponseDto::class.java,
                    null
                )
            return response.body ?: GetMailLabelIdsResponseDto(mailId)
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    fun callModifyMailAPI(mailId: String, requestDto: ModifyMailRequestDto): ModifyMailResponseDto {
        try {
            val requestEntity = HttpEntity<ModifyMailRequestDto>(requestDto, authorizationHeader)

            val response: ResponseEntity<ModifyMailResponseDto> =
                restTemplate.exchange(
                    "$gmailApiHost/gmail/v1/users/$userId/messages/$mailId/modify",
                    HttpMethod.POST,
                    requestEntity,
                    ModifyMailResponseDto::class.java,
                    null
                )
            return response.body ?: ModifyMailResponseDto()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    private fun UriComponentsBuilder.labelIdQueryParams(labelIds: List<String>): UriComponentsBuilder {
        for (labelId in labelIds) {
            this.queryParam("labelIds", labelId)
        }
        return this
    }
}

data class GetMailListResponseDto(
    val resultSizeEstimate: Long = 0,
    val messages: List<Message> = emptyList()
) {
    data class Message(
        val id: String,
        val threadId: String,
    )
}

data class GetMailResponseDto(
    val id: String? = null,
    val threadId: String? = null,
    val labelIds: List<String> = emptyList(),
    val snippet: String? = null,
    val payload: Payload? = null,
    val sizeEstimate: Long = 0,
    val historyId: String? = null,
    val internalDate: String? = null,
) {
    data class Payload(
        val mimeType: String? = null,
        val headers: List<Header>,
        val body: Body,
    ) {
        data class Header(
            val name: String? = null,
            val value: String? = null,
        )

        data class Body(
            val size: Long = 0,
            val data: String? = null,
        )
    }
}

data class GetMailLabelIdsResponseDto(
    val id: String,
    val labelIds: List<String> = emptyList()
)

data class ModifyMailRequestDto(
    val addLabelIds: List<String> = emptyList(),
    val removeLabelIds: List<String> = emptyList(),
)

data class ModifyMailResponseDto(
    val id: String? = null,
    val threadId: String? = null,
    val labelIds: List<String> = emptyList(),
)

enum class GetMailAPIFormat {
    MINIMAL, FULL, RAW, METADATA
}