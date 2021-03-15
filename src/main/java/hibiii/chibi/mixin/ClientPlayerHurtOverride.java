package hibiii.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerHurtOverride extends PlayerHurtOverride{
	
	@Override
	public void hurtSoundOverride(DamageSource junk, CallbackInfoReturnable<SoundEvent> ci) {
		ci.setReturnValue(SoundEvents.ENTITY_RABBIT_HURT);
	}
	

	@Override
	public void deathSoundOverride(CallbackInfoReturnable<SoundEvent> ci) {
		ci.setReturnValue(SoundEvents.ENTITY_RABBIT_DEATH);
	}
}
