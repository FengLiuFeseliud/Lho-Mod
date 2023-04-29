package fengliu.notheme.item;

import fengliu.notheme.item.armor.HeartArmor;
import fengliu.notheme.item.heart.*;
import fengliu.notheme.item.heart.drop.HeartAbsorptionDevice;
import fengliu.notheme.item.heart.drop.HeartDrop;
import fengliu.notheme.item.heart.drop.HeartDropDevice;
import fengliu.notheme.item.heart.drop.HeartHalfDrop;
import fengliu.notheme.item.heart.fake.*;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.item.BaseItem;
import fengliu.notheme.util.item.armor.BaseArmorItem;
import fengliu.notheme.util.level.ILevelItem;
import fengliu.notheme.util.level.LevelsUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

import java.util.Map;

public class ModItems {
    public static final Map<Item, ILevelItem> EXPAND_PASSENGERS = LevelsUtil.getItems(ExpandPassenger.ExpandPassengerLevels.values());
    public static final BaseItem STABILIZER = new BaseItem(new FabricItemSettings().maxCount(64), "stabilizer");
    public static final BaseItem CUING_AGENT = new BaseItem(new FabricItemSettings().maxCount(64), "curing_agent");
    public static final BaseItem EMPTY_BLOOD_NEEDLE = new EmptyBloodNeedle(new FabricItemSettings().maxCount(1), "empty_blood_needle");
    public static final BaseItem BLOOD_NEEDLE = new BloodNeedle(new FabricItemSettings().maxCount(1), "blood_needle");
    public static final BaseItem GOLD_BLOOD_NEEDLE = new GoldBloodNeedle(new FabricItemSettings().maxCount(1), "gold_blood_needle");
    public static final BaseItem FROST_BLOOD_NEEDLE = new FrostBloodNeedle(new FabricItemSettings().maxCount(1), "frost_blood_needle");
    public static final BaseItem POISON_BLOOD_NEEDLE = new PoisonBloodNeedle(new FabricItemSettings().maxCount(1), "poison_blood_needle");
    public static final BaseItem WITHER_BLOOD_NEEDLE = new WitherBloodNeedle(new FabricItemSettings().maxCount(1), "wither_blood_needle");
    public static final BaseItem WATER_BLOOD_NEEDLE = new WaterBloodNeedle(new FabricItemSettings().recipeRemainder(EMPTY_BLOOD_NEEDLE).maxCount(1), "water_blood_needle");
    public static final BaseItem EMPTY_HEART = new EmptyHeart(new FabricItemSettings().maxCount(1), "empty_heart");
    public static final BaseItem HEART = new Heart(new FabricItemSettings().maxCount(1), "heart");
    public static final BaseItem FAKE_HEART = new FakeHeart(new FabricItemSettings().maxCount(1), HEART);
    public static final BaseItem GOLD_HEART = new GoldHeart(new FabricItemSettings().maxCount(1), "gold_heart");
    public static final BaseItem FAKE_GOLD_HEART = new FakeHeart(new FabricItemSettings().maxCount(1), GOLD_HEART);
    public static final BaseItem FROST_HEART = new FrostHeart(new FabricItemSettings().maxCount(1), "frost_heart");
    public static final BaseItem FAKE_FROST_HEART = new FakeHeart(new FabricItemSettings().maxCount(1), FROST_HEART);
    public static final BaseItem POISON_HEART = new PoisonHeart(new FabricItemSettings().maxCount(1), "poison_heart");
    public static final BaseItem FAKE_POISON_HEART = new FakeHeart(new FabricItemSettings().maxCount(1), POISON_HEART);
    public static final BaseItem WITHER_HEART = new WitherHeart(new FabricItemSettings().maxCount(1), "wither_heart");
    public static final BaseItem FAKE_WITHER_HEART = new FakeHeart(new FabricItemSettings().maxCount(1), WITHER_HEART);
    public static final BaseItem ANIMAL_HEART = new AnimalHeart(new FabricItemSettings().maxCount(1), "animal_heart");
    public static final BaseItem FAKE_ANIMAL_HEART = new FakeHeart(new FabricItemSettings().maxCount(1), ANIMAL_HEART);
    public static final BaseItem HEART_DROP = new HeartDrop(new FabricItemSettings().maxCount(64), "heart_drop");
    public static final BaseItem HEART_HALF_DROP = new HeartHalfDrop(new FabricItemSettings().maxCount(64), "heart_half_drop");
    public static final Map<Item, ILevelItem> HEART_DROP_DEVICE = LevelsUtil.getItems(HeartDropDevice.HeartDropDeviceLevels.values());
    public static final Map<Item, ILevelItem> HEART_ABSORPTION_DEVICE = LevelsUtil.getItems(HeartAbsorptionDevice.HeartAbsorptionDeviceLevels.values());

    public static final BaseItem[] BODY_GROUP_ITEMS = new BaseItem[]{
        STABILIZER,
        CUING_AGENT,
        EMPTY_BLOOD_NEEDLE,
        BLOOD_NEEDLE,
        GOLD_BLOOD_NEEDLE,
        WATER_BLOOD_NEEDLE,
        FROST_BLOOD_NEEDLE,
        POISON_BLOOD_NEEDLE,
        WITHER_BLOOD_NEEDLE,
        EMPTY_HEART,
        HEART,
        FAKE_HEART,
        GOLD_HEART,
        FAKE_GOLD_HEART,
        FROST_HEART,
        FAKE_FROST_HEART,
        POISON_HEART,
        FAKE_POISON_HEART,
        WITHER_HEART,
        FAKE_WITHER_HEART,
        ANIMAL_HEART,
        FAKE_ANIMAL_HEART,
        HEART_DROP,
        HEART_HALF_DROP
    };

    public static final BaseArmorItem HEART_HELMET = new HeartArmor(ArmorItem.Type.HELMET, new FabricItemSettings());
    public static final BaseArmorItem HEART_CHESTPLATE = new HeartArmor(ArmorItem.Type.CHESTPLATE, new FabricItemSettings());
    public static final BaseArmorItem HEART_LEGGINGS = new HeartArmor(ArmorItem.Type.LEGGINGS, new FabricItemSettings());
    public static final BaseArmorItem HEART_BOOTS = new HeartArmor(ArmorItem.Type.BOOTS, new FabricItemSettings());

    public static final BaseArmorItem[] BODY_GROUP_ARMOR_ITEMS = new BaseArmorItem[]{
        HEART_HELMET,
        HEART_CHESTPLATE,
        HEART_LEGGINGS,
        HEART_BOOTS
    };

//    public static final BaseItem PHONE = new Phone(new FabricItemSettings().maxCount(1), "phone");
//
//    public static final BaseItem[] INTERNET_ITEMS = new BaseItem[]{
//        PHONE
//    };

    public static void registerAllItem(){
        LevelsUtil.registerAllItem(EXPAND_PASSENGERS);

        RegisterUtil.registerItems(BODY_GROUP_ITEMS);
        LevelsUtil.registerAllItem(HEART_DROP_DEVICE);
        LevelsUtil.registerAllItem(HEART_ABSORPTION_DEVICE);

        RegisterUtil.registerArmorItems(BODY_GROUP_ARMOR_ITEMS);
    }
}
