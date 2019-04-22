/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import misc.debug.Debug;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class AddPayeeLayoutController implements Initializable,ViewModelUser {

    private final String TAG = "AddPayeeLayoutController";

    @FXML
    private Label LName;
    @FXML
    private Label LIFSC;
    @FXML
    private Label LBranchName;
    @FXML
    private TextField TfBranchName;
    @FXML
    private TextField TfName;
    @FXML
    private Label LAccountNumber;
    @FXML
    private TextField TfAccountNumber;
    @FXML
    private Button ButtonAddPayee;
    @FXML
    private TextField TfIFSC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onAddPayeeClicked(ActionEvent actionEvent) {
    }

    @Override
    public void createObservables() {

    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
    }
}
