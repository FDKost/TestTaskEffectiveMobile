package by.edu.bank_rest_test_task.util.mapper.card;

import by.edu.bank_rest_test_task.dto.card.CardCreateEditDTO;
import by.edu.bank_rest_test_task.entity.CardEntity;
import by.edu.bank_rest_test_task.repository.OwnerRepository;
import by.edu.bank_rest_test_task.repository.StatusRepository;
import by.edu.bank_rest_test_task.util.SimpleCardGenerator;
import by.edu.bank_rest_test_task.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardCreateEditMapper implements Mapper<CardCreateEditDTO, CardEntity> {
    private final OwnerRepository ownerRepository;
    private final StatusRepository statusRepository;

    @Override
    public CardEntity map(CardCreateEditDTO object) {
        CardEntity cardEntity = new CardEntity();
        fillCardCreateEditDTO(object, cardEntity);
        return cardEntity;
    }

    @Override
    public CardEntity map(CardCreateEditDTO fromObject, CardEntity toObject) {
        fillCardCreateEditDTO(fromObject, toObject);
        return toObject;
    }

    private void fillCardCreateEditDTO(CardCreateEditDTO object, CardEntity cardEntity) {
        if (ownerRepository.findById(object.getOwnerId()).isPresent()
                && statusRepository.findById(object.getStatusId()).isPresent()) {
            cardEntity.setOwner(ownerRepository.findById(object.getOwnerId()).get());
            cardEntity.setStatusEntity(statusRepository.findById(object.getStatusId()).get());
            cardEntity.setExpiryDate(object.getExpiryDate());
            cardEntity.setCardNumber(SimpleCardGenerator.generateCardNumber());
            cardEntity.setAmount(object.getAmount());

        }
    }

}
