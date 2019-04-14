package model.changeapi;


public interface ChangeListener<I extends ChangeableBase> {
    void onChanged(I changeableInstance);
}