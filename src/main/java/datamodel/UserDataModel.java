package datamodel;

import io.reactivex.Observable;
import model.User;

public interface UserDataModel {
    Observable<User> fetchUserDetails();

    void updateUser(User u);
}
