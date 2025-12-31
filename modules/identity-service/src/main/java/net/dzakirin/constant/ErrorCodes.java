package net.dzakirin.constant;

public enum ErrorCodes {

    // User Credential error
    EMAIL_REGISTERED("Email is already registered!"),
    EMAIL_NOT_FOUND("User email not found with email: %s"),
    INVALID_EMAIL_OR_PASSWORD("Invalid email or password");

    private final String messageTemplate;

    ErrorCodes(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessage(String... args) {
        return String.format(messageTemplate, (Object[]) args);
    }
}
