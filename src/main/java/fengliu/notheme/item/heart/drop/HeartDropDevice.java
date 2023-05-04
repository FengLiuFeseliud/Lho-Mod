package fengliu.notheme.item.heart.drop;

import fengliu.notheme.entity.damage.HeartDropEntity;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.ProbabilityRandom;
import fengliu.notheme.util.item.BaseItem;
import fengliu.notheme.util.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class HeartDropDevice extends BaseItem {
    protected final ProbabilityRandom.RandomItem[] randomItems;

    public HeartDropDevice(Settings settings, String name, int gain) {
        super(settings, name);
        this.randomItems = new ProbabilityRandom.RandomItem[]{
            new ProbabilityRandom.RandomItem(5 * gain, ModItems.HEART_DROP),
            new ProbabilityRandom.RandomItem(10 * gain, ModItems.HEART_HALF_DROP),
            new ProbabilityRandom.RandomItem(100 - (5 * gain) - (10 * gain), (Item) null),
        };
    }

    public void dropHeart(Vec3d pos, PlayerEntity player, ItemStack stack){
        World world = player.getWorld();
        if (world.isClient()){
            return;
        }

       Item heart = ProbabilityRandom.random(randomItems).getItem();
       if (heart == null){
           return;
       }

        stack.damage(1, Random.create(), (ServerPlayerEntity) player);
        world.spawnEntity(new HeartDropEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(heart, 1)));
    }

    public enum HeartDropDeviceLevels implements ILevelItem {
        Lv1(1, 1),
        Lv2(2, 2),
        Lv3(3, 3);

        private final int level;
        private final int gain;

        HeartDropDeviceLevels(int level, int gain){
            this.level = level;
            this.gain = gain;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return HeartDropDeviceLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getIdName() {
            return "heart_drop_device";
        }

        @Override
        public Item getItem() {
            return new HeartDropDevice(new FabricItemSettings().maxCount(1).maxDamageIfAbsent(60 * this.getGain()), this.getPath(), this.getGain());
        }
    }
}
