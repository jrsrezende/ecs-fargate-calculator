package br.com.jrsr.backend.repository;

import br.com.jrsr.backend.model.Expense;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;

@Repository
public class ExpenseRepository {

    private final DynamoDbTable<Expense> table;

    public ExpenseRepository(DynamoDbEnhancedClient enhancedClient) {
        String tableName = System.getenv("DYNAMODB_TABLE_NAME");
        if (tableName == null) {
            tableName = "FinanceTable";
        }
        this.table = enhancedClient.table(tableName, TableSchema.fromBean(Expense.class));
    }

    public void save (Expense expense) {
        table.putItem(expense);
    }

    public List<Expense> findByCardId(String cardId) {
        QueryConditional query = QueryConditional.sortBeginsWith(k -> k.partitionValue("CARD#" + cardId).sortValue("EXPENSE#"));

        return table.query(query).items().stream().toList();
    }
}
