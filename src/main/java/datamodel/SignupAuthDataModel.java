package datamodel;

import io.reactivex.Observable;
import model.SignupCredentials;

public interface SignupAuthDataModel {
    //Send data to db for checking
    void checkSignUpDetails(SignupCredentials credentials);

    //Returns true if signup is successful, suggest better name plz
    Observable<Boolean> getConfirmSignUpStream();
}
