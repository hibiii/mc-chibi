package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibi.chibi.Chibi;
import hibi.chibi.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	private static boolean offHand = false;

	@Shadow
	public ClientPlayerEntity player;
	
	@Shadow
	public HitResult crosshairTarget;
	
	// WaveAway (looking at nothing and right clicking)
	@Inject(
		method = "doItemUse()V",
		at = @At("TAIL"))
	private void rightClickAirToSwing(CallbackInfo info) {
		if (Config.waving && this.crosshairTarget.getType() == HitResult.Type.MISS && this.player.getStackInHand(Hand.OFF_HAND).isEmpty()) {
			this.player.swingHand(Hand.OFF_HAND);
			return;
		}
	}

	// SwordsAkimbo
	@Inject(
		method = "doAttack()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"),
		cancellable = true)
	private void akimbo(CallbackInfo info) {
		if(Config.akimbo) {
			Chibi.canAkimbo = ItemStack.areItemsEqual(this.player.getOffHandStack(), this.player.getMainHandStack());
			if(Chibi.canAkimbo) {
				this.player.swingHand(offHand? Hand.OFF_HAND : Hand.MAIN_HAND);
				offHand = !offHand;
				info.cancel();
			}
		}
	}
}
