package com.yooni.newsletter.service

import com.yooni.newsletter.adaptor.GmailAdaptor
import com.yooni.newsletter.adaptor.ModifyMailRequestDto
import com.yooni.newsletter.helper.Base64Helper
import com.yooni.newsletter.helper.convertToLocalDateTime
import com.yooni.newsletter.type.MailType
import com.yooni.newsletter.type.NewsLetterType
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MailService(
    private val mailAdaptor: GmailAdaptor,
    private val base64Helper: Base64Helper,
) {
    fun getNotProcessedNewsLetterMailIds() =
        getMailList(labelIds = listOf(MailType.NEWS_LETTER.labelId, MailType.NOT_PROCESSED.labelId))
            .messages.map { it.id }

    fun getMailList(labelIds: List<String> = emptyList()) =
        mailAdaptor.callGetMailListAPI(labelIds = labelIds)

    fun getMailContent(mailId: String) =
        mailAdaptor.callGetMailAPI(mailId).payload?.body?.data?.let {
            base64Helper.decode(it)
        }

    fun getNewsLetterType(mailId: String) =
        mailAdaptor.callGetMailLabelIdsAPI(mailId).let {
            getNewsLetterTypeByLabelIds(it.labelIds)
        }


    // TODO : responseDto 에서 의미있는 값 추출하는 로직 리팩토링
    fun getNewsLetterMailData(mailId: String): NewsLetterMailData =
        mailAdaptor.callGetMailAPI(mailId).let { responseDto ->
            NewsLetterMailData(
                newsLetterType = getNewsLetterTypeByLabelIds(responseDto.labelIds),
                mailId = mailId,
                title = responseDto.payload?.headers?.find { it.name == "Subject" }?.value,
                snippet = responseDto.snippet,
                content = responseDto.payload?.body?.data?.let {
                    base64Helper.decode(it)
                },
                receivedAt = responseDto.payload?.headers?.find { it.name == "Date" }?.value?.convertToLocalDateTime()
            )
        }

    fun completeMail(mailId: String) {
        modifyMailLabel(
            mailId = mailId,
            addLabelIds = listOf(MailType.COMPLETED.labelId),
            removeLabelIds = listOf(MailType.NOT_PROCESSED.labelId)
        )
    }

    fun modifyMailLabel(
        mailId: String,
        addLabelIds: List<String> = emptyList(),
        removeLabelIds: List<String> = emptyList()
    ) = mailAdaptor.callModifyMailAPI(
        mailId = mailId,
        requestDto = ModifyMailRequestDto(
            addLabelIds = addLabelIds,
            removeLabelIds = removeLabelIds
        ))

    private fun getNewsLetterTypeByLabelIds(labelIds: List<String>): NewsLetterType {
        labelIds.forEach {
            if (NewsLetterType.labelIdToNewsLetterMap.containsKey(it)) {
                return NewsLetterType.labelIdToNewsLetterMap[it]!!
            }
        }
        error("Not Contains NewsLetter Label Id : $labelIds")
    }
}

data class NewsLetterMailData(
    val newsLetterType : NewsLetterType,
    val mailId: String,
    val title: String?,
    val snippet: String?,
    val content: String?,
    val receivedAt: LocalDateTime?
)


