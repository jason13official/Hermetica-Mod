package io.github.jason13official.hermetica;

import io.github.jason13official.hermetica.fabric.HermeticaDataAttachments;
import io.github.jason13official.hermetica.impl.common.network.packet.MagicChunkS2CPacket;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import io.github.jason13official.hermetica.impl.common.registry.ModEntities;
import io.github.jason13official.hermetica.impl.common.registry.ModItems;
import io.github.jason13official.hermetica.impl.common.registry.ModMenus;
import io.github.jason13official.hermetica.impl.common.registry.ModParticles;
import io.github.jason13official.hermetica.impl.common.registry.ModTabs;
import io.github.jason13official.hermetica.impl.common.registry.ModTiles;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicLevelEvents;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicLevelHandler;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public class HermeticaFabric implements ModInitializer {

  @Override
  public void onInitialize() {

    Hermetica.preInit();

    Hermetica.s2c = ServerPlayNetworking::send;
    PayloadTypeRegistry.playS2C().register(MagicChunkS2CPacket.TYPE, MagicChunkS2CPacket.STREAM_CODEC);

    bind(BuiltInRegistries.BLOCK, ModBlocks::register);
    bind(BuiltInRegistries.ENTITY_TYPE, ModEntities::register);
    bind(BuiltInRegistries.ITEM, ModItems::register);
    bind(BuiltInRegistries.PARTICLE_TYPE, ModParticles::register);
    bind(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(BuiltInRegistries.MENU, ModMenus::register);
    bind(BuiltInRegistries.CREATIVE_MODE_TAB, ModTabs::register);
    HermeticaDataAttachments.register();

    Hermetica.postInit();

    ServerWorldEvents.LOAD.register((server, serverLevel) -> MagicLevelEvents.onServerLevelLoad(serverLevel));
    ServerWorldEvents.UNLOAD.register((server, serverLevel) -> MagicLevelEvents.onServerLevelUnload(serverLevel));
    ServerChunkEvents.CHUNK_LOAD.register((serverLevel, chunk) -> MagicLevelEvents.onServerChunkLoad(serverLevel, chunk));
    ServerChunkEvents.CHUNK_UNLOAD.register((serverLevel, chunk) -> MagicLevelEvents.onServerChunkUnload(serverLevel, chunk.getPos()));
    ServerTickEvents.START_WORLD_TICK.register(serverLevel -> MagicLevelEvents.onServerLevelTickStart(serverLevel));

    ServerTickEvents.START_SERVER_TICK.register(server -> {

      if (server.overworld().getGameTime() % 20 != 0) {
        return;
      }

      server.getPlayerList().getPlayers().forEach(player -> {
        var p = new MagicChunkS2CPacket(MagicLevelHandler.getMagicChunkdata(player.level().dimension(), player.blockPosition()));
        Hermetica.s2c.accept(player, p);
      });
    });

    ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new ResourceReloadListener());
  }

  public <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {

    source.accept((t, rl) -> Registry.register(registry, rl, t));
  }

  public static class ResourceReloadListener implements SimpleSynchronousResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
      return Hermetica.identifier(Constants.MOD_ID);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
      // ModConfig.load(Services.PLATFORM.getConfigDirectory());
    }
  }
}
