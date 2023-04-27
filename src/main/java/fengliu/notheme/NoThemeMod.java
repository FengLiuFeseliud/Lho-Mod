package fengliu.notheme;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.block.entity.ModBlockEntitys;
import fengliu.notheme.callback.ArmorChangeCallback;
import fengliu.notheme.item.ModItemGroups;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.screen.handler.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoThemeMod implements ModInitializer {
	public static final String MOD_ID = "notheme";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerAllItem();
		ModBlockItems.registerAllBlockItem();
		ModItemGroups.registerItemGroup();

		ModBlocks.registerAllBlock();
		ModBlockEntitys.registerAllBlockEntity();

		ModScreenHandlers.registerAllScreenHandlers();

        ArmorChangeCallback.registerAllCallback();
	}
}
