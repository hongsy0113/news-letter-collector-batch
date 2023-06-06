package com.yooni.newsletter.adaptor

import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GmailAdaptorTest @Autowired constructor(
    private val gmailAdaptor: GmailAdaptor
) {
    @Test
    fun `메일 리스트 조회 API를 호출한다`() {
        // given
        val accessToken = "ya29.a0AWY7CkmzhnOvN4hbhXWmwF4zm_EC0DVwds0efoMl_3e63Pb1D5jwaoBgb7erHaxygAirg9gdvU2QgzZcBnEohWV5ijq8abV6becekd9m8HbvCOO5NhUURFXNVYi2wU-wcX55lSxFM2ORup9-dGY52V_I-qtWaCgYKAYwSARISFQG1tDrpMqbd624BRObOeNH1KGsDDQ0163"

        // when
        val actual = gmailAdaptor.callGetMailListAPI(accessToken = accessToken)

        // then
        actual shouldNotBe null
        println(actual)
    }

    @Test
    fun `메일 단건 조회 API를 호출한다`() {
        // given
        val accessToken = "ya29.a0AWY7CkmzhnOvN4hbhXWmwF4zm_EC0DVwds0efoMl_3e63Pb1D5jwaoBgb7erHaxygAirg9gdvU2QgzZcBnEohWV5ijq8abV6becekd9m8HbvCOO5NhUURFXNVYi2wU-wcX55lSxFM2ORup9-dGY52V_I-qtWaCgYKAYwSARISFQG1tDrpMqbd624BRObOeNH1KGsDDQ0163"
        val mailId = "1888497ada8cd377"

        // when
        val actual = gmailAdaptor.callGetMailAPI(mailId = mailId, accessToken = accessToken)

        // then
        actual shouldNotBe null
        println(actual)
    }
}
