package com.yooni.newsletter.service

import com.yooni.newsletter.domain.repository.NewsLetterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class NewsLetterDataServiceTest {

    @Autowired
    private lateinit var repository: NewsLetterRepository

}