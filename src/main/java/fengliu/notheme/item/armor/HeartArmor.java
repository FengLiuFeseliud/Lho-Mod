package fengliu.notheme.item.armor;

import fengliu.notheme.item.armor.material.HeartArmorMaterial;
import fengliu.notheme.util.item.armor.BaseArmorItem;
import fengliu.notheme.util.item.armor.material.ExtendArmorMaterial;

public class HeartArmor extends BaseArmorItem {
    public static final ExtendArmorMaterial MATERIAL = new HeartArmorMaterial("heart");
    public HeartArmor(Type type, Settings settings) {
        super(MATERIAL, type, settings);
    }
}
