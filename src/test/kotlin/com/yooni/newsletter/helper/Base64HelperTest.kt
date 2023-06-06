package com.yooni.newsletter.helper

import org.junit.jupiter.api.Test
import java.util.*

class Base64HelperTest(
) {
    private val base64Helper = Base64Helper()

    @Test
    fun `Base64 encoder decoder test`() {
        val originalInput = "테스트"
        val encodedString: String = Base64.getEncoder().encodeToString(originalInput.toByteArray())

        println(encodedString)

        val decodedBytes = Base64.getDecoder().decode(encodedString)
        val decodedString = String(decodedBytes)

        println(decodedString)
    }

    @Test
    fun `base64 helper 클래스의 디코드 함수를 사용한다`() {
        // given
        val encodedString = "PCFET0NUWVBFIGh0bWw-PGh0bWw-PGltZyBzcmM9Imh0dHBzOi8vZXZlbnQuc3RpYmVlLmNvbS92Mi"

        // when
        val actual = base64Helper.decode(encodedString)

        // then
        println(actual)
    }
}