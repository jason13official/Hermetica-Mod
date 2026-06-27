package io.github.jason13official.hermetica.impl.common.world.level.magic.aspect;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/// @param id the namespaced identifier of the aspect (so other mods can define their own)
/// @param translationKey the translation key, for getting the display name of the aspect
/// @param components component aspects for non-primal aspects
public record Aspect(ResourceLocation id, String translationKey, Aspect... components) {

  @Override
  public @NotNull String toString() {
    return id.toString();
  }
}
