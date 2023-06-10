package com.yooni.newsletter.service

import com.yooni.newsletter.domain.entity.NewsLetter
import com.yooni.newsletter.domain.repository.NewsLetterInfoRepository
import com.yooni.newsletter.domain.repository.NewsLetterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class NewsLetterDataService(
    private val newsLetterRepository: NewsLetterRepository,
    private val newsLetterInfoRepository: NewsLetterInfoRepository
) {
    @Transactional
    fun saveNewsLetter(newsLetterMailData: NewsLetterMailData) {
        val newsLetterInfo = getNewsLetterInfoByLabelId(newsLetterMailData.newsLetterType.labelId)
        with(newsLetterMailData) {
            newsLetterRepository.save(
                NewsLetter(
                    newsLetterInfoExternalId = newsLetterInfo.externalId,
                    mailId = mailId,
                    content = content ?: String(),
                    mailTitle = title ?: String(),
                    mailSnippet = snippet ?: String(),
                    receivedAt = receivedAt ?: LocalDateTime.now()
                )
            )
        }
    }

    fun getNewsLetterInfoByLabelId(labelId: String) =
        newsLetterInfoRepository.findByLabelId(labelId) ?: error("Invalid label id : $labelId")
}


