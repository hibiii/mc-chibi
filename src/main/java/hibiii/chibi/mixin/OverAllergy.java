package hibiii.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import hibiii.chibi.Chibi;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public abstract class OverAllergy<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> {
	
	protected OverAllergy(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Inject(at = @At("HEAD"), method = "isShaking", cancellable = true)
	protected void isShakingOverride (T entity, CallbackInfoReturnable<Boolean> info) {
		if(Chibi.config.overworldAllergyNot)
			return;
		//MinecraftClient plsNoLeakerinoHiber = MinecraftClient.getInstance();
		info.setReturnValue(Chibi.instance.cameraEntity == entity && entity.world.getDimension().isBedWorking());
	}


}
