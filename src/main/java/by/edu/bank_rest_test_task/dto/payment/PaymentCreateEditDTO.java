package by.edu.bank_rest_test_task.dto.payment;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@FieldNameConstants
public class PaymentCreateEditDTO {
    UUID senderId;
    UUID receiverId;
    BigDecimal amount;
}
