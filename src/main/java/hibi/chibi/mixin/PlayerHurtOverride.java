package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

@Mixin(PlayerEntity.class)
public abstract class PlayerHurtOverride {
	
	@Inject(
		method = "getHurtSound",
		at = @At("HEAD"),
		cancellable = true)
	public void hurtSoundOverride(DamageSource junk, CallbackInfoReturnable<SoundEvent> ci) {
	}
	

	@Inject(
		method = "getDeathSound",
		at = @At("HEAD"),
		cancellable = true)
	public void deathSoundOverride(CallbackInfoReturnable<SoundEvent> ci) {
	}
}
