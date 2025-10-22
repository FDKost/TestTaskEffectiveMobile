package by.edu.bank_rest_test_task.util.mapper;

import java.io.IOException;

public interface Mapper<F, T> {
    T map(F object) throws IOException;

    default T map(F fromObject, T toObject) throws IOException {
        return toObject;
    }
}
