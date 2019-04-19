package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.BankAccount;

import java.util.Map;

public class HomeViewModel {
    private UserDataModel mDataModel;

    public HomeViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }


    public Observable<BankAccount> getSelectedAccount() {
        return mDataModel.getPrimaryUserAccount();
    }

}
