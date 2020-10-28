package hibiii.hibex.mixin;
import hibiii.hibex.Hibix;

import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class SyncAttack {
	
	protected int lastAttackedTicks;
	private int militicks = 0;

	@Inject(method = "tick", at = @At("HEAD"))
	private void correctLastAttackedTicks (CallbackInfo info) {
		--this.lastAttackedTicks;
		militicks += 1000 * (Hibix.tpsRate / 20.0d);
		if(militicks > 1000) {
			this.lastAttackedTicks += militicks / 1000;
			militicks %= 1000;
		}
	}
}
