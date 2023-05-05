package fengliu.notheme.util;

/**
 * tick 工具
 */
public interface ITick {
    /**
     * 获取最大 tick
     * @return 最大 tick
     */
    int getMaxTick();

    /**
     * 获取当前 tick
     * @return tick
     */
    int getTick();

    /**
     * 增加一 tick
     */
    void addTick();

    /**
     * 清空 tick
     */
    void clearTick();

    /**
     * 增加一 tick, 并检查是否可以执行 tick 任务
     * @return 可以 true
     */
    default boolean canAccomplish(){
        this.addTick();
        return this.getTick() >= getMaxTick();
    }
}
