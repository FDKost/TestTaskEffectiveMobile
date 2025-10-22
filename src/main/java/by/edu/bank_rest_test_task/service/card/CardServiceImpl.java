package by.edu.bank_rest_test_task.service.card;

import by.edu.bank_rest_test_task.dto.card.CardCreateEditDTO;
import by.edu.bank_rest_test_task.dto.card.CardReadDTO;
import by.edu.bank_rest_test_task.entity.CardEntity;
import by.edu.bank_rest_test_task.repository.CardRepository;
import by.edu.bank_rest_test_task.util.mapper.card.CardCreateEditMapper;
import by.edu.bank_rest_test_task.util.mapper.card.CardReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardCreateEditMapper cardCreateEditMapper;
    private final CardReadMapper cardReadMapper;


    @Override
    public Optional<CardReadDTO> findById(UUID id) {
        return cardRepository.findById(id)
                .map(cardReadMapper::map);
    }

    @Override
    public List<CardReadDTO> findAllByOwner(UUID owner) {
        return cardRepository.findAllByOwnerOwnerId(owner).stream()
                .map(cardReadMapper::map)
                .map(this::maskCardDto)
                .toList();
    }

    @Override
    public List<CardReadDTO> findAll() {
        return cardRepository.findAll().stream()
                .map(cardReadMapper::map)
                .toList();
    }

    @Override
    public CardReadDTO create(CardCreateEditDTO dto) {

        return Optional.of(dto)
                .map(cardCreateEditMapper::map)
                .map(cardRepository::save)
                .map(cardReadMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<CardReadDTO> update(UUID id, CardCreateEditDTO dto) {
        return cardRepository.findById(id)
                .map(entity -> cardCreateEditMapper.map(dto, entity))
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map);
    }

    @Override
    public Optional<CardReadDTO> updateStatus(UUID id, CardCreateEditDTO dto) {
        Optional<CardReadDTO> ownerCard = findById(id);
        ownerCard.ifPresent(cardReadDTO -> dto.setAmount(cardReadDTO.amount()));
        return cardRepository.findById(id)
                .map(entity -> cardCreateEditMapper.map(dto, entity))
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map);
    }

    @Override
    public boolean delete(UUID id) {
        return cardRepository.findById(id)
                .map(entity -> {
                    cardRepository.delete(entity);
                    cardRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Page<CardReadDTO> findAllWithPaginationAndSorting(UUID ownerId, int offset, int limit, String variable) {
        Page<CardEntity> page = cardRepository.findAllByOwnerOwnerId(ownerId,
                PageRequest.of(offset, limit).withSort(Sort.by(variable)));
        return page.map(cardReadMapper::map)
                .map(this::maskCardDto);
    }

    @Override
    public List<CardReadDTO> findAllBy(String variable) {
        List<CardEntity> results = cardRepository.findAll(Sort.by(variable));
        return results.stream()
                .map(cardReadMapper::map)
                .map(this::maskCardDto)
                .toList();
    }

    private String maskCardNumber(String raw) {
        if (raw == null) return null;

        String digits = raw.replaceAll("\\D", "");
        if (digits.isEmpty()) return raw;

        if (digits.length() != 16) {
            String last4 = digits.length() <= 4 ? digits : digits.substring(digits.length() - 4);
            int maskedLen = Math.max(0, digits.length() - 4);
            String sb = "*".repeat(maskedLen) +
                    last4;

            return insertSpacesEvery4(sb);
        }

        String first4 = digits.substring(0, 4);
        String last4 = digits.substring(12);

        return first4 + " " + "****" + " " + "****" + " " + last4;
    }

    private String insertSpacesEvery4(String s) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && i % 4 == 0) out.append(' ');
            out.append(s.charAt(i));
        }
        return out.toString();
    }

    private CardReadDTO maskCardDto(CardReadDTO dto) {
        if (dto == null) return null;

        return new CardReadDTO(
                dto.id(),
                maskCardNumber(dto.cardNumber()),
                dto.amount(),
                dto.expiryDate(),
                dto.ownerId(),
                dto.statusId()
        );
    }
}
