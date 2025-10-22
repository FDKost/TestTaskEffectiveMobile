package by.edu.bank_rest_test_task.dto.owner;

import by.edu.bank_rest_test_task.entity.OwnerEntity;

import java.util.UUID;

public record OwnerReadDTO(UUID id, OwnerEntity owner, String username) {
}
