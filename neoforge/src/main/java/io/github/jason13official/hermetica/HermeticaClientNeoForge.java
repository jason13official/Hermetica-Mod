package io.github.jason13official.hermetica;

import io.github.jason13official.hermetica.impl.client.gui.HermeticaHUD;
import io.github.jason13official.hermetica.impl.common.network.packet.MagicChunkS2CPacket;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import java.util.function.Consumer;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.Nullable;

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

    modEventBus.addListener((Consumer<RegisterColorHandlersEvent.Block>) event -> {

      event.register(new BlockColor() {
                       @Override
                       public int getColor(BlockState blockState, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int i) {
                         return level != null && pos != null ? BiomeColors.getAverageWaterColor(level, pos) : -1;
                       }
                     },
          ModBlocks.ACIDIC_CAULDRON);
    });
  }
}
