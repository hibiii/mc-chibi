package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibi.chibi.Chibi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Shadow @Final
	private PlayerInventory inventory;

	@Inject(
		method = "resetLastAttackedTicks()V",
		at = @At("HEAD")
	)
	public void checkIfCanAkimbo(CallbackInfo info) {
		Chibi.canAkimbo = ItemStack.areItemsEqual(this.inventory.getMainHandStack(), this.inventory.offHand.get(0));
	}
}
