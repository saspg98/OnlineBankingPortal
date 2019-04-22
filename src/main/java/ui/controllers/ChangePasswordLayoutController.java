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

    @FXML
    private PasswordField oldPass;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField confirmPass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new ChangePasswordViewModel();
        oldPass.setText("");
        newPass.setText("");
        confirmPass.setText("");
    }

    @FXML
    private void confirmButtonClicked(ActionEvent actionEvent) {
        String oldPass = this.oldPass.getText().trim();
        String newPass = this.newPass.getText().trim();
        InputValidator.validatePassword(oldPass);
        InputValidator.validatePassword(newPass);
        mObservables.add(viewModel.setPassword(oldPass,newPass)
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::successfulPasswordChange ,this::showErrorLabel));
    }

    @Override
    public void createObservables() {
      Debug.log(TAG,"Not Implemented createObservables");
    }

    private void showErrorLabel(Throwable throwable) {

    }

    private void successfulPasswordChange(boolean isSuccessful) {

    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
        mObservables.clear();
    }
}
