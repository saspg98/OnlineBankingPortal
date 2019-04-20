package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import misc.debug.Debug;
import ui.ViewManager;
import viewmodel.SignupViewModel;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class SignUpScreenController implements Initializable, ViewModelUser {

    private static final String TAG = "SignUpScreenController";
    SignupViewModel viewModel = null;
    CompositeDisposable mObservables = new CompositeDisposable();
    
    @FXML
    public Label LError;
    @FXML
    private TextField TfName;
    @FXML
    private TextField TfDateOfBirth;
    @FXML
    private TextField TfEmail;
    @FXML
    private TextField TfAccountNo;
    @FXML
    private TextField TfAadhaarCard;
    @FXML
    private TextField TfUsername;
    @FXML
    private PasswordField TfPassword;
    @FXML
    private PasswordField TfConfirmPassword;
    @FXML
    private TextField TfAddressLine;
    @FXML
    private TextField BCode;

    @FXML
    public void onSignUpClicked(ActionEvent actionEvent) {

        Debug.err("SIGNUP","Sign up button clicked");
        try {
            viewModel.setAdhaar(Long.parseLong(TfAadhaarCard.getText().trim()));
            viewModel.setAccountNumber(Long.parseLong(TfAccountNo.getText().trim()));
            viewModel.setAddress(TfAddressLine.getText().trim());
            viewModel.setDOB(new SimpleDateFormat("dd/MM/yyyy").parse(TfDateOfBirth.getText().trim()));
            viewModel.setCPassword(TfConfirmPassword.getText().trim());
            viewModel.setName(TfName.getText().trim());
            viewModel.setUsername(TfUsername.getText().trim());
            viewModel.setPassword(TfPassword.getText().trim());
            viewModel.setEmail(TfEmail.getText().trim());
            viewModel.setBCode(Long.parseLong(BCode.getText()));
            viewModel.onSignUp();
        }catch (NumberFormatException e){
            showErrorLabel(true);
        }catch (ParseException e){
            showErrorLabel(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LError.setVisible(false);
        viewModel = new SignupViewModel(ViewManager.getInstance().getSignUpAuthDataModelInstance());
        createObservables();
    }

    public void createObservables(){

        mObservables.add(viewModel.getValidationStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe((answer) -> {
                    showErrorLabel(!answer);
                }, this::onSignUpError));
        mObservables.add(viewModel.getConfirmSignUpStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::signUpConfirm, this::onSignUpError));
    }

    private void showErrorLabel(boolean b) {

        if (b) {
            System.err.println("Invalid entry in field(s)");
            LError.setText("Invalid field entry");
            LError.setVisible(true);
        }
    }

    private void onSignUpError(Throwable throwable) {
        //Debug.printThread(TAG);
        Debug.log(TAG, "onLoginError!! printing throwable", throwable);
        LError.setText("Error in Signing!");
        LError.setVisible(true);
    }

    private void signUpConfirm(Boolean b) {

        if (b) {
            LError.setText("Sign Up successful!");
            LError.setVisible(true);
            viewModel.onSuccessfullSignUp();
        } else {
            System.err.println("Account already exists");
            LError.setText("Account already exists!");
            LError.setVisible(true);
            TfAadhaarCard.setText("");
            TfAccountNo.setText("");
            TfAddressLine.setText("");
            TfDateOfBirth.setText("");
            TfConfirmPassword.setText("");
            TfName.setText("");
            TfUsername.setText("");
            TfPassword.setText("");
            TfEmail.setText("");
        }
    }

    public void disposeObservables(){
        mObservables.clear();
    }
    
}
