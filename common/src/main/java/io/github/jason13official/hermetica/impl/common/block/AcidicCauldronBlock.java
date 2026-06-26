package io.github.jason13official.hermetica.impl.common.block;

import com.mojang.serialization.MapCodec;
import io.github.jason13official.hermetica.impl.common.world.level.magic.cauldron.AcidicCauldronInteraction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

/// inept impl
public class AcidicCauldronBlock extends AbstractCauldronBlock {

  public static final MapCodec<AcidicCauldronBlock> CODEC = simpleCodec(AcidicCauldronBlock::new);

  public AcidicCauldronBlock(Properties properties) {
    super(properties, AcidicCauldronInteraction.ACID);

    this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.LEVEL_CAULDRON, 1));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

    builder.add(BlockStateProperties.LEVEL_CAULDRON);
  }

  @Override
  protected MapCodec<? extends AbstractCauldronBlock> codec() {

    return CODEC;
  }

  @Override
  public boolean isFull(BlockState state) {

    return state.getValue(BlockStateProperties.LEVEL_CAULDRON) == 3;
  }

  @Override
  protected double getContentHeight(BlockState state) {

    return ((double) 6.0F + (double) state.getValue(BlockStateProperties.LEVEL_CAULDRON) * (double) 3.0F) / (double) 16.0F;
  }

  @Override
  protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

    process(level, pos, entity);
  }

  private static void process(Level level, BlockPos pos, Entity entity) {
    // server side and entity inside bounding box
    if (!level.isClientSide() && entity instanceof ItemEntity itemEntity) {
      System.out.println("attempting discard");
      itemEntity.discard();
    }
  }
}
