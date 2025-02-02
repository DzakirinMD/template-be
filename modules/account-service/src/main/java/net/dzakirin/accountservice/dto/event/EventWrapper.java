package net.dzakirin.accountservice.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * A generic event envelope that wraps any payload.
 *
 * @param <T> the type of the payload
 */
@Data
@Builder
public class EventWrapper<T> {
    /**
     * Unique identifier for the event.
     */
    private String eventId;

    /**
     * The type of the event (e.g., ACCOUNT_CREATED, ACCOUNT_WITHDRAWAL).
     */
    private String eventType;

    /**
     * The source of the event (e.g., account-service).
     */
    private String eventSource;

    /**
     * Timestamp when the event was created.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant timestamp;

    /**
     * The payload containing the event data.
     */
    private T payload;
}
