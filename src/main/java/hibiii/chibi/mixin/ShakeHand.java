package hibiii.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import hibiii.chibi.Chibi;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;

@Mixin(HeldItemRenderer.class)
public class ShakeHand {
	
	@Redirect(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw(F)F"))
	private float shiveringPlayer(ClientPlayerEntity in, float tickDelta) {
		if(!Chibi.config.overworldAllergyNot & in.world.getDimension().isBedWorking()) {
			return in.getYaw(tickDelta) + (float)(Math.cos(in.age * 3.25) * Math.PI);
		}
		return in.getYaw(tickDelta);
	}
}
