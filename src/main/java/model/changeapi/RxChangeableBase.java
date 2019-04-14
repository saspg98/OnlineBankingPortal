package model.changeapi;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposables;

public class RxChangeableBase{

    private RxChangeableBase() {
    }

    public static <T extends ChangeableBase> Observable<T> observe(final T changeableBase) {
        return Observable.create((emitter) -> {
            final ChangeListener<T> changeListener = (changedEntity) ->
                    emitter.onNext(changedEntity);

                emitter.setDisposable(Disposables.fromAction(() ->
                    changeableBase.removeChangeListener(changeListener)));

            changeableBase.addChangeListener(changeListener);
        });
    }

}
