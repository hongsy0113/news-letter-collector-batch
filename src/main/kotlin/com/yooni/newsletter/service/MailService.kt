package com.yooni.newsletter.service

import com.yooni.newsletter.adaptor.GmailAdaptor
import com.yooni.newsletter.adaptor.ModifyMailRequestDto
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailAdaptor: GmailAdaptor,
) {
    fun getMailList(labelIds: List<String> = emptyList()) =
        mailAdaptor.callGetMailListAPI(labelIds = labelIds)

    // TODO: 실제로 관심있는 데이터만 선택해서 리턴하도록 구현
    // TODO: 디코딩
    fun getMailContent(mailId: String) =
        mailAdaptor.callGetMailAPI(mailId).payload?.body

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

