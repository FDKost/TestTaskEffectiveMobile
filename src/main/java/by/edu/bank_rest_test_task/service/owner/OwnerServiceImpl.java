package by.edu.bank_rest_test_task.service.owner;

import by.edu.bank_rest_test_task.dto.owner.OwnerCreateEditDTO;
import by.edu.bank_rest_test_task.dto.owner.OwnerReadDTO;
import by.edu.bank_rest_test_task.repository.OwnerRepository;
import by.edu.bank_rest_test_task.util.mapper.owner.OwnerCreateEditMapper;
import by.edu.bank_rest_test_task.util.mapper.owner.OwnerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerCreateEditMapper ownerCreateEditMapper;
    private final OwnerReadMapper ownerReadMapper;

    @Override
    public Optional<OwnerReadDTO> findById(UUID id) {
        return ownerRepository.findById(id)
                .map(ownerReadMapper::map);
    }

    @Override
    public Optional<OwnerReadDTO> findByUsername(String username) {
        return ownerRepository.findByUsername(username)
                .map(ownerReadMapper::map);
    }

    @Override
    public OwnerReadDTO create(OwnerCreateEditDTO dto) {
        return Optional.of(dto)
                .map(ownerCreateEditMapper::map)
                .map(ownerRepository::save)
                .map(ownerReadMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<OwnerReadDTO> update(UUID id, OwnerCreateEditDTO dto) {
        return ownerRepository.findById(id)
                .map(entity -> ownerCreateEditMapper.map(dto, entity))
                .map(ownerRepository::saveAndFlush)
                .map(ownerReadMapper::map);
    }

    @Override
    public boolean delete(UUID id) {
        return ownerRepository.findById(id)
                .map(entity -> {
                    ownerRepository.delete(entity);
                    ownerRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
