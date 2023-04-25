package fengliu.lho.screen.handler;

import fengliu.lho.util.IdUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<CustomersBlockScreenHandler> CUSTOMERS_BLOCK_SCREEN_HANDLER;

    public static void registerAllScreenHandlers(){
        CUSTOMERS_BLOCK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(IdUtil.get("customers_block"), CustomersBlockScreenHandler::new);
    }
}