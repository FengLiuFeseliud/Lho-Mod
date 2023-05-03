package fengliu.notheme.criterion;

import fengliu.notheme.util.IdUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ItemCriterion;
import net.minecraft.advancement.criterion.OnKilledCriterion;

public class ModCriteria {
    public static ItemCriterion MOD_ITEM_USE;
    public static OnKilledCriterion DRAW_BLOOD_KILLER;
    public static AddHeartCriterion ADD_HEART;
    public static void registerAllCriteria(){
        MOD_ITEM_USE = Criteria.register(new ItemCriterion(IdUtil.get("mod_item_use")));
        DRAW_BLOOD_KILLER = Criteria.register(new OnKilledCriterion(IdUtil.get("draw_blood_killer")));
        ADD_HEART = Criteria.register(new AddHeartCriterion(IdUtil.get("add_heart")));
    }
}
