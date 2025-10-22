package by.edu.bank_rest_test_task.util.mapper.owner;

import by.edu.bank_rest_test_task.dto.owner.OwnerReadDTO;
import by.edu.bank_rest_test_task.entity.OwnerEntity;
import by.edu.bank_rest_test_task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class OwnerReadMapper implements Mapper<OwnerEntity, OwnerReadDTO> {
    @Override
    public OwnerReadDTO map(OwnerEntity object) {
        return new OwnerReadDTO(
                object.getOwnerId(),
                object,
                object.getUsername()
        );
    }
}
