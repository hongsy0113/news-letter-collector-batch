package com.yooni.newsletter.job

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class FetchAndSaveNewsLetterJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory
) {
    @Bean(name = [JOB_NAME])
    fun fetchNewsLetterJob(@Qualifier(FETCH_NEWS_LETTER_STEP) step: Step): Job =
        jobBuilderFactory.get(JOB_NAME).start(step).listener(this).build()
}

@Configuration
class FetchAndSaveNewsLetterStepConfiguration(
    private val stepBuilderFactory: StepBuilderFactory
) {
    @Bean
    fun fetchNewsLetterStep(
        fetchNewsLetterTasklet: Tasklet,
        transactionManager: PlatformTransactionManager
    ): Step =
        stepBuilderFactory
            .get(FETCH_NEWS_LETTER_STEP)
            .transactionManager(transactionManager)
            .tasklet(fetchNewsLetterTasklet)
            .build()

}


private const val JOB_NAME = "fetchAndSaveNewsLetterJob"
private const val FETCH_NEWS_LETTER_STEP = "fetchNewsLetterStep"
