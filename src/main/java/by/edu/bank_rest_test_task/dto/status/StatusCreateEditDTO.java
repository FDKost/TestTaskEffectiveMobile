package by.edu.bank_rest_test_task.dto.status;

import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@FieldNameConstants
public record StatusCreateEditDTO(UUID statusId, String statusName) {
}
