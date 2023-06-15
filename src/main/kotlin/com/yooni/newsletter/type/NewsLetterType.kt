package com.yooni.newsletter.type

enum class NewsLetterType(
    val labelId: String
) {
    NEWNEEK("Label_935356157207187575"),
    UPPITY("Label_2611734388845633742"),
    FUN_JOOMAL("Label_8364301457868954205"),
    BOODING("Label_4270947439533042062"),
    BESPICK("Label_1134868995310523961"),
    ANTIEGG("Label_8998182209305125543"),
    BOOK_JOURNALISM("Label_4723062009939634519"),
    MIRACLE_LETTER("Label_6712361157726090574");

    // TODO 리팩토링 가능힐지..?
    companion object {
        val labelIdToNewsLetterMap = mapOf(
            NEWNEEK.labelId to NEWNEEK,
            UPPITY.labelId to UPPITY,
            FUN_JOOMAL.labelId to FUN_JOOMAL,
            BOODING.labelId to BOODING,
            BESPICK.labelId to BESPICK,
            ANTIEGG.labelId to ANTIEGG,
            BOOK_JOURNALISM.labelId to BOOK_JOURNALISM,
            MIRACLE_LETTER.labelId to MIRACLE_LETTER
        )
    }
}
