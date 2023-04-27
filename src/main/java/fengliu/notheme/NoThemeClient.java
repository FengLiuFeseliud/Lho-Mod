package fengliu.notheme;

import fengliu.notheme.networking.packets.server.ModServerMessage;
import fengliu.notheme.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;

public class NoThemeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.registerAllScreen();

        ModServerMessage.registerS2CPackets();
    }
}
