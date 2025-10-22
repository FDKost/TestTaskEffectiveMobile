package by.edu.bank_rest_test_task.util.mapper.owner;

import by.edu.bank_rest_test_task.dto.owner.OwnerCreateEditDTO;
import by.edu.bank_rest_test_task.entity.OwnerEntity;
import by.edu.bank_rest_test_task.util.Role;
import by.edu.bank_rest_test_task.util.mapper.Mapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OwnerCreateEditMapper implements Mapper<OwnerCreateEditDTO, OwnerEntity> {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    @Override
    public OwnerEntity map(OwnerCreateEditDTO object) {
        OwnerEntity ownerEntity = new OwnerEntity();
        fillOwnerCreateEditDTO(object, ownerEntity);
        return ownerEntity;
    }

    @Override
    public OwnerEntity map(OwnerCreateEditDTO fromObject, OwnerEntity toObject) {
        fillOwnerCreateEditDTO(fromObject, toObject);
        return toObject;
    }

    private void fillOwnerCreateEditDTO(OwnerCreateEditDTO object, OwnerEntity ownerEntity) {
        ownerEntity.setRole(Role.valueOf(object.role()));
        ownerEntity.setPassword(encoder.encode(object.password()));
        ownerEntity.setUsername(object.username());
    }
}
