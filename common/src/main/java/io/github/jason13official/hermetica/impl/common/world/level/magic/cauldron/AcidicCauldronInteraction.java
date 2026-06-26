package io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron;

import io.github.jason13official.hermetica.Constants;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;

public class AcidicCauldronInteraction {

  public static InteractionMap ACID = CauldronInteraction.newInteractionMap(Constants.MOD_ID + "_acid");

  public static CauldronInteraction FILL_WATER_TO_ACIDIFY = (state, level, pos, player, hand, stack) -> {
    return CauldronInteraction.emptyBucket(level, pos, player, hand, stack,
        ModBlocks.ACIDIC_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
        SoundEvents.BUCKET_EMPTY);
  };
}
