package com.yooni.newsletter.domain.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NewsLetterRepositoryTest @Autowired constructor(
    private val newsLetterRepository: NewsLetterRepository
) {
    @Test
    fun `newsletter entity를 조회한다`() {
        // given
        val newsLetterId = 1L

        // when
        val actual = newsLetterRepository.findById(newsLetterId)

        // then
        println(actual)

    }
}