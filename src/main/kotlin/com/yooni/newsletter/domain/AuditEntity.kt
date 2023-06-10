package com.yooni.newsletter.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class AuditEntity {
    @Column(nullable = false, updatable = false)
    @CreatedDate
    open var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    open var updatedAt: LocalDateTime = LocalDateTime.now()
}