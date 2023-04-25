package fengliu.lho;

import fengliu.lho.networking.packets.server.ModServerMessage;
import fengliu.lho.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;

public class LhoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.registerAllScreen();

        ModServerMessage.registerS2CPackets();
    }
}
