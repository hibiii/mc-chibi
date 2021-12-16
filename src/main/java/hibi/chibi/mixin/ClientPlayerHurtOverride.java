package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import hibi.chibi.Chibi;
import hibi.chibi.ChibiConfig.PlayerHurtSound;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerHurtOverride extends PlayerHurtOverride{
	
	@Override
	public void hurtSoundOverride(DamageSource junk, CallbackInfoReturnable<SoundEvent> ci) {
		if(Chibi.config.hurtSoundType == PlayerHurtSound.DEFAULT)
			return;
		ci.setReturnValue(Chibi.playerHurtSound);
	}
	

	@Override
	public void deathSoundOverride(CallbackInfoReturnable<SoundEvent> ci) {
		if(Chibi.config.hurtSoundType == PlayerHurtSound.DEFAULT)
			return;
		ci.setReturnValue(Chibi.playerDeathSound);
	}
}
