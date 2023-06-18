package com.ajith.spring.springint.config;

import com.ajith.spring.springint.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class SpringIntConfig {

    @ConfigurationProperties(prefix="spring.datasource")
    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }


    @Bean
    public MessageChannel newPersonFoundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel newPersonProcessChannel() {
        return new DirectChannel();
    }

    @Bean
    @Autowired
    @InboundChannelAdapter(value = "newPersonFoundChannel", poller = @Poller(fixedDelay="5000"))
    public MessageSource<Object> jdbcMessageSource(DataSource dataSource) {
        JdbcPollingChannelAdapter adapter = new JdbcPollingChannelAdapter(dataSource, "select * from person");
        adapter.setRowMapper((rs, index) -> new Person(rs.getLong("id"), rs.getString("name")));

        return adapter;
    }


    @Splitter(inputChannel = "newPersonFoundChannel", outputChannel = "newPersonPrinterChannel")
    public List<Person> invokeService(Message message) {
        List<Person> personList=  (List)message.getPayload();
        return personList;
    }

    @ServiceActivator(inputChannel = "newPersonPrinterChannel")
    public void handleReservation(Person person) {
        System.out.println("Received message: " + person);
    }

}
