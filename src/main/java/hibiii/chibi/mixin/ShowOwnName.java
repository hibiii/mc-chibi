package hibiii.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import hibiii.chibi.Chibi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public abstract class ShowOwnName<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {

	protected ShowOwnName(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Inject(method = "hasLabel", at = @At("HEAD"), cancellable = true)
	public void showOwnName(T livingEntity, CallbackInfoReturnable<Boolean> cir) {
		if(Chibi.config.hideOwnName)
			return;
		MinecraftClient client = MinecraftClient.getInstance();
		if (livingEntity == client.cameraEntity)
			cir.setReturnValue(true);
	}
}
