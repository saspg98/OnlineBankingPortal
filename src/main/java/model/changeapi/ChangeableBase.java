package model.changeapi;

import model.changeapi.ChangeListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class ChangeableBase {

    private List<ChangeListener> changeListeners = new CopyOnWriteArrayList<>();

    public void notifyListeners(){
        for(ChangeListener changeListener: changeListeners) {
            changeListener.onChanged(this);
        }
    }

    public void addChangeListener(ChangeListener listener) {
        this.changeListeners.add(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        this.changeListeners.remove(listener);
    }

}
