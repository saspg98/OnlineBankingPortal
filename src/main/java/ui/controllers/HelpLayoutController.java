/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import javafx.fxml.Initializable;
import viewmodel.HelpScreenViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class HelpLayoutController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HelpScreenViewModel viewModel = new HelpScreenViewModel();
        //set UI elements to
        viewModel.getContactNum();
        viewModel.getEmailId();
    }

}
