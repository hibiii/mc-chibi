package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import hibi.chibi.Chibi;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

	// WalkAway
	@Inject(
		method = "shouldSlowDown()Z",
		at = @At(value = "INVOKE"),
		cancellable = true)
	public void walkModifier (CallbackInfoReturnable<Boolean> ci) {
		if(Chibi.walkModifier)
			ci.setReturnValue(true);
	}
}
