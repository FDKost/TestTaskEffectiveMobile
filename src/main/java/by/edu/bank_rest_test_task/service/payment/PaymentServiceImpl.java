package by.edu.bank_rest_test_task.service.payment;

import by.edu.bank_rest_test_task.dto.card.CardCreateEditDTO;
import by.edu.bank_rest_test_task.dto.card.CardReadDTO;
import by.edu.bank_rest_test_task.service.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final CardService cardService;

    @Transactional
    @Override
    public Boolean performPayment(UUID sender, UUID recipient, BigDecimal amount) {
        Optional<CardReadDTO> senderCard = cardService.findById(sender);
        Optional<CardReadDTO> recipientCard = cardService.findById(recipient);

        if (senderCard.isPresent() && recipientCard.isPresent()
                && senderCard.get().ownerId().equals(recipientCard.get().ownerId())
                && senderCard.get().amount().subtract(amount).compareTo(BigDecimal.ZERO) >= 0) {
            CardCreateEditDTO senderCreateEditDto = convertReadDtoToCreateEditDto(senderCard.get());
            senderCreateEditDto.setAmount(senderCard.get().amount().subtract(amount));

            CardCreateEditDTO recipientCreateEditDto = convertReadDtoToCreateEditDto(recipientCard.get());
            recipientCreateEditDto.setAmount(recipientCard.get().amount().add(amount));

            cardService.update(senderCard.get().id(), senderCreateEditDto);
            cardService.update(recipientCard.get().id(), recipientCreateEditDto);
            return true;

        } else {
            throw new IllegalArgumentException("Sender or recipient is not found/Insufficient funds in the card/" +
                    "Maybe you sending sum not to yourself?");
        }
    }

    private CardCreateEditDTO convertReadDtoToCreateEditDto(CardReadDTO readDto) {
        CardCreateEditDTO createEditDto = new CardCreateEditDTO();
        createEditDto.setCardNumber(readDto.cardNumber());
        createEditDto.setOwnerId(readDto.ownerId());
        createEditDto.setAmount(readDto.amount());
        createEditDto.setStatusId(readDto.statusId());
        createEditDto.setExpiryDate(readDto.expiryDate());
        return createEditDto;
    }
}
