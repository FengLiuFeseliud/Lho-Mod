package fengliu.notheme.item.armor.material;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.item.armor.material.ExtendArmorMaterial;
import net.minecraft.item.ArmorItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class HeartArmorMaterial extends ExtendArmorMaterial {

    public HeartArmorMaterial(String name) {
        super(name);
    }

    @Override
    public float addHealth(ArmorItem.Type type) {
        switch (type){
            case HELMET -> {
                return 4;
            }

            case CHESTPLATE -> {
                return 8;
            }

            case LEGGINGS -> {
                return 6;
            }

            case BOOTS -> {
                return 2;
            }

            default -> {
                return 0;
            }
        }
    }

    @Override
    public float deleteHealth(ArmorItem.Type type) {
        return 0;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return 0;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        switch (type){
            case HELMET -> {
                return 2;
            }

            case CHESTPLATE -> {
                return 4;
            }

            case LEGGINGS -> {
                return 3;
            }

            case BOOTS -> {
                return 1;
            }

            default -> {
                return 0;
            }
        }
    }

    @Override
    public int getEnchantability() {
        return 10;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.EMPTY_HEART);
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
