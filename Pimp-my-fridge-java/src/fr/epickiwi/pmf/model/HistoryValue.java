package fr.epickiwi.pmf.model;

public class HistoryValue<T> {

    private long sysMili;
    private T value;

    public HistoryValue(long sysMili, T value) {
        this.sysMili = sysMili;
        this.value = value;
    }

    public long getSysMili() {
        return sysMili;
    }

    public void setSysMili(long date) {
        this.sysMili = date;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
