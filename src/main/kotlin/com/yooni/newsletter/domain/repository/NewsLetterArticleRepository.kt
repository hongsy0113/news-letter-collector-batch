package com.yooni.newsletter.domain.repository

import com.yooni.newsletter.domain.entity.NewsLetterArticle
import org.springframework.data.repository.CrudRepository

interface NewsLetterArticleRepository : CrudRepository<NewsLetterArticle, Long> {
    fun findByExternalId(externalId: String): NewsLetterArticle?
}