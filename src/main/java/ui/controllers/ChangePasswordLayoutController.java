/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import misc.debug.Debug;
import misc.validator.InputValidator;
import viewmodel.ChangePasswordViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class ChangePasswordLayoutController implements Initializable,ViewModelUser {

    private final String TAG = "ChangePasswordLayoutController";
    private ChangePasswordViewModel viewModel;
    CompositeDisposable mObservables = new CompositeDisposable();
    private String oldPwd = "Default";
    private String newPwd = "Default";

    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField confirmPass;
    @FXML
    private Label passChangeLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new ChangePasswordViewModel();
        oldPass.setText("");
        newPass.setText("");
        confirmPass.setText("");
        passChangeLabel.setVisible(false);
    }

    @FXML
    private void confirmButtonClicked(ActionEvent actionEvent) {
        oldPwd = oldPass.getText().trim();
        newPwd = newPass.getText().trim();
        if(validateInput(oldPwd,newPwd,confirmPass.getText().trim())) {
            mObservables.add(viewModel.setPassword(oldPwd, newPwd)
                    .observeOn(JavaFxScheduler.platform())
                    .subscribe(this::successfulPasswordChange, this::showError));
        }
    }

    private boolean validateInput(String newPwd, String oldPwd, String confirmPwd) {

        if(!(InputValidator.validatePassword(newPwd)&&InputValidator.validatePassword(oldPwd))) {
            passChangeLabel.setText("Password is weak!");
            return false;
        }

        if(!(InputValidator.validatePassword(newPwd,confirmPwd))){
            passChangeLabel.setText("Password do not match");
            return false;
        }
        return true;
    }

    @Override
    public void createObservables() {
      Debug.log(TAG,"Not Implemented createObservables");
    }

    private void showError(Throwable throwable) {
        Debug.err(TAG,(Object[]) throwable.getStackTrace());
    }

    private void successfulPasswordChange(boolean isSuccessful) {
        if(isSuccessful)
            passChangeLabel.setText("Password change successful!");
        else
            passChangeLabel.setText("Wrong old password!");
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }

    @FXML
    private void onOldPassClicked(ActionEvent actionEvent) {
        passChangeLabel.setVisible(false);
    }

    @FXML
    private void onNewPassTextfield(ActionEvent actionEvent) {
        passChangeLabel.setVisible(false);
    }

    @FXML
    private void onConfirmTextfield(ActionEvent actionEvent) {
        passChangeLabel.setVisible(false);
    }
}
