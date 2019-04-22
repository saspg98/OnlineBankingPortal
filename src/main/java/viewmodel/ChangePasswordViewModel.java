package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;


public class ChangePasswordViewModel {

    private UserDataModel mDataModel;

    public Observable<Boolean> setPassword(String oldPass, String newPass) {
        return mDataModel.updatePassword(oldPass,newPass);
    }
}
