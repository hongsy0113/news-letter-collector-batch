package com.yooni.newsletter.helper

import org.junit.jupiter.api.Test

class DateTimeFormatHelperTest {

    @Test
    fun `rfc 포맷 형식의 String을 LocalDateTime으로 변환한디`() {
        // given
        val receivedAtString = "Fri, 09 Jun 2023 06:11:16 +0900"

        // when
        val actual = receivedAtString.convertToLocalDateTime()

        // then
        println(actual)
    }
}