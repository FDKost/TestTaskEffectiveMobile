package by.edu.bank_rest_test_task.service.status;

import by.edu.bank_rest_test_task.dto.status.StatusCreateEditDTO;
import by.edu.bank_rest_test_task.dto.status.StatusReadDTO;
import by.edu.bank_rest_test_task.repository.StatusRepository;
import by.edu.bank_rest_test_task.util.mapper.status.StatusCreateEditMapper;
import by.edu.bank_rest_test_task.util.mapper.status.StatusReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final StatusCreateEditMapper statusCreateEditMapper;
    private final StatusReadMapper statusReadMapper;

    @Override
    public Optional<StatusReadDTO> findById(UUID id) {
        return statusRepository.findById(id)
                .map(statusReadMapper::map);
    }

    @Override
    public StatusReadDTO create(StatusCreateEditDTO dto) {
        return Optional.of(dto)
                .map(statusCreateEditMapper::map)
                .map(statusRepository::save)
                .map(statusReadMapper::map)
                .orElseThrow();
    }

    @Override
    public Optional<StatusReadDTO> update(UUID id, StatusCreateEditDTO dto) {
        return statusRepository.findById(id)
                .map(entity -> statusCreateEditMapper.map(dto, entity))
                .map(statusRepository::saveAndFlush)
                .map(statusReadMapper::map);
    }

}
