package fengliu.lho.util.item.armor.material;

import fengliu.lho.util.player.IExtendPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;

public interface IExtendMaterial {

    float addHealth(ArmorItem.Type type);
    float deleteHealth(ArmorItem.Type type);
    default void append(IExtendPlayer player, ArmorItem.Type type){
        player.addAddHealth(this.addHealth(type));
        player.addDeleteHealth(this.deleteHealth(type));
    }
    default void remove(IExtendPlayer player, ArmorItem.Type type){
        player.subtractAddHealth(this.addHealth(type));
        player.subtractDeleteHealth(this.deleteHealth(type));
    }
}
