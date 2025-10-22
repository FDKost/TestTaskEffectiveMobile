package by.edu.bank_rest_test_task.service.card;

import by.edu.bank_rest_test_task.dto.card.CardCreateEditDTO;
import by.edu.bank_rest_test_task.dto.card.CardReadDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardService {
    Optional<CardReadDTO> findById(UUID id);

    List<CardReadDTO> findAllByOwner(UUID owner);

    List<CardReadDTO> findAll();

    CardReadDTO create(CardCreateEditDTO dto);

    List<CardReadDTO> findAllBy(String variable);

    Page<CardReadDTO> findAllWithPaginationAndSorting(UUID ownerId, int offset, int limit, String variable);

    Optional<CardReadDTO> updateStatus(UUID id, CardCreateEditDTO dto);

    Optional<CardReadDTO> update(UUID id, CardCreateEditDTO dto);

    boolean delete(UUID id);

}
