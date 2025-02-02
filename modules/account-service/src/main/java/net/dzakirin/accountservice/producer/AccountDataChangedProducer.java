package net.dzakirin.accountservice.producer;

import net.dzakirin.accountservice.dto.response.AccountDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AccountDataChangedProducer extends BaseProducer<AccountDto> {

    public AccountDataChangedProducer(
            @Value("${kafka.producer.topic.account-data-changed}") String topic,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        super(topic, kafkaTemplate);
    }
}
