package noobanidus.mods.saveloottables.mixins;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import noobanidus.mods.saveloottables.SaveLootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LootItem.Serializer.class)
public class MixinLootItem$Serializer {
  @Redirect(method= "deserialize(Lcom/google/gson/JsonObject;Lcom/google/gson/JsonDeserializationContext;II[Lnet/minecraft/world/level/storage/loot/predicates/LootItemCondition;[Lnet/minecraft/world/level/storage/loot/functions/LootItemFunction;)Lnet/minecraft/world/level/storage/loot/entries/LootItem;", at=@At(value="INVOKE", target="Lnet/minecraft/util/GsonHelper;getAsItem(Lcom/google/gson/JsonObject;Ljava/lang/String;)Lnet/minecraft/world/item/Item;"))
  private Item getAsItem(JsonObject p_13910_, String p_13911_) {
    Item item;
    try {
      item = GsonHelper.getAsItem(p_13910_, p_13911_);
    } catch (JsonSyntaxException exception) {
      item = Items.AIR;
      SaveLootTables.LOG.error("Caught an exception while trying to get item from loot table: '{}' does not exist in the item registry.", GsonHelper.getAsString(p_13910_, p_13911_));
      SaveLootTables.LOG.error("This entry in the loot table has been ignored. Original error is as follows:");
      for (StackTraceElement element : exception.getStackTrace()) {
        SaveLootTables.LOG.error(element.toString());
      }
    }
    return item;
  }
}
