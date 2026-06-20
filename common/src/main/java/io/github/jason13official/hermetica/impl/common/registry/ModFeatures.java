package io.github.jason13official.hermetica.impl.common.registry;

import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.impl.common.world.level.levelgen.feature.AuraNodeFeature;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModFeatures {

  public static final ResourceKey<PlacedFeature> NODE_FEATURE_KEY = ResourceKey.create(Registries.PLACED_FEATURE, Hermetica.identifier("aura_node_feature"));
  public static Feature<NoneFeatureConfiguration> NODE;

  public static void register(BiConsumer<Feature<?>, ResourceLocation> consumer) {

    NODE = new AuraNodeFeature(NoneFeatureConfiguration.CODEC);

    consumer.accept(NODE, Hermetica.identifier("aura_node_feature"));
  }
}
