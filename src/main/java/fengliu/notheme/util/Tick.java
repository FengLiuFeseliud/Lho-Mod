package fengliu.notheme.util;

public class Tick implements ITick {
    private final int maxTick;
    private int tick;

    public Tick(int maxTick){
        this.maxTick = maxTick;
    }

    @Override
    public int getMaxTick() {
        return this.maxTick;
    }

    @Override
    public int getTick() {
        return this.tick;
    }

    @Override
    public void addTick() {
        this.tick ++;
    }

    @Override
    public void clearTick() {
        this.tick = 0;
    }

    public interface tickCallBack{
        void run();
    }

    public void accomplish(tickCallBack callBack){
        if (!this.canAccomplish()){
            return;
        }

        callBack.run();
    }
}
