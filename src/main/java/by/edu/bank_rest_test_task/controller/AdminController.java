package by.edu.bank_rest_test_task.controller;


import by.edu.bank_rest_test_task.dto.owner.OwnerCreateEditDTO;
import by.edu.bank_rest_test_task.dto.owner.OwnerReadDTO;
import by.edu.bank_rest_test_task.entity.CardStatusRequestEntity;
import by.edu.bank_rest_test_task.service.owner.OwnerService;
import by.edu.bank_rest_test_task.service.request.CardStatusRequestService;
import by.edu.bank_rest_test_task.util.RequestState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "admin", description = "Операции производимые администратором")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    private final OwnerService ownerService;
    private final CardStatusRequestService statusRequestService;

    @Operation(summary = "Получить пользователя по UUID",
            description = "Возвращает пользователя из бд")
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<OwnerReadDTO> getOwnerById(@PathVariable @NotNull UUID id) {
        return ownerService.findById(id);
    }

    @Operation(summary = "Получить пользователя по имени",
            description = "Возвращает пользователя из бд")
    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<OwnerReadDTO> getOwnerByUsername(@PathVariable @NotNull String username) {
        return ownerService.findByUsername(username);
    }

    @Operation(summary = "Удалить пользователя по UUID",
            description = "Удаляет пользователя из бд, возвращает true/false")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteOwner(@PathVariable @NotNull UUID id) {
        return ownerService.delete(id);
    }

    @Operation(summary = "Обновить запись пользователя по UUID",
            description = "Возвращает изменённого пользователя из бд")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<OwnerReadDTO> updateOwner(@PathVariable @NotNull UUID id,
                                              @RequestBody OwnerCreateEditDTO ownerCreateEditDTO) {
        return ownerService.update(id, ownerCreateEditDTO);
    }

    @Operation(summary = "Получить все реквесты созданные пользователями с указанным статусом",
            description = "Возвращает список реквестов от пользователей с указанным статусом")
    @GetMapping("/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CardStatusRequestEntity> getRequestsWithState(@RequestParam @NotNull RequestState state) {
        return statusRequestService.getAllRequests(state);
    }

    @Operation(summary = "Подтвердить реквест пользователя",
            description = "Возвращает true/false в зависимости от результата")
    @Transactional
    @PostMapping("/requests/approve/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean approve(@PathVariable @NotNull UUID requestId) {
        statusRequestService.approveRequest(requestId);
        return true;
    }

    @Operation(summary = "Отклонить реквест пользователя",
            description = "Возвращает true/false в зависимости от результата")
    @Transactional
    @PostMapping("/requests/reject/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean reject(@PathVariable @NotNull UUID requestId) {
        statusRequestService.rejectRequest(requestId);
        return true;
    }

}
