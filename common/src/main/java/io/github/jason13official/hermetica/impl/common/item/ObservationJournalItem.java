package io.github.jason13official.hermetica.impl.common.item;

import io.github.jason13official.hermetica.impl.common.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ObservationJournalItem extends Item {

  public ObservationJournalItem(Properties properties) {
    super(properties);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {


    if (!(entity instanceof Player player)) {
      return;
    }

    // is in player inventory
    // we need a better system for saving this progress
    // on the player instead of as tags that get re-applied
    // every session

    if (!player.getTags().contains("HermeticaFirstJournal")) {
      player.addTag("HermeticaFirstJournal");
    }

    boolean glintOverride = stack.hasFoil(); //stack.has(DataComponents.ENCHANTMENT_GLINT_OVERRIDE) && stack.get(DataComponents.ENCHANTMENT_GLINT_OVERRIDE;
    boolean hasObservedNode = player.getTags().contains("ObservedAuraNode");
    if (glintOverride && !hasObservedNode) {
      player.addTag("ObservedAuraNode");
    } else if (hasObservedNode && !glintOverride) {
      stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
      if (player.getInventory().getItem(slotId).is(ModItems.OBSERVATION_JOURNAL)) {
        player.getInventory().setItem(slotId, stack);
      }
    }
  }
}
