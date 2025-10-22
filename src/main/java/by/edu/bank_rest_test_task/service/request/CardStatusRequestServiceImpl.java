package by.edu.bank_rest_test_task.service.request;

import by.edu.bank_rest_test_task.dto.request.CardStatusRequestCreateDTO;
import by.edu.bank_rest_test_task.entity.CardEntity;
import by.edu.bank_rest_test_task.entity.CardStatusRequestEntity;
import by.edu.bank_rest_test_task.entity.OwnerEntity;
import by.edu.bank_rest_test_task.entity.StatusEntity;
import by.edu.bank_rest_test_task.exception.RequestIOException;
import by.edu.bank_rest_test_task.repository.CardRepository;
import by.edu.bank_rest_test_task.repository.CardStatusRequestRepository;
import by.edu.bank_rest_test_task.repository.OwnerRepository;
import by.edu.bank_rest_test_task.repository.StatusRepository;
import by.edu.bank_rest_test_task.util.RequestState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardStatusRequestServiceImpl implements CardStatusRequestService {
    private final CardRepository cardRepository;
    private final StatusRepository statusRepository;
    private final OwnerRepository ownerRepository;
    private final CardStatusRequestRepository requestRepository;

    @Override
    public CardStatusRequestEntity createRequest(UUID ownerId,
                                                 CardStatusRequestCreateDTO dto) throws RequestIOException {
        Optional<CardEntity> card = Optional.ofNullable(cardRepository.findById(dto.cardId()).orElseThrow(() ->
                new RequestIOException("Card not found")));
        Optional<StatusEntity> status = Optional.ofNullable(statusRepository.findById(dto.requestedStatusId()).orElseThrow(() ->
                new RequestIOException("Status not found")));
        Optional<OwnerEntity> owner = Optional.ofNullable(ownerRepository.findById(ownerId).orElseThrow(() ->
                new RequestIOException("Owner not found")));
        if (card.isPresent() && status.isPresent() && owner.isPresent()) {
            CardStatusRequestEntity request = new CardStatusRequestEntity();
            request.setCard(card.get());
            request.setRequester(owner.get());
            request.setRequestedStatus(status.get());
            request.setState(RequestState.NEW);

            return requestRepository.save(request);
        } else {
            throw new RequestIOException("Status/Owner/Card not found");
        }
    }

    @Override
    public List<CardStatusRequestEntity> getAllRequests(RequestState state) {
        return requestRepository.findAllByState(RequestState.valueOf(state.name()));
    }

    @Override
    public void approveRequest(UUID requestId) {
        Optional<CardStatusRequestEntity> request = Optional.ofNullable(
                requestRepository.findById(requestId)
                        .orElseThrow(() -> new RuntimeException("Request not found")));
        if (request.isPresent()) {
            request.get().getCard().setStatusEntity(request.get().getRequestedStatus());
            request.get().setState(RequestState.APPROVED);
            requestRepository.save(request.get());
        }
    }

    @Override
    public void rejectRequest(UUID requestId) {
        Optional<CardStatusRequestEntity> request = Optional.ofNullable(
                requestRepository.findById(requestId)
                        .orElseThrow(() -> new RuntimeException("Request not found")));
        if (request.isPresent()) {
            request.get().setState(RequestState.REJECTED);
            requestRepository.save(request.get());
        }
    }
}
