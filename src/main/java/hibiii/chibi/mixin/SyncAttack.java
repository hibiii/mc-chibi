package hibiii.chibi.mixin;
import hibiii.chibi.Chibi;

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

	// I'll scale client ticking to the server's ticking
	// !!! Try this: cooldown correction instead of tick scaling
	@Inject(method = "tick", at = @At("HEAD"))
	private void correctLastAttackedTicks (CallbackInfo info) {
		if (Chibi.config.syncAttack) {
			--this.lastAttackedTicks;
			milliticks += 1000 * (Chibi.tpsRate / 20.0d);
			if(milliticks > 1000) {
				this.lastAttackedTicks += milliticks / 1000;
				milliticks %= 1000;
			}
		}
	}
}
