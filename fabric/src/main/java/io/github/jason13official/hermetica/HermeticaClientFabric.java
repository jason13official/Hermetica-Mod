package io.github.jason13official.hermetica;

import net.fabricmc.api.ClientModInitializer;

public class HermeticaClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    HermeticaClient.init();
  }
}
