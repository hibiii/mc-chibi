package hibiii.chibi.mixin;
import hibiii.chibi.Chibi;
import hibiii.chibi.ChibiConfig;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// "TPS aware 1.9", the vanilla client doesn't take into consideration a server's lagginess on combat.
// This causes the combat bar to visually recharge faster on the client compared to the server.
// This mod alleviates this problem, though it's not completely fixed.
@Environment(EnvType.CLIENT)
@Mixin(PlayerEntity.class)
public class SyncAttack {
	
	// Somehow I don't need an accessor here
	protected int lastAttackedTicks;
	private int milliticks = 0;

	// Supports tick scaling and cooldown correction
	@Inject(method = "tick", at = @At("HEAD"))
	private void correctLastAttackedTicks (CallbackInfo info) {
		if (Chibi.config.syncAttack == ChibiConfig.SyncAttack.OFF) return;
		// If you're exclusively on TS, you're not on CC.
		if (Chibi.config.syncAttack != ChibiConfig.SyncAttack.COOLDOWN_CORRECTION) {
			--this.lastAttackedTicks;
			milliticks += 1000 * (Chibi.tpsRate / 20.0d);
			if(milliticks > 1000) {
				this.lastAttackedTicks += milliticks / 1000;
				milliticks %= 1000;
			}
		}
		// If you're exclusively on CC, you're not on TS.
		if (Chibi.config.syncAttack != ChibiConfig.SyncAttack.TICK_SCALING) {
			if(Chibi.tpsElapsedTicks > 0) {
				this.lastAttackedTicks -= Chibi.tpsElapsedTicks;
				Chibi.tpsElapsedTicks = 0;
			}
		}
		// If you're on hybrid, you are not exclusively on TS and your are not exclusively on CC.
	}
}
