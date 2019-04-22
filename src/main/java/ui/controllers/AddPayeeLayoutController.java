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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import misc.debug.Debug;
import ui.ViewManager;
import viewmodel.AddBeneficiaryViewModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class AddPayeeLayoutController implements Initializable,ViewModelUser, DataReceiver {

    private final String TAG = "AddPayeeLayoutController";
    private AddBeneficiaryViewModel viewModel;
    private CompositeDisposable mObservables = new CompositeDisposable();

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
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        errorLabel.setVisible(false);

    }

    @Override
    public <T> void receiveData(T... data) {
        long accno = (Long) data[0];
        viewModel = new AddBeneficiaryViewModel(ViewManager.getInstance().getUserDataModel(),accno);

        createObservables();
    }

    @FXML
    private void onAddPayeeClicked(ActionEvent actionEvent) {
        viewModel.setBeneficiaryAccNo(TfAccountNumber.getText());
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getValidationStream()
                        .observeOn(JavaFxScheduler.platform())
                        .subscribe(this::isValid, this::onError));
        mObservables.add(viewModel.getSuccessListenerStream()
                .observeOn(JavaFxScheduler.platform())
                .subscribe(this::isSuccessful, this::onError));

    }

    private void isValid(boolean isValid) {
        if(!isValid){
            //TODO: Show error message, acc no is not 11 digit long etc
        }
    }

    private void isSuccessful(boolean isSuccesful) {
        if(!isSuccesful){
            //TODO: Show other error message, acc doesn't exist etc
        }
    }

    @Override
    public void disposeObservables() {
        Debug.log(TAG,"Disposing Observables");
    }


}
