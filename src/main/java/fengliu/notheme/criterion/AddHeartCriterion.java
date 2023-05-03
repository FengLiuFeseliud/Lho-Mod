package fengliu.notheme.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AddHeartCriterion extends AbstractCriterion<AddHeartCriterion.Conditions> {
    final Identifier id;

    public AddHeartCriterion(Identifier id){
        this.id = id;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(this.id, playerPredicate, obj.get("heart").getAsInt());
    }

    public void trigger(ServerPlayerEntity player, int heart) {
        super.trigger(player, (conditions -> conditions.test(heart)));
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public static class Conditions extends AbstractCriterionConditions{
        private final int addHeart;

        public Conditions(Identifier id, EntityPredicate.Extended entity, int addHeart) {
            super(id, entity);
            this.addHeart = addHeart;
        }

        public static Conditions create(Identifier id, EntityPredicate.Extended entity, int addHeart) {
            return new Conditions(id, entity, addHeart);
        }

        public boolean test(int heart) {
            return heart >= this.addHeart;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.addProperty("heart", this.addHeart);
            return jsonObject;
        }
    }
}
