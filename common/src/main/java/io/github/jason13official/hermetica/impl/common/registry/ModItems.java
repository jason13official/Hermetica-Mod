package io.github.jason13official.hermetica.impl.common.registry;

import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.impl.common.item.ObservationJournalItem;
import io.github.jason13official.hermetica.impl.common.item.TransmutationWandItem;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ModItems {

  public static final ArrayList<Item> REGISTERED_ITEMS = new ArrayList<>();

  public static Item OBSERVATION_JOURNAL;
  public static Item TRANSMUTATION_WAND;
  public static Item ACIDIC_CAULDRON;

  public static Item TOWER_HEART;
  public static Item VESTIGIAL_TOWER_HEART;

  public static Item AURA_NODE;

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {

    registerBlockItems(consumer);
  }

  private static void registerBlockItems(BiConsumer<Item, ResourceLocation> consumer) {

    OBSERVATION_JOURNAL = construct("observation_journal", ObservationJournalItem::new, new Properties().stacksTo(1), consumer);
    TRANSMUTATION_WAND = construct("transmutation_wand", TransmutationWandItem::new, new Properties().stacksTo(1), consumer);
    ACIDIC_CAULDRON = construct("acidic_cauldron", p -> new BlockItem(ModBlocks.ACIDIC_CAULDRON, p), new Properties(), consumer);

    TOWER_HEART = construct("tower_heart", p -> new BlockItem(ModBlocks.TOWER_HEART, p), new Properties(), consumer);
    VESTIGIAL_TOWER_HEART = construct("vestigial_tower_heart", p -> new BlockItem(ModBlocks.VESTIGIAL_TOWER_HEART, p), new Properties(), consumer);

    AURA_NODE = construct("aura_node", p -> new BlockItem(ModBlocks.AURA_NODE, p), new Properties(), consumer);
  }

  private static Item construct(String name, Function<Properties, Item> constructor, Properties properties, BiConsumer<Item, ResourceLocation> consumer) {

    ResourceLocation id = Hermetica.identifier(name);
    Item item = constructor.apply(properties);
    consumer.accept(item, id);

    ModItems.REGISTERED_ITEMS.add(item);

    return item;
  }
}
