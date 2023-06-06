package com.yooni.newsletter.adaptor

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GmailAdaptorTest @Autowired constructor(
    private val gmailAdaptor: GmailAdaptor,
) {
    private val accessToken: String = "ya29.a0AWY7Ckk4v_A5j2iO-lnzW6GTqn43CtUzYGm_3DHbBveJCmryWo9X92bTaw535wQ5fhHrB_tv9Cv1g-ExqkPGjQvJfsrJIw1S84yoXnQTt8TbwUQ1EqITUWHhgEWO1JgUjcQQZjCtF0n2I_Z65HBwxw7jH828aCgYKAa8SARISFQG1tDrp65kfeFcLmqv0ZOlWVe3Bng0163"
    @Test
    fun `메일 리스트 조회 API를 호출한다`() {
        // given

        // when
        val actual = gmailAdaptor.callGetMailListAPI()

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
        val actual = gmailAdaptor.callGetMailAPI(mailId = mailId)

        // then
        actual shouldNotBe null
        println(actual)
    }

    @Test
    fun `메일 수정 API를 호출한다`() {
        // given
        val mailId = "1888488daacc6723"
        val labelIdToAdd = "Label_5055091156381294150"
        val labelIdToRemove = "Label_2611734388845633742"
        val requestDto = ModifyMailRequestDto(
            addLabelIds = listOf(labelIdToAdd),
            removeLabelIds = listOf(labelIdToRemove)
        )

        // when
        val actual = gmailAdaptor.callModifyMailAPI(mailId = mailId, requestDto = requestDto)

        // then
        actual shouldNotBe null
        actual.labelIds shouldContain labelIdToAdd
        actual.labelIds shouldNotContain labelIdToRemove
        println(actual)
    }
}
