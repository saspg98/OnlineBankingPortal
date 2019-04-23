package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.BankAccount;

public class HomeViewModel {
    private UserDataModel mDataModel;

    public HomeViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    public Observable<BankAccount> getSelectedAccount() {
        return mDataModel.getPrimaryUserAccount();
    }

}
