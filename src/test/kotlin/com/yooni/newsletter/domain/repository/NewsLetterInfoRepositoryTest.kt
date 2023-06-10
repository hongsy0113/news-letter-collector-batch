package com.yooni.newsletter.domain.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NewsLetterInfoRepositoryTest @Autowired constructor(
    private val newsLetterInfoRepository: NewsLetterInfoRepository
) {
    @Test
    fun `newsletter entity를 조회한다`() {
        // given
        val newsLetterInfoId = 1L

        // when
        val actual = newsLetterInfoRepository.findById(newsLetterInfoId)

        // then
        println(actual)

    }
}