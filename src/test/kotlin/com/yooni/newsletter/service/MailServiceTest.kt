package com.yooni.newsletter.service

import com.yooni.newsletter.type.NewsLetterType
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MailServiceTest @Autowired constructor(
    private val mailService: MailService
) {
    @Test
    fun `메일함에 모든 메일 목록을 조회한다`() {
        // given

        // when
        val actual = mailService.getMailList()

        // then
        actual shouldNotBe null
        println(actual)
    }

    @Test
    fun `메일함에서 특정 라벨을 가진 메일 목록을 조회한다`() {
        // given
        val labelId = "UNREAD"

        // when
        val actual = mailService.getMailList(labelIds = listOf(labelId))

        // then
        actual shouldNotBe null
        println(actual)
    }

    @Test
    fun `특정 메일 내용을 조회한다`() {
        // given
        val mailId = "18889be0c9804993"

        // when
        val actual = mailService.getMailContent(mailId)

        // then
        actual shouldNotBe null
        println(actual)
    }

    @Test
    fun `어떤 뉴스레터인지 조회한다`() {
        // given
        val mailId = "18889be0c9804993"

        // when
        val actual = mailService.getNewsLetterType(mailId)

        // then
        actual shouldNotBe null
        actual.shouldBeTypeOf<NewsLetterType>()
        println(actual)
    }

    @Test
    fun `뉴스레터 저장에 필요한 정보를 조회한다`() {
        // given
        val mailId = "18897d2bc55101ca"

        // when
        val newsLetterMailData = mailService.getNewsLetterMailData(mailId)

        // then
        println(newsLetterMailData)
    }
}
