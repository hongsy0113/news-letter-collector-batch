package com.yooni.newsletter.domain.repository

import com.yooni.newsletter.domain.entity.NewsLetter
import org.springframework.data.repository.CrudRepository

interface NewsLetterRepository : CrudRepository<NewsLetter, Long> {
    fun findByExternalId(externalId: String): NewsLetter?
}