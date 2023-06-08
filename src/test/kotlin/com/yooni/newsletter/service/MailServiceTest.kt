package com.yooni.newsletter.service

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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
    fun `메일 종류와 함께 메일 타입을 조회한다`() {
        // given
        val mailId = "18897d2bc55101ca"

        // when
        val (mailType, mailContent) = mailService.getMailTypeAndContent(mailId)

        // then
        mailType.isNewsLetter() shouldBe true
        println(mailType)
        println(mailContent)
    }
}
