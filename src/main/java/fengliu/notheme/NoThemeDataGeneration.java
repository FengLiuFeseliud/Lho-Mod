package fengliu.notheme;

import fengliu.notheme.data.generation.ItemDataGeneration;
import fengliu.notheme.data.generation.LangGeneration;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NoThemeDataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemDataGeneration::new);
        pack.addProvider(LangGeneration::new);
    }

}
