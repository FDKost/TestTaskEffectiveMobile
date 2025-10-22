package by.edu.bank_rest_test_task.dto.card;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public record CardReadDTO(UUID id, String cardNumber, BigDecimal amount, LocalDate expiryDate, UUID ownerId,
                          UUID statusId) {
}
