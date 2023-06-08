package com.yooni.newsletter

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class NewsletterApplication

fun main(args: Array<String>) {
	runApplication<NewsletterApplication>(*args)
}
