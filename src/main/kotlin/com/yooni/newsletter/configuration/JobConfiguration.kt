package com.yooni.newsletter.configuration

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class JobConfiguration : DefaultBatchConfigurer() {

    override fun setDataSource(dataSource: DataSource) {
        // override to do not set datasource even if a datasource exist.
        // initialize will use a Map base JobRepository (instead of Database)
    }
}