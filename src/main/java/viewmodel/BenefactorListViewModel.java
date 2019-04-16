package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.Beneficiary;

import java.util.List;

public class BenefactorListViewModel {
    private UserDataModel userDataModel;

    public BenefactorListViewModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
    }

    public Observable<List<Beneficiary>> getBenefactors(){
        return userDataModel.getUserBenefactors();
    }
}
