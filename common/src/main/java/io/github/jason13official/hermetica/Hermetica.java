package io.github.jason13official.hermetica;

import net.minecraft.resources.ResourceLocation;

public class Hermetica {

  public static void init() {
  }

  public static ResourceLocation identifier(final String path) {
    return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}