package by.edu.bank_rest_test_task.util.mapper.status;

import by.edu.bank_rest_test_task.dto.status.StatusReadDTO;
import by.edu.bank_rest_test_task.entity.StatusEntity;
import by.edu.bank_rest_test_task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class StatusReadMapper implements Mapper<StatusEntity, StatusReadDTO> {

    @Override
    public StatusReadDTO map(StatusEntity object) {
        return new StatusReadDTO(
                object.getId(),
                object.getStatusName()
        );
    }
}
