package net.dzakirin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.common.dto.event.OrderEvent;
import net.dzakirin.common.dto.request.EmailRequest;
import net.dzakirin.entity.EmailDetails;
import net.dzakirin.template.EmailTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProcessingService {

    private final EmailService emailService;

    public void sendOrderConfirmationEmail(OrderEvent orderEvent) {
        log.info("Processing order data for emailing: CustomerId={}, OrderId={}",
                orderEvent.getCustomerId(),
                orderEvent.getId()
        );

        double totalAmount = orderEvent.getOrderItems().stream()
                .mapToDouble(product -> product.getPrice().doubleValue() * product.getQuantity())
                .sum();

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(orderEvent.getCustomerEmail())
                .subject("🛒 Order Confirmation - Order ID: " + orderEvent.getId())
                .msgBody(EmailTemplate.orderConfirmationTemplate(orderEvent, totalAmount))
                .build();

        try {
            emailService.sendEmail(emailDetails);
            log.info("Order confirmation email has been sent to {}", emailDetails.getRecipient());
        } catch (Exception e) {
            log.error("Failed to send order confirmation email for Order ID: {}", orderEvent.getId());
        }
    }

    public void sendLoyaltyPointsEmail(EmailRequest emailRequest) {
        log.info("Processing loyalty points email for CustomerID={}, Email={}, Points={}",
                emailRequest.customerId(), emailRequest.customerEmail(), emailRequest.points());

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(emailRequest.customerEmail())
                .subject("🎉 Loyalty Points Earned!")
                .msgBody(EmailTemplate.loyaltyPointsTemplate(emailRequest))
                .build();

        try {
            emailService.sendEmail(emailDetails);
            log.info("Loyalty points email has been sent to {}", emailDetails.getRecipient());
        } catch (Exception e) {
            log.error("Failed to send loyalty points email for {}", emailDetails.getRecipient());
        }
    }
}
