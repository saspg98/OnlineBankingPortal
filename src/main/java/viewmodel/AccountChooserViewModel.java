package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import model.BankAccount;
import ui.ViewManager;
import viewmodel.constant.Constant;

//Used for opening the MakeTransaction Screen
public class AccountChooserViewModel {
    private UserDataModel mDataModel;
    private BehaviorSubject<BankAccount> mSelectedAccount = BehaviorSubject.create();

    public AccountChooserViewModel(UserDataModel mDataModel) {
        this.mDataModel = mDataModel;
    }

    public void accountSelected(String account) {
        mSelectedAccount.onNext(mDataModel.getAccountFromString(account));
    }

    public Observable<BankAccount> getSelectedAccount() {
        return mSelectedAccount;
    }

    public void openTransferPage() throws Exception {
        ViewManager.getInstance().setScene(Constant.Path.TRANSFER_VIEW, mSelectedAccount.blockingLast());
    }
}
