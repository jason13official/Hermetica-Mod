package io.github.jason13official.hermetica;

import io.github.jason13official.hermetica.impl.client.gui.HermeticaHUD;
import io.github.jason13official.hermetica.impl.common.network.packet.MagicChunkS2CPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HermeticaClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    HermeticaClient.preInit();

    HermeticaClient.c2s = ClientPlayNetworking::send;
    ClientPlayNetworking.registerGlobalReceiver(MagicChunkS2CPacket.TYPE, (payload, context) -> {
      MagicChunkS2CPacket.handle(payload);
    });

    HermeticaClient.postInit();

    HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
      HermeticaHUD.renderOverlay(drawContext, tickCounter);
    });
  }
}
