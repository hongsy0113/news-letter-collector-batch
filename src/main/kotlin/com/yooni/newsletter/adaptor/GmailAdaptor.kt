package com.yooni.newsletter.adaptor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.Exception

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

    fun callGetMailListAPI(accessToken: String): GetMailListResponseDto {
        try {
            val headers = HttpHeaders()
            headers.set("Authorization", "Bearer $accessToken")
            val requestEntity = HttpEntity<String>(headers)

            val response: ResponseEntity<GetMailListResponseDto> =
                restTemplate.exchange(
                    "$gmailApiHost/gmail/v1/users/$userId/messages",
                    HttpMethod.GET,
                    requestEntity,
                    GetMailListResponseDto::class.java,
                    null
                )
            println(response)
            return response.body ?: GetMailListResponseDto()
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }

    fun callGetMailAPI(mailId: String, accessToken: String): GetMailResponseDto {
        try {
            val headers = HttpHeaders()
            headers.set("Authorization", "Bearer $accessToken")
            val requestEntity = HttpEntity<String>(headers)

            val response: ResponseEntity<GetMailResponseDto> =
                restTemplate.exchange(
                    "$gmailApiHost/gmail/v1/users/$userId/messages/$mailId",
                    HttpMethod.GET,
                    requestEntity,
                    GetMailResponseDto::class.java,
                    null
                )
            println(response)
            return response.body ?: GetMailResponseDto()
        } catch (e: Exception) {
            println(e)
            throw e
        }
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
