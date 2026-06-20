package io.github.jason13official.hermetica;

import io.github.jason13official.hermetica.impl.client.gui.HermeticaHUD;
import io.github.jason13official.hermetica.impl.common.network.packet.MagicChunkS2CPacket;
import java.util.function.Consumer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class HermeticaClientNeoForge {

  public HermeticaClientNeoForge(final IEventBus modEventBus) {

    HermeticaClient.preInit();

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {

      HermeticaClient.postInit();
    });

    HermeticaClient.c2s = PacketDistributor::sendToServer;

    NeoForge.EVENT_BUS.addListener((Consumer<RenderGuiEvent.Pre>) event -> {
      HermeticaHUD.renderOverlay(event.getGuiGraphics(), event.getPartialTick());
    });
  }
}
