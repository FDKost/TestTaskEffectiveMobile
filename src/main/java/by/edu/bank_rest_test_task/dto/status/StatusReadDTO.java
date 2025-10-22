package by.edu.bank_rest_test_task.dto.status;

import lombok.Value;

import java.util.UUID;

@Value
public class StatusReadDTO {
    UUID id;
    String statusName;
}
