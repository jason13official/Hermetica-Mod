package io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron;

import io.github.jason13official.hermetica.Constants;
import io.github.jason13official.hermetica.impl.common.registry.ModBlockStateProperties;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import io.github.jason13official.hermetica.impl.common.world.level.magic.aspect.Aspect;
import io.github.jason13official.hermetica.impl.common.world.level.magic.aspect.Aspects;
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

  public static final Map<Item, Aspect> ASPECT_BY_ITEM = new HashMap<>();

  public static InteractionMap ACID = CauldronInteraction.newInteractionMap(Constants.MOD_ID + "_acid");

  public static CauldronInteraction FILL_WATER_TO_ACIDIFY = (state, level, pos, player, hand, stack) -> {
    return CauldronInteraction.emptyBucket(level, pos, player, hand, stack,
        ModBlocks.ACIDIC_CAULDRON.defaultBlockState().setValue(ModBlockStateProperties.CAULDRON_ACID_LEVEL, 3),
        SoundEvents.BUCKET_EMPTY);
  };

  public static void registerDistillations() {

    ASPECT_BY_ITEM.put(Items.DIAMOND, Aspects.ORDER);
    ASPECT_BY_ITEM.put(Items.FIRE_CHARGE, Aspects.FIRE);
    ASPECT_BY_ITEM.put(Items.HEART_OF_THE_SEA, Aspects.WATER);
    ASPECT_BY_ITEM.put(Items.WIND_CHARGE, Aspects.AIR);
    ASPECT_BY_ITEM.put(Items.DIRT, Aspects.EARTH);
    ASPECT_BY_ITEM.put(Items.END_CRYSTAL, Aspects.ENTROPY);
  }
}
