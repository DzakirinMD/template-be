package net.dzakirin.accountservice.producer;

import net.dzakirin.accountservice.dto.response.TransactionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionDataChangedProducer extends BaseProducer<TransactionDto> {

    public TransactionDataChangedProducer(
            @Value("${kafka.producer.topic.transaction-data-changed}") String topic,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        super(topic, kafkaTemplate);
    }
}
