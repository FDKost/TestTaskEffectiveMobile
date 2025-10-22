package by.edu.bank_rest_test_task.util.mapper.status;

import by.edu.bank_rest_test_task.dto.status.StatusCreateEditDTO;
import by.edu.bank_rest_test_task.entity.StatusEntity;
import by.edu.bank_rest_test_task.util.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class StatusCreateEditMapper implements Mapper<StatusCreateEditDTO, StatusEntity> {
    @Override
    public StatusEntity map(StatusCreateEditDTO object) {
        StatusEntity statusEntity = new StatusEntity();
        fillStatusCreateEditDTO(object, statusEntity);
        return statusEntity;
    }

    @Override
    public StatusEntity map(StatusCreateEditDTO fromObject, StatusEntity toObject) {
        fillStatusCreateEditDTO(fromObject, toObject);
        return toObject;
    }

    private void fillStatusCreateEditDTO(StatusCreateEditDTO object, StatusEntity statusEntity) {
        statusEntity.setStatusName(object.statusName());
        statusEntity.setId(object.statusId());
    }
}
