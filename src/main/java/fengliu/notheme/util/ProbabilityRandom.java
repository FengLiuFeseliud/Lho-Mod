package fengliu.notheme.util;

import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 实现一个简单的概率随机
 */
public class ProbabilityRandom {

    /**
     * 进行随机
     * @param randomItem 随机项目列表
     * @return 随机到的项目
     */
    public static RandomItem random(RandomItem[] randomItem){
        List<Double> all_probability = allItemProbability(randomItem);
        double nextDouble = Math.random();

        all_probability.add(nextDouble);
        Collections.sort(all_probability);
        return randomItem[all_probability.indexOf(nextDouble)];
    }

    /**
     * 获取所有项目概率
     * @param randomItem 随机项目列表
     * @return 所有项目概率
     */
    public static List<Double> allItemProbability(RandomItem[] randomItem){
        List<Double> all_probability = new ArrayList<>();

        double sumRate = 0d;
        for(RandomItem item : randomItem){
            sumRate += item.getProbability();
        }

        double tempSumRate = 0d;
        for(RandomItem item : randomItem){
            tempSumRate += item.getProbability();
            all_probability.add(tempSumRate / sumRate);
        }

        return all_probability;
    }

    /**
     * 随机项目
     */
    public static class RandomItem {
        private final double probability;
        private Item item;
        private RandomItem[] items;

        /**
         * 随机项目
         * @param probability 概率
         * @param Item 项目内容
         */
        public RandomItem(double probability, @Nullable Item Item){
            this.probability = Math.round(probability * 100) * 0.01;
            this.item = Item;
        }

        public RandomItem(double probability, RandomItem[] Items){
            this.probability = Math.round(probability * 100) * 0.01;
            this.items = Items;
        }

        public double getProbability(){
            return probability;
        }

        /**
         * 获取项目内容, 如果项目内容为随机项目列表继续随机项目内容
         * @return 项目内容
         */
        public Item getItem(){
            if(this.item != null){
                return item;
            }

            if (this.items == null) {
                return null;
            }

            return random(this.items).getItem();
        }
    }
}