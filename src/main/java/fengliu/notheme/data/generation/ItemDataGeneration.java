package fengliu.notheme.data.generation;

import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.item.BaseItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;

import static net.minecraft.data.client.Models.GENERATED;

public class ItemDataGeneration extends FabricModelProvider {
    public ItemDataGeneration(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        RegisterUtil.ITEM_MODEL.forEach((item, model) -> {
            if (model == null){
                return;
            }

            if (model == RegisterUtil.Model.GENERATED){
                if (item instanceof BaseItem baseItem){
                    GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(IdUtil.get(baseItem.getTextureName()).withPrefixedPath(baseItem.getPrefixedPath())), itemModelGenerator.writer);
                    return;
                }
                itemModelGenerator.register(item, GENERATED);
            }
        });

        RegisterUtil.ITEM_MODEL.clear();
    }
}
