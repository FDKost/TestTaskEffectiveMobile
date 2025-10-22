package by.edu.bank_rest_test_task.controller;

import by.edu.bank_rest_test_task.dto.payment.PaymentCreateEditDTO;
import by.edu.bank_rest_test_task.service.payment.PaymentService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private PaymentCreateEditDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dto = new PaymentCreateEditDTO();
        dto.setSenderId(UUID.randomUUID());
        dto.setReceiverId(UUID.randomUUID());
        dto.setAmount(BigDecimal.valueOf(150.0));
    }

    @Test
    void performPaymentWhenPaymentSuccessful() {
        when(paymentService.performPayment(dto.getSenderId(), dto.getReceiverId(), dto.getAmount())).thenReturn(true);

        Boolean result = paymentController.performPayment(dto);

        Assertions.assertThat(result).isTrue();
        verify(paymentService, times(1))
                .performPayment(dto.getSenderId(), dto.getReceiverId(), dto.getAmount());
    }

    @Test
    void performPaymentWhenPaymentFails() {
        when(paymentService.performPayment(dto.getSenderId(), dto.getReceiverId(), dto.getAmount())).thenReturn(false);

        Boolean result = paymentController.performPayment(dto);

        Assertions.assertThat(result).isFalse();

        verify(paymentService, times(1))
                .performPayment(dto.getSenderId(), dto.getReceiverId(), dto.getAmount());
    }
}