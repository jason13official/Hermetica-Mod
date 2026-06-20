package io.github.jason13official.hermetica;

import io.github.jason13official.hermetica.impl.common.network.packet.MagicChunkS2CPacket;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import io.github.jason13official.hermetica.impl.common.registry.ModEntities;
import io.github.jason13official.hermetica.impl.common.registry.ModFeatures;
import io.github.jason13official.hermetica.impl.common.registry.ModItems;
import io.github.jason13official.hermetica.impl.common.registry.ModMenus;
import io.github.jason13official.hermetica.impl.common.registry.ModParticles;
import io.github.jason13official.hermetica.impl.common.registry.ModTabs;
import io.github.jason13official.hermetica.impl.common.registry.ModTiles;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicLevelEvents;
import io.github.jason13official.hermetica.impl.common.world.level.magic.ambient.MagicLevelHandler;
import io.github.jason13official.hermetica.neoforge.HermeticaDataAttachments;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class HermeticaNeoForge {

  public static IEventBus EVENT_BUS;

  public HermeticaNeoForge(final IEventBus modEventBus) {

    EVENT_BUS = modEventBus;

    Hermetica.preInit();

    bind(Registries.BLOCK, ModBlocks::register);
    bind(Registries.ENTITY_TYPE, ModEntities::register);
    bind(Registries.ITEM, ModItems::register);
    bind(Registries.PARTICLE_TYPE, ModParticles::register);
    bind(Registries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(Registries.MENU, ModMenus::register);
    bind(Registries.FEATURE, ModFeatures::register);
    bind(Registries.CREATIVE_MODE_TAB, ModTabs::register);
    bind(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, HermeticaDataAttachments::register);

    EVENT_BUS.addListener((Consumer<FMLCommonSetupEvent>) event -> Hermetica.postInit());

    Hermetica.s2c = PacketDistributor::sendToPlayer;
    modEventBus.addListener((Consumer<RegisterPayloadHandlersEvent>) event -> {
      PayloadRegistrar registrar = event.registrar("1");
      registrar.playToClient(MagicChunkS2CPacket.TYPE, MagicChunkS2CPacket.STREAM_CODEC, (payload, context) -> {
        MagicChunkS2CPacket.handle(payload);
      });
    });

    NeoForge.EVENT_BUS.addListener((Consumer<LevelEvent.Load>) event -> {
      if (event.getLevel() instanceof ServerLevel serverLevel) {
        MagicLevelEvents.onServerLevelLoad(serverLevel);
      }
    });

    NeoForge.EVENT_BUS.addListener((Consumer<LevelEvent.Unload>) event -> {
      if (event.getLevel() instanceof ServerLevel serverLevel) {
        MagicLevelEvents.onServerLevelUnload(serverLevel);
      }
    });

    NeoForge.EVENT_BUS.addListener((Consumer<ChunkEvent.Load>) event -> {
      if (!(event.getLevel() instanceof ServerLevel serverLevel)
          || !(event.getChunk() instanceof LevelChunk levelChunk)) {
        return;
      }
      MagicLevelEvents.onServerChunkLoad(serverLevel, levelChunk);
    });

    NeoForge.EVENT_BUS.addListener((Consumer<ChunkEvent.Unload>) event -> {
      if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
        return;
      }
      MagicLevelEvents.onServerChunkUnload(serverLevel, event.getChunk().getPos());
    });

    NeoForge.EVENT_BUS.addListener((Consumer<LevelTickEvent.Pre>) event -> {
      if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
        return;
      }
      MagicLevelEvents.onServerLevelTickStart(serverLevel);
    });

    NeoForge.EVENT_BUS.addListener((Consumer<ServerTickEvent.Pre>) event -> {

      MinecraftServer server = event.getServer();

      if (server.overworld().getGameTime() % 20 != 0) {
        return;
      }

      server.getPlayerList().getPlayers().forEach(player -> {
        var p = new MagicChunkS2CPacket(MagicLevelHandler.getMagicChunkdata(player.level().dimension(), player.blockPosition()));
        Hermetica.s2c.accept(player, p);
      });
    });

    NeoForge.EVENT_BUS.addListener((Consumer<AddReloadListenerEvent>) event -> {
      event.addListener(new ResourceReloadListener());
    });

    if (FMLLoader.getDist() == Dist.CLIENT) {
      new HermeticaClientNeoForge(EVENT_BUS);
    }
  }

  public <T> void bind(ResourceKey<Registry<T>> registryKey, Consumer<BiConsumer<T, ResourceLocation>> source) {

    EVENT_BUS.addListener((Consumer<RegisterEvent>) event -> {
      if (registryKey.equals(event.getRegistryKey())) {
        source.accept((t, rl) -> event.register(registryKey, rl, () -> t));
      }
    });
  }

  public static class ResourceReloadListener extends SimplePreparableReloadListener<Void> {

    @Override
    public String getName() {
      return Hermetica.identifier(Constants.MOD_ID).toString();
    }

    @Override
    protected void apply(Void unused, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      // ModConfig.load(Services.PLATFORM.getConfigDirectory());
    }

    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      return null;
    }
  }
}