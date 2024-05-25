package com.example.batch.demo_data_migration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.batch.demo_data_migration.entity.Person;

@Configuration
public class JobConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JobConfiguration.class);

    @Bean
    Job migratePersonJob(JobRepository jobRepository, Step migrateData) throws Exception {
        log.info("starting job");
        return new JobBuilder("Person Migration Job", jobRepository)
                .start(migrateData)
                .build();
    }

    @Bean
    Step migrateData(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<Person> reader, JdbcBatchItemWriter<Person> writer,
            ItemProcessor<Person, Person> tempProcessor) {
        log.info("in migration step");
        return new StepBuilder("migration step", jobRepository)
                .<Person, Person>chunk(2, transactionManager)
                .reader(reader)
                .processor(tempProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    JdbcCursorItemReader<Person> reader(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        log.info("In reader");
        String sql = "select * from person";
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("db table reader")
                .dataSource(dataSource)
                .sql(sql)
                .rowMapper(new DataClassRowMapper<>(Person.class))
                .build();
    }

    @Bean
    JdbcBatchItemWriter<Person> writer(@Qualifier("dataTarget") DataSource dataTarget) throws Exception {
        log.info("In writer");
        String sql = "INSERT INTO person (id, name, age) VALUES (:id, :name, :age)";
        return new JdbcBatchItemWriterBuilder<Person>()
                .dataSource(dataTarget)
                .sql(sql)
                .beanMapped()
                .build();
    }
}
