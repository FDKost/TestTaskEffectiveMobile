package by.edu.bank_rest_test_task.repository;

import by.edu.bank_rest_test_task.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, UUID> {
    Optional<StatusEntity> findByStatusName(String statusName);
}
