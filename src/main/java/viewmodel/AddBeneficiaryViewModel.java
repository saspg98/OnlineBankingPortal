package viewmodel;

import datamodel.UserDataModel;

public class AddBeneficiaryViewModel {
    private UserDataModel mUserDataModel;

    public AddBeneficiaryViewModel(UserDataModel mUserDataModel) {
        this.mUserDataModel = mUserDataModel;
    }

    public boolean addBeneficiary(String beneficiary){
        return mUserDataModel.addBeneficiary(beneficiary);
    }
}
