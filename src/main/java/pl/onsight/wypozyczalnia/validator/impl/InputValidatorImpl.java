package pl.onsight.wypozyczalnia.validator.impl;

import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.validator.InputValidator;

@Component
public class InputValidatorImpl implements InputValidator {
    @Override
    public boolean isInputValid(String input) {

        CharSequence[] dontAllowCharacter = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`",
                "=", "}", "{", "|", "'", "+", "[", "/", ">", "<", "+"};
        for (int x = 0; x < dontAllowCharacter.length; x++) {
            if (input.contains(dontAllowCharacter[x])) {
                return false;
            }
        }
        return true;
    }
}
