package com.yooni.newsletter.domain.repository

import com.yooni.newsletter.domain.entity.NewsLetterInfo
import org.springframework.data.repository.CrudRepository

interface NewsLetterInfoRepository : CrudRepository<NewsLetterInfo, Long> {
    fun findByExternalId(externalId: String): NewsLetterInfo?
    fun findByLabelId(labelId: String): NewsLetterInfo?
}