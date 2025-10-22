package by.edu.bank_rest_test_task.dto.request;

import java.util.UUID;

public record CardStatusRequestCreateDTO(UUID cardId, UUID requestedStatusId) {
}
