package com.yooni.newsletter.job.tasklet

import com.yooni.newsletter.service.MailService
import com.yooni.newsletter.service.NewsLetterDataService
import com.yooni.newsletter.type.MailType
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class FetchNewsLetterTasklet(
    private val mailService: MailService,
    private val newsLetterDataService: NewsLetterDataService,
) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val notProcessedMailIds = mailService.getNotProcessedMailIds()

        for (mailId in notProcessedMailIds) {
            val mailType = mailService.getMailType(mailId)

            if (mailType.isNewsLetter()) {
                mailService.getNewsLetterMailData(mailId).let {
                    newsLetterDataService.saveNewsLetter(it)
                }
                mailService.completeMail(mailId)

            }
        }

        return RepeatStatus.FINISHED
    }
}

