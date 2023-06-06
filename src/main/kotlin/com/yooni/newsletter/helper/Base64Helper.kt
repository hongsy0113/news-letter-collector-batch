package com.yooni.newsletter.helper

import org.springframework.stereotype.Component
import java.util.*

@Component
class Base64Helper {

    fun decode(encodedString: String) =
        Base64.getDecoder().decode(
            encodedString.replace('-', '+').replace('_', '/')
        ).let { String(it) }
}