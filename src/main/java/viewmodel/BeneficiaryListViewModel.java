package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.Beneficiary;

import java.util.List;

public class BeneficiaryListViewModel {
    private UserDataModel userDataModel;

    public BeneficiaryListViewModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
    }

    public Observable<List<Beneficiary>> getBeneficiaries() {
        return userDataModel.getUserBeneficiaries();
    }
}
