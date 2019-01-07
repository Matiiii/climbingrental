package pl.sda.javapoz.service;

import pl.sda.javapoz.model.entity.UserEntity;

public interface SessionService {
    UserEntity getCurrentUser();
}
