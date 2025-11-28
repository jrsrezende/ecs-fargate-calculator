package br.com.jrsr.backend.repository;

import br.com.jrsr.backend.model.CreditCard;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

@Repository
public class CreditCardRepository {

    private final DynamoDbTable<CreditCard> table;

    public CreditCardRepository(DynamoDbEnhancedClient enhancedClient) {
        String tableName = System.getenv("DYNAMODB_TABLE_NAME");
        if (tableName == null) {
            tableName = "FinanceTable";
        }
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(CreditCard.class));
    }

    public void save(CreditCard creditCard) {
        table.putItem(creditCard);
    }

    public CreditCard findById(String cardId) {
        Key key = Key.builder().partitionValue("CARD#" + cardId).sortValue("METADATA").build();

        return table.getItem(key);
    }

    public List<CreditCard> findAll() {
        Expression filter = Expression.builder().expression("SK = :skVal")
                .expressionValues(Map.of(":skVal", AttributeValue.builder().s("METADATA").build()))
                .build();

        return table.scan(r -> r.filterExpression(filter)).items().stream().toList();
    }
}
