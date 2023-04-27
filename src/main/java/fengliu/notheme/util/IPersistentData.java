package fengliu.notheme.util;

import net.minecraft.nbt.NbtCompound;

/**
 * 扩展 nbt
 */
public interface IPersistentData {
    /**
     * 扩展 nbt
     * @return 扩展 nbt
     */
    NbtCompound getPersistentData();

    /**
     * 写入扩展 nbt
     * @param nbt 扩展 nbt
     */
    void writePersistentData(NbtCompound nbt);

    /**
     * 清除扩展 nbt
     */
    void clearPersistentData();

    /**
     * 向客户端同步扩展 nbt
     */
    void syncData();
}
