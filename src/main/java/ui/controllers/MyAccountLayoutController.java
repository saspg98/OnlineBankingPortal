/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import misc.debug.Debug;
import model.User;
import ui.ViewManager;
import viewmodel.SettingsViewModel;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Pranek
 */
public class MyAccountLayoutController implements Initializable, ViewModelUser {

    private CompositeDisposable mObservables = new CompositeDisposable();
    private SettingsViewModel viewModel;
    private final String TAG = "MyAccountLayoutController";
    private final HashMap<String, String> sex = new HashMap<>();

    @FXML
    private Label LNameOutput;
    @FXML
    private Label LDate;
    @FXML
    private Label LEmailIdOutput;
    @FXML
    private Label LPhoneNumberOutput;
    @FXML
    private Label LAddressOutput;
    @FXML
    private Label LAadhaarCardOutput;
    @FXML
    private Label LGenderOutput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewModel = new SettingsViewModel(ViewManager.getInstance().getUserDataModel());
        createObservables();
        sex.put("M", "Male");
        sex.put("F", "Female");
    }

    public void onUpdateInfo() {
        //TODO: Create a DefaultUser Object and call setUserDetails
        //User user = new DefaultUser(...);
        //viewModel.setUserDetails(user);
    }

    @Override
    public void createObservables() {
        mObservables.add(viewModel.getUserDetails()
                .observeOn(JavaFxScheduler.platform())
                .subscribe((user) -> setViews(user), this::onError));
    }

    public void setViews(Map<User, List<Long>> userMap) {
        if (userMap.size() > 1) {
            //Throw error
        } else {
            User user = userMap.keySet().iterator().next();
            LNameOutput.setText(user.name());
            LAadhaarCardOutput.setText(user.uid().toString());
            LAddressOutput.setText(user.Address());
            LDate.setText(user.DOB().toString());
            LEmailIdOutput.setText(user.Email());
            LGenderOutput.setText(sex.get(user.Sex()));
            StringBuilder builder = new StringBuilder();
            for (long l : userMap.get(user)) {
                builder.append(l + "\n");
            }

            LPhoneNumberOutput.setText(builder.toString());
        }

    }


    @Override
    public void disposeObservables() {
        Debug.log(TAG, "Disposing Observables");
        mObservables.clear();
    }

    @Override
    public void onError(Throwable throwable) {
        Debug.err(TAG, throwable);
    }
}
