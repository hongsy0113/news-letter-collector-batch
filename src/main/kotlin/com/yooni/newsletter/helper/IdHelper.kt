package com.yooni.newsletter.helper

import java.util.*

fun generateExternalId(prefix: String): String =
    "$prefix-${UUID.randomUUID().toString().replace(oldValue = "-", newValue = "")}"