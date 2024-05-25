package com.example.batch.demo_data_migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import com.example.batch.demo_data_migration.entity.Person;

@Configuration
public class TempProcessor implements ItemProcessor<Person, Person> {
    private static final Logger log = LoggerFactory.getLogger(TempProcessor.class);

    @Override
    public Person process(Person item) throws Exception {
        log.debug("Processing: {}", item.toString());
        return item;
    }

}
