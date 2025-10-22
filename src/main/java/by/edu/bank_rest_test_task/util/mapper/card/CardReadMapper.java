package by.edu.bank_rest_test_task.util.mapper.card;

import by.edu.bank_rest_test_task.dto.card.CardReadDTO;
import by.edu.bank_rest_test_task.entity.CardEntity;
import by.edu.bank_rest_test_task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CardReadMapper implements Mapper<CardEntity, CardReadDTO> {
    @Override
    public CardReadDTO map(CardEntity object) {
        return new CardReadDTO(
                object.getId(),
                object.getCardNumber(),
                object.getAmount(),
                object.getExpiryDate(),
                object.getOwner().getOwnerId(),
                object.getStatusEntity().getId()
        );
    }
}
