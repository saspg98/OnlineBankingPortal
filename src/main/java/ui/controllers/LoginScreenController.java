package ui.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import misc.debug.Debug;
import ui.ViewManager;
import viewmodel.LoginViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Pranek
 */
public class LoginScreenController implements Initializable {

    LoginViewModel viewModel = null;
    CompositeDisposable mObservables = new CompositeDisposable();

    private static final String TAG = "LoginScreenController";

    @FXML
    private TextField TfUsername;
    @FXML
    private TextField TfPassword;
    @FXML
    private Label ErrorLable;

    @FXML
    private void onSignUp(ActionEvent actionEvent) {
    }

    @FXML
    private void onLoginIn(ActionEvent actionEvent) {

        System.err.println("Sign Up button clicked");

        viewModel.setUsername(TfUsername.getText());
        viewModel.setPassword(TfPassword.getText());
        viewModel.onLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        viewModel = new LoginViewModel(ViewManager.getInstance().getLoginAuthDataModelInstance());
        mObservables.add(viewModel.getValidationStream()
                .observeOn(Schedulers.trampoline())
                .subscribe((answer) -> {
                    showErrorLable(!answer);
                }, this::onError));
        mObservables.add(viewModel.getAuthorizationStream()
                .observeOn(Schedulers.trampoline())
                .subscribe(this::loginAuth, this::onError));
    }

    private void onError(Throwable throwable) {
        Debug.printThread(TAG);
        Debug.log(TAG, "Onerror!! printing throwable", throwable);
    }

    private void loginAuth(Boolean b) {

        if (b) {
            viewModel.onSuccessfullLogin();
        } else {
            // Invalid username and password
            System.err.println("Wrong Username or Password");
        }

    }

    private void showErrorLable(boolean b) {
        Debug.err(TAG, Thread.currentThread().getName());
        Debug.log(TAG, "Should I show error Label?", b);
        if (b) {
            System.err.println("Invalid Username or Password");
            //show SQL injection error
        }
    }

    private void onDispose() {
        mObservables.clear();
    }
}
