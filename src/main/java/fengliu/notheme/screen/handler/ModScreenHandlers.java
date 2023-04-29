package fengliu.notheme.screen.handler;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.block.IBaseBlock;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<CustomersBlockScreenHandler> CUSTOMERS_BLOCK_SCREEN_HANDLER;
    public static ScreenHandlerType<PhoneScreenHandler> PHONE_SCREEN_HANDLER;
    public static ScreenHandlerType<BloodPoolBlockScreenHandler> BLOOP_POOL_BLOCK_SCREEN_HANDLER;

    public static void registerAllScreenHandlers(){
        CUSTOMERS_BLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(IdUtil.get("customers_block"), CustomersBlockScreenHandler::new);
        PHONE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(IdUtil.get("phone"), PhoneScreenHandler::new);

        BLOOP_POOL_BLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(((IBaseBlock) ModBlocks.BLOOD_POOL_BLOCK).getId(), BloodPoolBlockScreenHandler::new);
    }
}