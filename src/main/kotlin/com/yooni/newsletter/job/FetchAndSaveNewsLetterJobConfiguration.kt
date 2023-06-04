package com.yooni.newsletter.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class FetchAndSaveNewsLetterJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory
) {
    @Bean(name = [JOB_NAME])
    fun fetchNewsLetterJob(@Qualifier(GET_ACCESS_TOKEN_STEP_NAME) step: Step): Job =
        jobBuilderFactory.get(JOB_NAME).start(step).listener(this).build()
}

@Configuration
class FetchAndSaveNewsLetterStepConfiguration(
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun getAccessTokenStep(getAccessTokenTasklet: Tasklet): Step =
        stepBuilderFactory
            .get(GET_ACCESS_TOKEN_STEP_NAME)
            .tasklet(getAccessTokenTasklet)
            .build()

}

@Component
class GetAccessTokenTasklet(

) : Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        TODO("Not yet implemented")
    }

}


private const val JOB_NAME = "fetchAndSaveNewsLetterJob"
private const val GET_ACCESS_TOKEN_STEP_NAME = "getAccessTokenStep"
