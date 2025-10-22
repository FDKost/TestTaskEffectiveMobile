package by.edu.bank_rest_test_task.service.payment;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentService {
    Boolean performPayment(UUID sender, UUID recipient, BigDecimal amount);
}
