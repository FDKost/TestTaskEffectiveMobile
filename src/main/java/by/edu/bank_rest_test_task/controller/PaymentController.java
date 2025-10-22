package by.edu.bank_rest_test_task.controller;

import by.edu.bank_rest_test_task.dto.payment.PaymentCreateEditDTO;
import by.edu.bank_rest_test_task.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@Tag(name = "payment", description = "Операции связанные с переводами")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "Отправить перевод на свою карту",
            description = "Перевести деньги с одной своей карты на другую")
    @PostMapping("/perform")
    public Boolean performPayment(@RequestBody PaymentCreateEditDTO dto) {
        return paymentService.performPayment(dto.getSenderId(),
                dto.getReceiverId(), dto.getAmount());
    }
}
