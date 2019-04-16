package ui.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import ui.ViewManager;
import viewmodel.LoginViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Pranek
 */
public class LoginScreenController implements Initializable {

    LoginViewModel viewModel = null;

    @FXML
    private TextField TfUsername;
    @FXML
    private TextField TfPassword;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        System.err.println("Sign Up button clicked");

        viewModel.setUsername(TfUsername.getText());
        viewModel.setPassword(TfPassword.getText());
        viewModel.onLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        viewModel = new LoginViewModel(ViewManager.getInstance().getLoginAuthDataModelInstance());
        viewModel.getValidationStream()
                .subscribe((answer)-> {showErrorLable(!answer);});
        viewModel.getAuthorizationStream()
                .subscribe(this::loginAuth);
    }

    private void loginAuth(Boolean b) {

        if(b){
            viewModel.onSuccessfullLogin();
        }else{
            // Invalid username and password
        }

    }

    private void showErrorLable(boolean b) {

        if(b){
            //show SQL injection error
        }
    }

}
