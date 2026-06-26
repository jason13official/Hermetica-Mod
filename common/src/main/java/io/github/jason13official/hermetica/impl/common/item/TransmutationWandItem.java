package io.github.jason13official.hermetica.impl.common.item;

import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DebugStickItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class TransmutationWandItem extends DebugStickItem {

  public TransmutationWandItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {

    Level level = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
    if (level.getBlockState(blockpos).is(Blocks.CAULDRON)) {
      level.setBlockAndUpdate(blockpos, ModBlocks.ACIDIC_CAULDRON.defaultBlockState());
    }

    return super.useOn(context);
  }
}
