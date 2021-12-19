package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibi.chibi.Chibi;
import hibi.chibi.Config;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

	@Shadow
	private float equipProgressMainHand;
	@Shadow
	private float equipProgressOffHand;

	@Inject(
		method = "updateHeldItems()V",
		at = @At("TAIL"))
	private void akimboThingy(CallbackInfo info) {
		if(Config.akimbo && Chibi.canAkimbo)
			this.equipProgressOffHand = this.equipProgressMainHand;
	}

	@Redirect(
		method = "updateHeldItems()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"))
	private float attackCooldownOverride(PlayerEntity that, float base) {
		if (Config.akimbo) return base;
		return that.getAttackCooldownProgress(base);
	}
}
