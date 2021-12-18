package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibi.chibi.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

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
}
