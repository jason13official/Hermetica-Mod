package io.github.jason13official.hermetica.impl.common.registry;

import io.github.jason13official.hermetica.Constants;
import io.github.jason13official.hermetica.Hermetica;
import io.github.jason13official.hermetica.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  public static CreativeModeTab HERMETICA;

  public static void register(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {

    HERMETICA = Services.PLATFORM.tabBuilder()
        .icon(() -> new ItemStack(ModItems.TOWER_HEART))
        .title(Component.translatable("itemGroup.hermetica"))
        .displayItems((itemDisplayParameters, output) -> {
          ModItems.REGISTERED_ITEMS.forEach(output::accept);
        }).build();

    consumer.accept(HERMETICA, Hermetica.identifier(Constants.MOD_ID));
  }
}
