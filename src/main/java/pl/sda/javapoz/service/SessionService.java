package pl.sda.javapoz.service;

import pl.sda.javapoz.model.UserEntity;

public interface SessionService {
    UserEntity getCurrentUser();
}
