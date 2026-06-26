package io.github.jason13official.hermetica.impl.common.world.level.magic.aspect;

import net.minecraft.resources.ResourceLocation;

public record Aspect(ResourceLocation id, String simpleNameKey, String latinNameKey, Aspect... parents) {
  public boolean isPrimal() { return parents.length == 0; }
  public boolean isCompound() { return parents.length > 0; }
}

//public record Aspect(ResourceLocation id, String simpleNameKey, String latinNameKey) {
//}
