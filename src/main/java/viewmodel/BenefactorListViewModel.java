package viewmodel;

import datamodel.UserDataModel;
import io.reactivex.Observable;
import model.Benefactor;
import model.Transaction;

import java.util.List;

public class BenefactorListViewModel {
    private UserDataModel userDataModel;

    public BenefactorListViewModel(UserDataModel userDataModel) {
        this.userDataModel = userDataModel;
    }

    public Observable<List<Benefactor>> getBenefactors(){
        return userDataModel.getUserBenefactors();
    }
}
