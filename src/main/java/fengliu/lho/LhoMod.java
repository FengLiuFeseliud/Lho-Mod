package fengliu.lho;

import fengliu.lho.block.ModBlocks;
import fengliu.lho.block.entity.ModBlockEntitys;
import fengliu.lho.callback.ArmorChangeCallback;
import fengliu.lho.callback.IArmorChangeCallback;
import fengliu.lho.item.ModItemGroups;
import fengliu.lho.item.ModItems;
import fengliu.lho.item.block.ModBlockItems;
import fengliu.lho.screen.handler.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LhoMod implements ModInitializer {
	public static final String MOD_ID = "lho";
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
