package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.entity.UserEntity;

public interface SessionService {
    UserEntity getCurrentUser();
}
