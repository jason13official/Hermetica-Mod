package io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron;

import io.github.jason13official.hermetica.Constants;
import io.github.jason13official.hermetica.impl.common.registry.ModBlockStateProperties;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class AcidicCauldronInteraction {

  public static final Map<Item, Block> BLOCK_BY_ITEM = new HashMap<>();

  public static InteractionMap ACID = CauldronInteraction.newInteractionMap(Constants.MOD_ID + "_acid");

  public static CauldronInteraction FILL_WATER_TO_ACIDIFY = (state, level, pos, player, hand, stack) -> {
    return CauldronInteraction.emptyBucket(level, pos, player, hand, stack,
        ModBlocks.ACIDIC_CAULDRON.defaultBlockState().setValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL, 3),
        SoundEvents.BUCKET_EMPTY);
  };

  public static void registerDistillations() {

    BLOCK_BY_ITEM.put(Items.DIAMOND, Blocks.DIAMOND_ORE);
  }
}
