package com.yooni.newsletter.domain.entity

import com.yooni.newsletter.domain.AuditEntity
import com.yooni.newsletter.helper.generateExternalId
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class NewsLetter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_letter_id")
    val id: Long? = null,
    val externalId: String,
    val newsLetterInfoExternalId: String,
    val mailId: String,
    val content: String,
    val mailTitle: String,
    val mailSnippet: String,
    val uploadedDate: LocalDate,
    val receivedAt: LocalDateTime
) : AuditEntity() {

    constructor(
        newsLetterInfoExternalId: String,
        mailId: String,
        content: String,
        mailTitle: String,
        mailSnippet: String,
        receivedAt: LocalDateTime
    ) : this(
        externalId = generateExternalId(ABBREVIATED_TABLE_NAME),
        newsLetterInfoExternalId = newsLetterInfoExternalId,
        mailId = mailId,
        content = content,
        mailTitle = mailTitle,
        mailSnippet = mailSnippet,
        uploadedDate = receivedAt.toLocalDate(),
        receivedAt = receivedAt
    )
    companion object {
        private const val ABBREVIATED_TABLE_NAME = "nla"
    }
}