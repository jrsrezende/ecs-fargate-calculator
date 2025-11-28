package br.com.jrsr.backend.model;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Getter
@Setter
public class Expense {

    private String expenseId;
    private String creditCardId;
    private String description;
    private Double amount;
    private String date;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPk() {
        return "CARD#" + this.creditCardId;
    }

    public void setPk(String pk) {
        if (pk != null && pk.startsWith("CARD#")) {
            this.creditCardId = pk.substring(5);
        }
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    public String getSk() {
        return "EXPENSE#" + this.date + "#" + this.expenseId;
    }

    public void setSk(String sk) {
        if (sk != null && sk.startsWith("EXPENSE#")) {
            String[] parts = sk.split("#");
            if (parts.length >= 3) {
                this.date = parts[1];
                this.expenseId = parts[2];
            }
        }
    }
}
