package by.edu.bank_rest_test_task.dto.owner;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record OwnerCreateEditDTO(String username, String password, String role) {
}
