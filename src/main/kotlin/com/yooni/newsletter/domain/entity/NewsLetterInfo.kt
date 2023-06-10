package com.yooni.newsletter.domain.entity

import com.yooni.newsletter.domain.AuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class NewsLetterInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_letter_info_id")
    val id: Long,
    val externalId: String,
    val labelId: String,
    val name: String,
) : AuditEntity()