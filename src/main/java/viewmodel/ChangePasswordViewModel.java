package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;


public class ChangePasswordViewModel {

    private UserDataModel mDataModel;

    public ChangePasswordViewModel(UserDataModel userDataModel) {
        mDataModel = userDataModel;
    }

    public Observable<Boolean> setPassword(String oldPass, String newPass) {
        return mDataModel.updatePassword(oldPass, newPass);
    }
}
