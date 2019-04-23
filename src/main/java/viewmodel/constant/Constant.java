package viewmodel.constant;

import java.math.BigDecimal;

public class Constant {

    public static class Path {
        public static final String MAIN_SCREEN_VIEW = "MainScreenLayout.fxml";
        public static final String TRANSFER_VIEW = "TransferLayout.fxml";
        public static final String SIDE_PANE = "SidePanel.fxml";
        public static final String HOME_SCREEN_VIEW = "HomeLayout.fxml";
        public static final String LOGIN_VIEW = "LoginScreen.fxml";
        public static final String CHANGE_PASSWORD_VIEW = "ChangePasswordLayout.fxml";
        public static final String SIGNUP_VIEW = "SignUpScreen.fxml";
        public static final String MY_ACCOUNT_LAYOUT = "MyAccountLayout.fxml";
        public static final String BENEFACTOR_DETAIL_VIEW = "";
        public static final String HELP_VIEW = "HelpLayout.fxml";
        public static final String TRANSACTION_VIEW = "TransactionLayout.fxml";
        public static final String ADD_PAYEE = "AddPayeeLayout.fxml";
    }

    public static class Connection {
        public static final String DBMS_URL = "jdbc:mysql://localhost:3306/bank";
        public static final String DBMS_PASS = "root";
        public static final String DBMS_USER = "root";
        public static final String DBMS_FULL_URL = "jdbc:mysql://admin:saurav%4017@localhost:3306/bank";
    }

    public static class Bank {
        public static final BigDecimal MIN_ACCOUNT_BALANCE = new BigDecimal(2000);
    }


}
