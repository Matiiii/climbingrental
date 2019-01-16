package pl.onsight.wypozyczalnia.validator;

import pl.onsight.wypozyczalnia.model.entity.UserEntity;

public interface RegisterValidator {
    boolean isEmailTaken(UserEntity userEntity);
}
