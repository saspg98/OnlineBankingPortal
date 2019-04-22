package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.User;

import java.util.List;
import java.util.Map;

public class SettingsViewModel {

    UserDataModel mDataModel;

    public SettingsViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    public Observable<Map<User, List<Long>>> getUserDetails() {
        return mDataModel.fetchUserDetails();
    }

    public void setUserDetails(User u) {
        mDataModel.updateUser(u);
    }


}
