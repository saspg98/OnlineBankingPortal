package ui;

import io.reactivex.disposables.CompositeDisposable;
import viewmodel.LoginViewModel;

//TODO: implement LoginScreen
public abstract class LoginScreen {

    CompositeDisposable mObservables = new CompositeDisposable();
    LoginViewModel mViewModel;

    //Call during initialization
    void init() {
        //TODO: initialize ViewModel
//        mViewModel = new LoginViewModel(Some data model);
        mObservables.add(mViewModel.getAuthorizationStream()
                .subscribe(this::onLoginAttempt, this::onError));
    }

    abstract void onLoginAttempt(boolean isCorrect);

    abstract void onError(Throwable error);

    //Call when no longer needed
    void onClose() {
        mObservables.clear();
    }
}
