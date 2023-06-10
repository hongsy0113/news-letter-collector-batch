package com.yooni.newsletter.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.convertToLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.RFC_1123_DATE_TIME)