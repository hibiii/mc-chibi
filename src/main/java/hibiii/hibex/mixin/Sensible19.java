package hibiii.hibex.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.HitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Sensible19 prevents the player from swinging weapon during attack cooldown.
// Currently it restores control at 90% charge, but will be customizable in the future.
// This mod can be seen as kinda cheaty, but I'm implementing it because it's a feature in combat-test.
// Since we still use 1.9 combat, I'll give the option to turn it off, and to restore at 40% or something.
@Mixin(MinecraftClient.class)
public class Sensible19 {
	
	@Shadow
	public ClientPlayerEntity player;
	@Shadow
	public HitResult crosshairTarget;
	
	@Inject(at = @At("HEAD"), method = "doAttack()V", cancellable = true)
	private void attackOnCooldownOverride(CallbackInfo info) {
		// If I'm spam clicking, trying to hunt a mob or player, I am looking at an entity or at nothing.
		// !!! I'm comparing against blocks because it's faster as it's the only option left.
		if (this.player.getAttackCooldownProgress(1.0f) < 0.9f && this.crosshairTarget.getType() != HitResult.Type.BLOCK) {
				info.cancel();
		}
	}
}
