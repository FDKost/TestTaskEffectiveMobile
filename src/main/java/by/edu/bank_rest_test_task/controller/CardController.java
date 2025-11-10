package by.edu.bank_rest_test_task.controller;

import by.edu.bank_rest_test_task.dto.card.CardCreateEditDTO;
import by.edu.bank_rest_test_task.dto.card.CardReadDTO;
import by.edu.bank_rest_test_task.service.userdetails.CustomUserDetails;
import by.edu.bank_rest_test_task.exception.CardIOException;
import by.edu.bank_rest_test_task.service.card.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
@Tag(name = "card", description = "Операции производимые над картами")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class CardController {
    private final CardService cardService;

    @Operation(summary = "Создать карту пользователю",
            description = "Возвращает созданную карту")
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public CardReadDTO createCard(@RequestBody CardCreateEditDTO cardCreateEditDTO) {
        return cardService.create(cardCreateEditDTO);
    }

    @Operation(summary = "Получить карту пользователя по uuid",
            description = "Возвращает карту пользователя")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<CardReadDTO> getCard(@PathVariable UUID id) {
        return cardService.findById(id);
    }

    @Operation(summary = "Получить весь список карт",
            description = "Возвращает все карты в системе")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CardReadDTO> getAllCards() {
        return cardService.findAll();
    }

    @Operation(summary = "Получить весь список карт по фильтру",
            description = "Возвращает все карты в системе найденные по фильтру")
    @GetMapping("/{variable}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CardReadDTO> getAllCardsBy(@PathVariable String variable) {
        return cardService.findAllBy(variable);
    }

    @Operation(summary = "Получить весь список карт пользователя",
            description = "Возвращает все карты в системе принадлежащие пользователю")
    @GetMapping("/by/{ownerId}")
    @PreAuthorize("hasRole('USER')")
    public List<CardReadDTO> getCardsByOwner(@PathVariable UUID ownerId) {
        return cardService.findAllByOwner(ownerId);
    }

    @Operation(summary = "Получить весь список карт",
            description = "Возвращает все карты в системе по пользователю с пагинацией и фильтром")
    @GetMapping("/by/pagination")
    @PreAuthorize("hasRole('USER')")
    public Page<CardReadDTO> getAllCardsByOwnerWithPagination(@RequestParam UUID ownerId,
                                                              @RequestParam int offset,
                                                              @RequestParam int limit,
                                                              @RequestParam String variable) {
        return cardService.findAllWithPaginationAndSorting(ownerId, offset, limit, variable);
    }

    @Operation(summary = "Обновить карту пользователя",
            description = "Возвращает изменённую карту в системе")
    @PutMapping("/update/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<CardReadDTO> update(@PathVariable UUID cardId,
                                        CardCreateEditDTO cardCreateEditDTO) {
        return cardService.update(cardId, cardCreateEditDTO);
    }

    @Operation(summary = "Обновляет статус карты пользователя",
            description = "Возвращает изменённую карту в системе")
    @PatchMapping("/update/status/{cardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<CardReadDTO> updateStatus(@PathVariable UUID cardId,
                                              @RequestBody CardCreateEditDTO cardCreateEditDTO) {
        return cardService.updateStatus(cardId, cardCreateEditDTO);
    }

    @Operation(summary = "Получить баланс карты",
            description = "Возвращает баланс карты по uuid самой карты")
    @GetMapping("/get/card/balance/{cardId}")
    @PreAuthorize("hasRole('USER')")
    public String getCardBalance(@PathVariable UUID cardId,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) throws CardIOException {
        Optional<CardReadDTO> cardDto = cardService.findById(cardId);
        List<CardReadDTO> possibleCards = cardService.findAllByOwner(userDetails.getId());
        if (cardDto.isPresent() && possibleCards.contains(cardDto.get())) {
            return cardDto.get().amount().toString();
        } else if (cardDto.isPresent() && !possibleCards.contains(cardDto.get())) {
            log.warn("There is no such card for current user");
            return "There is no such card for current user";
        } else {
            log.warn("Card is not present");
            return "Card is not present";
        }
    }

    @Operation(summary = "Удалить карту",
            description = "Возвращает true/false в зависимости от результата")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteCard(@PathVariable UUID id) {
        return cardService.delete(id);
    }
}
