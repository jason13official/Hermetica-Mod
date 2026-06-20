package io.github.jason13official.hermetica.impl.common.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks;
import io.github.jason13official.hermetica.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class AuraNodeFeature extends Feature<NoneFeatureConfiguration> {

  private final BlockState node;

  public AuraNodeFeature(Codec<NoneFeatureConfiguration> codec) {
    super(codec);
    this.node = ModBlocks.AURA_NODE.defaultBlockState();
  }

  @Override
  public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

    WorldGenLevel level = context.level();
    BlockPos origin = context.origin();

    boolean set = level.setBlock(origin, this.node, Block.UPDATE_CLIENTS);

    if (set && Services.PLATFORM.isDevelopmentEnvironment()) {
      System.out.println("Created new node at " + origin.toShortString());
    }

    AuraNodeFeatureInfo.LOGGER.info("Created new node at " + origin.toShortString());

    return set;
  }
}

