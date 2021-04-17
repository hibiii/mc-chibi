package hibiii.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import hibiii.chibi.Chibi;

import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@Mixin(HeadFeatureRenderer.class)
public abstract class RenderHat<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

	public RenderHat(FeatureRendererContext<T, M> context) {
		super(context);
	}
	
	@Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"))
	public ItemStack forceRenderHat(LivingEntity entity, EquipmentSlot slot) {
		ItemStack temporaryStack = entity.getEquippedStack(slot);
		if(Chibi.config.hat && Chibi.instance.player == entity && temporaryStack.isEmpty())
			return Chibi.testHat;
		return temporaryStack;
	}

}
