package hibiii.chibi.mixin;

import java.util.function.BooleanSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibiii.chibi.Chibi;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

@Mixin(ClientWorld.class)
public class Blackheart {

	@Inject(at = @At("TAIL"), method = "tick")
	public void giveBackgroundWitherEffect(BooleanSupplier junk, CallbackInfo info) {
		if(Chibi.config.blackheart && !Chibi.instance.player.hasStatusEffect(StatusEffects.WITHER)) {
			Chibi.instance.player.addStatusEffect(new StatusEffectInstance(
					StatusEffects.WITHER,
					20,
					0,
					true,
					false, false));
		}
	}
}
