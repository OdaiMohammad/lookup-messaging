package ae.accumed.lookuprequestsmanager.validation;

import ae.accumed.lookuprequestsmanager.dto.CreateAccountDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, CreateAccountDTO> {

    @Override
    public void initialize(PasswordMatch p) {

    }

    public boolean isValid(CreateAccountDTO createAccountDTO, ConstraintValidatorContext c) {
//        String plainPassword = createAccountDTO.getPassword();
//        String repeatPassword = createAccountDTO.getRepeatPassword();
//
//        return plainPassword != null && plainPassword.equals(repeatPassword);

        return true;
    }

}