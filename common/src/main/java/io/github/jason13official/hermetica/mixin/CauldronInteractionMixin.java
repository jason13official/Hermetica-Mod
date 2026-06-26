package io.github.jason13official.hermetica.mixin;

import io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron.AcidicCauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO we don't really need a mixin for the bootstrap logic, maybe we could move this but it "shouldn't" break anything
@Mixin(CauldronInteraction.class)
public interface CauldronInteractionMixin {

  @Inject(at = @At("TAIL"), method = "bootStrap")
  private static void hermetica$bootstrap(CallbackInfo ci) {
    AcidicCauldronInteraction.ACID.map().put(Items.WATER_BUCKET, AcidicCauldronInteraction.FILL_WATER_TO_ACIDIFY);
  }
}
