package by.edu.bank_rest_test_task.repository;

import by.edu.bank_rest_test_task.entity.CardStatusRequestEntity;
import by.edu.bank_rest_test_task.util.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardStatusRequestRepository extends JpaRepository<CardStatusRequestEntity, UUID> {
    List<CardStatusRequestEntity> findAllByState(RequestState state);
}
