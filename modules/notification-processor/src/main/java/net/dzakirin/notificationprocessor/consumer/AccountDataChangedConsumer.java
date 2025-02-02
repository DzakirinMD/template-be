package net.dzakirin.notificationprocessor.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.notificationprocessor.dto.event.AccountDto;
import net.dzakirin.notificationprocessor.dto.event.EventWrapper;
import net.dzakirin.notificationprocessor.service.ProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountDataChangedConsumer {

    private final ObjectMapper objectMapper;
    private final ProcessingService processingService;


    public AccountDataChangedConsumer(
            ObjectMapper objectMapper,
            ProcessingService processingService
    ) {
        this.objectMapper = objectMapper;
        this.processingService = processingService;
    }

    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(@Payload EventWrapper<?> event) {
        try {
            AccountDto accountDto = objectMapper.convertValue(event.getPayload(), AccountDto.class);
            log.info("Received event: ID={}, Type={}, Source={}, Timestamp={}, Payload={}",
                    event.getEventId(),
                    event.getEventType(),
                    event.getEventSource(),
                    event.getTimestamp(),
                    accountDto);

            processingService.processAccountDataChanged(accountDto);
        } catch (Exception e) {
            log.error("Error processing event: {}", e.getMessage(), e);
        }
    }
}
