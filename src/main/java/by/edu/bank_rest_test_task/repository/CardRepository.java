package by.edu.bank_rest_test_task.repository;

import by.edu.bank_rest_test_task.entity.CardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    Optional<CardEntity> findAllByOwnerOwnerId(UUID id);

    Page<CardEntity> findAllByOwnerOwnerId(UUID id, Pageable pageable);
}
