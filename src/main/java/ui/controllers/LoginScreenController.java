package ui.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import misc.debug.Debug;
import ui.ViewManager;
import viewmodel.LoginViewModel;
import viewmodel.constant.Constant;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Pranek
 */
public class LoginScreenController implements Initializable {

    private static final String TAG = "LoginScreenController";
    LoginViewModel viewModel = null;
    CompositeDisposable mObservables = new CompositeDisposable();

    @FXML
    private TextField TfUsername;
    @FXML
    private TextField TfPassword;
    @FXML
    private Label errorLabel;

    @FXML
    private void onSignUp(ActionEvent actionEvent) {

        disposeObservables();
        viewModel.onSignUp(Constant.Path.SIGNUP_VIEW);
    }

    @FXML
    private void onLoginClicked(ActionEvent actionEvent) {

        Debug.err("LOGIN","Sign Up button clicked");

        viewModel.setUsername(TfUsername.getText().trim());
        viewModel.setPassword(TfPassword.getText().trim());
        viewModel.onLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        errorLabel.setVisible(false);
        viewModel = new LoginViewModel(ViewManager.getInstance().getLoginAuthDataModelInstance());
        createObservables();

    }

    private void createObservables(){

        mObservables.add(viewModel.getValidationStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe((answer) -> {
                    showErrorLabel(!answer);
                }, this::onLoginError));
        mObservables.add(viewModel.getAuthorizationStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::loginAuth, this::onLoginError));
    }

    private void onLoginError(Throwable throwable) {
        //Debug.printThread(TAG);
        Debug.log(TAG, "onLoginError!! printing throwable", throwable);
        errorLabel.setText("Error in Logging!");
        errorLabel.setVisible(true);
    }

    private void loginAuth(Boolean b) {

        if (b) {
            viewModel.onSuccessfullLogin();
        } else {
            System.err.println("Wrong Username or Password");
            errorLabel.setText("Invalid Username or Password!");
            errorLabel.setVisible(true);
        }
    }

    private void showErrorLabel(boolean b) {

        if (b) {
            System.err.println("Invalid Username or Password");
            errorLabel.setText("Invalid Username or Password!");
            errorLabel.setVisible(true);
        }
    }

    public void disposeObservables() {
        mObservables.clear();
    }
}
