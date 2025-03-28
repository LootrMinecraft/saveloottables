package noobanidus.mods.saveloottables.common.mixins;

import com.mojang.serialization.DataResult;
import net.minecraft.world.level.storage.loot.LootDataType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(LootDataType.class)
public class MixinLootDataType {
  @Redirect(method="deserialize", at=@At(value="INVOKE", target="Lcom/mojang/serialization/DataResult;result()Ljava/util/Optional;"))
  private <R> Optional<R> SaveLootTablesDeserialize (DataResult instance) {
    //noinspection unchecked
    return (Optional<R>) instance.resultOrPartial();
  }
}
