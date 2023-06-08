package com.yooni.newsletter.type

enum class MailType(
    val labelId: String
) {
    BOODING("Label_4270947439533042062"),
    NEWNEEK("Label_935356157207187575"),
    FUN_JOOMAL("Label_8364301457868954205"),
    UPPITY("Label_2611734388845633742"),
    COMPLETED("Label_5055091156381294150"),
    NOT_PROCESSED("Label_6087905838935334018"),
    NOT_NEWS_LETTER("Label_7927830800911859779");

    fun isNewsLetter() = this in NEWS_LETTER_LABEL

    companion object {
        val NEWS_LETTER_LABEL = listOf(BOODING, NEWNEEK, FUN_JOOMAL, UPPITY)
    }
}