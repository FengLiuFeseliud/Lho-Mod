package fengliu.notheme.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent BENTO_BOX = new FoodComponent.Builder()
        .hunger(4)
        .saturationModifier(0.6f)
        .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 2), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 2), 1.0f)
        .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 300, 2), 1.0f)
        .build();
}
