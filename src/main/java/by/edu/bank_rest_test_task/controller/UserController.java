package by.edu.bank_rest_test_task.controller;

import by.edu.bank_rest_test_task.dto.owner.OwnerReadDTO;
import by.edu.bank_rest_test_task.dto.request.CardStatusRequestCreateDTO;
import by.edu.bank_rest_test_task.entity.CardStatusRequestEntity;
import by.edu.bank_rest_test_task.exception.RequestIOException;
import by.edu.bank_rest_test_task.service.owner.OwnerService;
import by.edu.bank_rest_test_task.service.request.CardStatusRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user", description = "Операции выполняемые пользователем")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final CardStatusRequestService requestService;
    private final OwnerService ownerService;

    @Operation(summary = "Отправить запрос на изменение статуса",
            description = "Отправляет запрос администратору на изменение статуса карты")
    @Transactional
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public CardStatusRequestEntity createRequest(@AuthenticationPrincipal UserDetails user,
                                                 @RequestBody CardStatusRequestCreateDTO dto)
            throws RequestIOException {
        Optional<OwnerReadDTO> ownerDto = ownerService.findByUsername(user.getUsername());
        if (ownerDto.isPresent()) {
            return requestService.createRequest(ownerService.findByUsername(
                    user.getUsername()).get().id(), dto);
        } else {
            throw new RequestIOException("Can't create request because the user doesn't exist");
        }
    }
}
