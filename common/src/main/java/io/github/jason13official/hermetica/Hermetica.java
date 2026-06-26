package io.github.jason13official.hermetica;

import io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron.AcidicCauldronInteraction;
import java.util.function.BiConsumer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class Hermetica {

  /// general purpose server-to-client packet acceptor
  public static BiConsumer<ServerPlayer, CustomPacketPayload> s2c;

  /// before game objects are registered
  public static void preInit() {
  }

  /// after game objects are registered
  public static void postInit() {

    AcidicCauldronInteraction.registerDistillations();
  }

  public static ResourceLocation identifier(final String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}