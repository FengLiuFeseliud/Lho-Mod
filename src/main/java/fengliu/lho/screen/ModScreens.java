package fengliu.lho.screen;

import fengliu.lho.screen.handler.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreens {

    public static void registerAllScreen(){
        HandledScreens.register(ModScreenHandlers.CUSTOMERS_BLOCK_SCREEN_HANDLER, CustomersBlockScreen::new);
    }
}
