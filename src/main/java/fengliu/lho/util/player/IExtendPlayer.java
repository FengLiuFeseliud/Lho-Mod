package fengliu.lho.util.player;

/**
 * 扩展玩家设置
 */
public interface IExtendPlayer {
    /**
     * 玩家删除血量上限
     * @return 删除血量上限
     */
    float getDeleteHealth();

    /**
     * 设置玩家删除血量上限
     * @param health 删除血量上限
     * @return 成功 true
     */
    boolean setDeleteHealth(float health);

    /**
     * 增加玩家删除血量上限
     * @param health 增加的删除血量上限
     * @return 成功 true
     */
    boolean addDeleteHealth(float health);

    /**
     * 减少玩家删除血量上限
     * @param health 减少的删除血量上限
     * @return 成功 true
     */
    boolean subtractDeleteHealth(float health);

    /**
     * 玩家增加血量上限
     * @return 增加血量上限
     */
    float getAddHealth();

    /**
     * 设置玩家增加血量上限
     * @param health 增加血量上限
     * @return 成功 true
     */
    boolean setAddHealth(float health);

    /**
     * 增加玩家增加血量上限
     * @param health 增加血量上限
     * @return 成功 true
     */
    boolean addAddHealth(float health);

    /**
     * 减少玩家增加血量上限
     * @param health 减少的玩家增加血量上限
     * @return 成功 true
     */
    boolean subtractAddHealth(float health);
}
