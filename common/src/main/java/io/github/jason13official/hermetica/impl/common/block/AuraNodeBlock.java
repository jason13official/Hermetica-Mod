package io.github.jason13official.hermetica.impl.common.block;

import io.github.jason13official.hermetica.Constants;
import io.github.jason13official.hermetica.impl.common.registry.ModBlocks.VanillaTransparentBlock;
import io.github.jason13official.hermetica.impl.common.registry.ModItems;
import io.github.jason13official.hermetica.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AuraNodeBlock extends VanillaTransparentBlock {

  public AuraNodeBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

//    if (Services.PLATFORM.isDevelopmentEnvironment()) {
//
//      System.out.println();
//      Constants.LOG.info("{} interacting with an Aura Node with an item in hand, {} {}", player.getScoreboardName(), Component.translatable(stack.getDescriptionId()).getString(), hand.name());
//      Constants.LOG.info("Client-side? {}, Server-side? {}", level.isClientSide(), !level.isClientSide());
//      Constants.LOG.info("Interaction Position and Dimension? {} in {}", pos.toShortString(), level.dimension().location().toString());
//      System.out.println();
//    }

    if (stack.is(Items.WRITABLE_BOOK) && !player.getTags().contains("HermeticaFirstJournal")) {
      player.setItemInHand(hand, new ItemStack(ModItems.OBSERVATION_JOURNAL));
      player.addTag("HermeticaFirstJournal");
      return ItemInteractionResult.CONSUME;
    } else if (stack.is(ModItems.OBSERVATION_JOURNAL) && !player.getTags().contains("ObservedAuraNode")) {

      if (Services.PLATFORM.isDevelopmentEnvironment()) {
        player.sendSystemMessage(Component.translatable("lore.hermetica.aura_node_observation"));
        Constants.LOG.info("{} observed an aura node for the first time.", player.getScoreboardName());
      }

      stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
      player.addTag("ObservedAuraNode");
      return ItemInteractionResult.SUCCESS;
    }

    return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
  }
}
