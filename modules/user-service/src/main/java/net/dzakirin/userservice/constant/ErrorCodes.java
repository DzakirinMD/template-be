package net.dzakirin.userservice.constant;

public enum ErrorCodes {
    ACCOUNT_NOT_FOUND("Account id not found : %s"),
    USER_NOT_FOUND("User id not found : %s"),
    USERNAME_NOT_FOUND("Username not found : %s");

    private final String messageTemplate;

    ErrorCodes(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessage(String... args) {
        return String.format(messageTemplate, (Object[]) args);
    }
}
