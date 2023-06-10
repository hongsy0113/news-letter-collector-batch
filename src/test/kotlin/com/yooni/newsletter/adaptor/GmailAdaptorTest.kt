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
    fun `특정 라벨을 가진 메일 리스트만 조회한다`() {
        // given
        val labelIds = listOf("Label_5055091156381294150")

        // when
        val actual = gmailAdaptor.callGetMailListAPI(labelIds = labelIds)

        // then
        actual shouldNotBe null
        println(actual)
    }

    @Test
    fun `메일 단건 조회 API를 호출한다`() {
        // given
        val mailId = "1888497ada8cd377"

        // when
        val actual = gmailAdaptor.callGetMailAPI(mailId = mailId)

        // then
        actual shouldNotBe null
        println(actual)
    }

    @Test
    fun `메일의 메타데이터만 조회할 수 있다`() {
        // given
        val mailId = "1888497ada8cd377"

        // when
        val actual = gmailAdaptor.callGetMailLabelIdsAPI(mailId = mailId)

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
