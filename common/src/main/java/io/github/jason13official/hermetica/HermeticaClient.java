package io.github.jason13official.hermetica;

import java.util.function.Consumer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class HermeticaClient {

  /// general purpose client-to-server packet acceptor
  public static Consumer<CustomPacketPayload> c2s;

  /// before game objects are registered
  public static void preInit() {
  }

  /// after game objects are registered
  public static void postInit() {
  }
}