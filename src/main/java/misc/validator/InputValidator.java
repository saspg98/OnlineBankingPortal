package misc.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    //Checks the format of the all user input data

    public static boolean validateUsername(String username) {
//        if (username == null || username.length() < 4)
//            return false;
////        RegexValidator validator = new RegexValidator("^([a-zA-Z0-9]*)([^\\s])$");
////        if (!validator.isValid(username)) {
////            Debug.err("Username", "Invalid");
////            return false;
////        }
        return username != null && (!username.equals(""));
    }

    public static boolean validatePassword(String password, String cPassword) {
//        if (password == null || password.length() < 4 ||password!=cPassword)
//            return false;
////        RegexValidator validator = new RegexValidator("^([a-zA-Z]{1,})([0-9]{1,})([^\\s])$");
////        if (!(validator.isValid(password) && validator.isValid(cPassword))) {
////            Debug.log("Password", "Invalid");
////            return false;
////        }
        return true;
    }

    public static boolean validatePassword(String password) {
//        if (password == null || password.length() < 4 )
//            return false;
////        RegexValidator validator = new RegexValidator("^([a-zA-Z]{1,})([0-9]{1,})([^\\s])$");
////        if (!validator.isValid(password)){
////            Debug.log("Password", "Invalid");
////            return false;
////        }
        return !password.equals("") && password != null;
    }

    public static boolean validateBCode(Long BCode) {
        return true;
    }

    public static boolean validateAdhaar(Long adhaar) {
//        if(String.valueOf(adhaar).length()!= 12) {
//            Debug.err("Wrong Adhaar");
//            return false;
//        }
        return true;
    }

    public static boolean validateAccountNumber(Long accountNumber) {
//        if(String.valueOf(accountNumber).length()!= 11) {
//            Debug.err("Wrong account number");
//            return false;
//        }
        return true;
    }

    public static boolean validateEmail(String email) {

        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
