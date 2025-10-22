package by.edu.bank_rest_test_task.dto.card;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@FieldNameConstants
public class CardCreateEditDTO {
    String cardNumber;
    UUID ownerId;
    UUID statusId;
    LocalDate expiryDate;
    BigDecimal amount;
}
