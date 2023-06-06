package com.yooni.newsletter.service

import com.yooni.newsletter.adaptor.GmailAdaptor
import com.yooni.newsletter.adaptor.ModifyMailRequestDto
import com.yooni.newsletter.helper.Base64Helper
import org.springframework.stereotype.Service

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
}

