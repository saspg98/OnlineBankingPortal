/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import misc.debug.Debug;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class SettingsLayoutController implements Initializable,ViewModelUser {

    private final String TAG = "SettingsLayoutController";

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
        // TODO
    }

    @FXML
    private void confirmButtonClicked(ActionEvent actionEvent) {
    }

    @Override
    public void createObservables() {

    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
    }
}
