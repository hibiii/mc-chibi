package hibiii.chibi.mixin;
import hibiii.chibi.ChibiConfig;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Previously known as Sensible19, prevents the player from swinging weapon during attack cooldown.
// This mod can be seen as kinda cheaty, but I'm implementing it because it's a feature in combat-test.
@Mixin(MinecraftClient.class)
public class PreventWeaponSwing {
	
	@Shadow
	public ClientPlayerEntity player;
	@Shadow
	public HitResult crosshairTarget;
	
	private float threshold;
	
	@Inject(at = @At("HEAD"), method = "doAttack()V", cancellable = true)
	private void attackOnCooldownOverride(CallbackInfo info) {
		
		switch (ChibiConfig.preventSwing) {
		// Disables this feature entirely.
		case OFF:
			return;
		case LENIENT:
			threshold = 0.5f;
			break;
		case STRICT:
			threshold = 0.9f;
			break;
		// On default, use the last known value.
		};
		
		// If I'm spam clicking, trying to hunt a mob or player, I am looking at an entity or at nothing.
		// Or rather, I don't want to mine slowly, so I won't prevent myself from breaking blocks.
		// !!! I'm comparing against blocks because it's faster as it's the only option left.
		if (this.player.getAttackCooldownProgress(1.0f) < threshold && this.crosshairTarget.getType() != HitResult.Type.BLOCK) {
				info.cancel();
		}
	}
}
