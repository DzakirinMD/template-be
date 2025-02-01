package net.dzakirin.accountservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {
    DEPOSIT("Account deposit"),
    WITHDRAWAL("Account withdrawal"),
    TRANSFER("Account transfer");

    public final String eventName;
}