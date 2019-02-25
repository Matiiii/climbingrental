package pl.onsight.wypozyczalnia.validator;

public interface UserValidator {

    boolean isUserHavePermissionToSeeThisOrder(Long userId, Long orderId);
}
