package by.edu.bank_rest_test_task.service.request;

import by.edu.bank_rest_test_task.dto.request.CardStatusRequestCreateDTO;
import by.edu.bank_rest_test_task.entity.CardStatusRequestEntity;
import by.edu.bank_rest_test_task.exception.RequestIOException;
import by.edu.bank_rest_test_task.util.RequestState;

import java.util.List;
import java.util.UUID;

public interface CardStatusRequestService {
    CardStatusRequestEntity createRequest(UUID ownerId, CardStatusRequestCreateDTO dto) throws RequestIOException;

    List<CardStatusRequestEntity> getAllRequests(RequestState state);

    void approveRequest(UUID requestId);

    void rejectRequest(UUID requestId);
}
