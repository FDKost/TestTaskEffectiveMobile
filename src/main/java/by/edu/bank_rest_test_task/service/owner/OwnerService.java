package by.edu.bank_rest_test_task.service.owner;

import by.edu.bank_rest_test_task.dto.owner.OwnerCreateEditDTO;
import by.edu.bank_rest_test_task.dto.owner.OwnerReadDTO;

import java.util.Optional;
import java.util.UUID;

public interface OwnerService {
    Optional<OwnerReadDTO> findById(UUID id);

    Optional<OwnerReadDTO> findByUsername(String username);

    OwnerReadDTO create(OwnerCreateEditDTO dto);

    Optional<OwnerReadDTO> update(UUID id, OwnerCreateEditDTO dto);

    boolean delete(UUID id);
}
