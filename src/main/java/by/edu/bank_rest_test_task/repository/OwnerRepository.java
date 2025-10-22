package by.edu.bank_rest_test_task.repository;

import by.edu.bank_rest_test_task.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, UUID> {
    Optional<OwnerEntity> findByUsername(String username);
}
