package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.User;

public class SettingsViewModel {

    UserDataModel mDataModel;


    public SettingsViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    public Observable<User> getUserDetails(){
        return mDataModel.fetchUserDetails();
    }

    public void setUserDetails(User u){
        mDataModel.updateUser(u);
    }




}
