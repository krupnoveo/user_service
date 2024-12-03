package ru.krupnoveo.edu.user_service.api.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("Пользователь с UUID %s не найден".formatted(id));
    }
    public UserNotFoundException() {
        super("Пользователь не найден");
    }
}
