package by.edu.bank_rest_test_task.service.status;

import by.edu.bank_rest_test_task.dto.status.StatusCreateEditDTO;
import by.edu.bank_rest_test_task.dto.status.StatusReadDTO;

import java.util.Optional;
import java.util.UUID;

public interface StatusService {
    Optional<StatusReadDTO> findById(UUID id);

    StatusReadDTO create(StatusCreateEditDTO dto);

    Optional<StatusReadDTO> update(UUID id, StatusCreateEditDTO dto);

}
