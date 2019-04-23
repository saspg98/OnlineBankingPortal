/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import misc.debug.Debug;
import viewmodel.HelpScreenViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class HelpLayoutController implements Initializable, ViewModelUser {

    private final String TAG = "HelpLayoutConstroller";

    @FXML
    private Label LEmailIdOutput;
    @FXML
    private Label LContactNumberOutput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HelpScreenViewModel viewModel = new HelpScreenViewModel();

        LEmailIdOutput.setText(viewModel.getEmailId());
        LContactNumberOutput.setText(viewModel.getContactNum());
    }

    @Override
    public void createObservables() {

    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG, "Disposing observables");
    }
}
