package misc.validator;

import misc.debug.Debug;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;

public class InputValidator {

    //Checks the format of the all user input data

    public static boolean validateUsername(String username) {
        if (username == null || username.length() < 4)
            return false;
        RegexValidator validator = new RegexValidator("^([a-zA-Z0-9]*)([^\\s])$");
        if (!validator.isValid(username)) {
            Debug.err("Username", "Invalid");
            return false;
        }
        return false;
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.length() < 4)
            return false;
        RegexValidator validator = new RegexValidator("^([0-9a-zA-Z]+)([^\\s])$");
        if (!validator.isValid(password)) {
            Debug.log("Password", "Invalid");
            return false;
        }
        return false;
    }

    public static boolean validateIFSC(String ifsc) {
        return true;
    }

    public static boolean validateAdhaar(String adhaar) {
        return true;
    }

    public static boolean validateAccountNumber(Long accountNumber) {
        return true;
    }

    public static boolean validateEmail(String email) {
        return EmailValidator.getInstance().isValid(email);

    }


}
