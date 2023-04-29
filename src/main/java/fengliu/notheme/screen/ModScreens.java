package fengliu.notheme.screen;

import fengliu.notheme.screen.handler.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreens {

    public static void registerAllScreen(){
        HandledScreens.register(ModScreenHandlers.CUSTOMERS_BLOCK_SCREEN_HANDLER, CustomersBlockScreen::new);
        HandledScreens.register(ModScreenHandlers.PHONE_SCREEN_HANDLER, PhoneScreen::new);
        HandledScreens.register(ModScreenHandlers.BLOOP_POOL_BLOCK_SCREEN_HANDLER, BloodPoolBlockScreen::new);
    }
}
