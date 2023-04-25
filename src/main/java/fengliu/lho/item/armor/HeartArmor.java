package fengliu.lho.item.armor;

import fengliu.lho.item.armor.material.HeartArmorMaterial;
import fengliu.lho.util.item.armor.BaseArmorItem;
import fengliu.lho.util.item.armor.material.ExtendArmorMaterial;

public class HeartArmor extends BaseArmorItem {
    public static final ExtendArmorMaterial MATERIAL = new HeartArmorMaterial("heart");
    public HeartArmor(Type type, Settings settings) {
        super(MATERIAL, type, settings);
    }
}
