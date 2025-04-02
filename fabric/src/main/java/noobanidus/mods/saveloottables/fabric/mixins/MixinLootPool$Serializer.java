package noobanidus.mods.saveloottables.fabric.mixins;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import noobanidus.mods.saveloottables.mixins.AccessorMixinLootItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(LootPool.Serializer.class)
public class MixinLootPool$Serializer {
  @ModifyArg(method= "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/world/level/storage/loot/LootPool;", at=@At(value= "INVOKE", target="Lnet/minecraft/world/level/storage/loot/LootPool;<init>([Lnet/minecraft/world/level/storage/loot/entries/LootPoolEntryContainer;[Lnet/minecraft/world/level/storage/loot/predicates/LootItemCondition;[Lnet/minecraft/world/level/storage/loot/functions/LootItemFunction;Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;)V"), index=0)
  private LootPoolEntryContainer[] initEntries(LootPoolEntryContainer[] value) {
    List<LootPoolEntryContainer> newEntries = new ArrayList<>();
    for (LootPoolEntryContainer entry : value) {
      if (entry instanceof LootItem item) {
        if (((AccessorMixinLootItem) item).SaveLootTablesGetItem() == Items.BARRIER) {
          continue;
        }
        newEntries.add(entry);
      }
    }
    return newEntries.toArray(new LootPoolEntryContainer[0]);
  }
}
