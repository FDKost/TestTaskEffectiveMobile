package by.edu.bank_rest_test_task.service.payment;

import by.edu.bank_rest_test_task.dto.card.CardCreateEditDTO;
import by.edu.bank_rest_test_task.dto.card.CardReadDTO;
import by.edu.bank_rest_test_task.service.card.CardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    @Mock
    private CardService cardService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private UUID senderId;
    private UUID receiverId;
    private UUID ownerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        senderId = UUID.randomUUID();
        receiverId = UUID.randomUUID();
        ownerId = UUID.randomUUID();
    }

    @Test
    void performPaymentSuccessful() {
        UUID senderStatus = UUID.randomUUID();
        UUID recipientStatus = UUID.randomUUID();

        CardReadDTO senderCard = new CardReadDTO(
                senderId,
                "1234",
                BigDecimal.valueOf(500.0),
                LocalDate.now().plusYears(1),
                ownerId,
                senderStatus
        );

        CardReadDTO recipientCard = new CardReadDTO(
                receiverId,
                "5678",
                BigDecimal.valueOf(100.0),
                LocalDate.now().plusYears(1),
                ownerId,
                recipientStatus
        );

        Assertions.assertThat(senderCard.ownerId()).isEqualTo(ownerId);
        Assertions.assertThat(recipientCard.ownerId()).isEqualTo(ownerId);
        Assertions.assertThat(senderCard.amount()).isEqualTo(BigDecimal.valueOf(500.0));

        when(cardService.findById(senderId)).thenReturn(Optional.of(senderCard));
        when(cardService.findById(receiverId)).thenReturn(Optional.of(recipientCard));

        boolean result = paymentService.performPayment(senderId, receiverId, BigDecimal.valueOf(200.0));

        Assertions.assertThat(result).isTrue();
        verify(cardService, times(1)).update(eq(senderId), any(CardCreateEditDTO.class));
        verify(cardService, times(1)).update(eq(receiverId), any(CardCreateEditDTO.class));
    }


}