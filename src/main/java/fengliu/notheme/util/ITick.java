package fengliu.notheme.util;

public interface ITick {
    int getMaxTick();
    int getTick();
    void addTick();
    void clearTick();
    default boolean canAccomplish(){
        this.addTick();
        return this.getTick() >= getMaxTick();
    }
}
