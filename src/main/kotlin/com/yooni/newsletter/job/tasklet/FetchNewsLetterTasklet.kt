package com.yooni.newsletter.job.tasklet

import com.yooni.newsletter.service.MailService
import com.yooni.newsletter.type.MailType
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class FetchNewsLetterTasklet(
    private val mailService: MailService,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val notProcessedMailIds = mailService.getMailList(
            labelIds = listOf(MailType.NOT_PROCESSED.labelId)
        ).messages.map { it.id }

        for (mailId in notProcessedMailIds) {
            val (mailType, mailContent) = mailService.getMailTypeAndContent(mailId)

            println(mailType)

            if (mailType.isNewsLetter()) {
                // TODO 데이터 처리
                println(mailContent)
            }

            mailService.completeMail(mailId)
        }

        return RepeatStatus.FINISHED
    }
}

