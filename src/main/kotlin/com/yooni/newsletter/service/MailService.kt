package com.yooni.newsletter.service

import com.yooni.newsletter.adaptor.GmailAdaptor
import com.yooni.newsletter.adaptor.ModifyMailRequestDto
import com.yooni.newsletter.helper.Base64Helper
import com.yooni.newsletter.helper.convertToLocalDateTime
import com.yooni.newsletter.type.MailType
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MailService(
    private val mailAdaptor: GmailAdaptor,
    private val base64Helper: Base64Helper,
) {
    fun getMailList(labelIds: List<String> = emptyList()) =
        mailAdaptor.callGetMailListAPI(labelIds = labelIds)

    fun getMailContent(mailId: String) =
        mailAdaptor.callGetMailAPI(mailId).payload?.body?.data?.let {
            base64Helper.decode(it)
        }

    fun getMailTypeAndContent(mailId: String): Pair<MailType, String?> {
        val getMailResponseDto = mailAdaptor.callGetMailAPI(mailId)
        val mailType = getMailTypeByLabelIds(getMailResponseDto.labelIds)

        return when (mailType) {
            MailType.NOT_NEWS_LETTER -> mailType to null
            else -> mailType to getMailResponseDto.payload?.body?.data?.let {
                base64Helper.decode(it)
            }
        }
    }

    fun getNewsLetterMailData(mailId: String): NewsLetterMailData =
        mailAdaptor.callGetMailAPI(mailId).let { responseDto ->
            NewsLetterMailData(
                newsLetterType = getMailTypeByLabelIds(responseDto.labelIds),
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

    private fun getMailTypeByLabelIds(labelIds: List<String>): MailType {
        MailType.NEWS_LETTER_LABEL.forEach {
            if (it.labelId in labelIds) {
                return it
            }
        }
        return MailType.NOT_NEWS_LETTER
    }
}

data class NewsLetterMailData(
    val newsLetterType : MailType,
    val title: String?,
    val snippet: String?,
    val content: String?,
    val receivedAt: LocalDateTime?
)


