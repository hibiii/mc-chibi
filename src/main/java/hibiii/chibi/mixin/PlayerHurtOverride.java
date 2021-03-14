package hibiii.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import hibiii.chibi.Chibi;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@Mixin(ClientPlayNetworkHandler.class)
public class PlayerHurtOverride {
	
	@Inject(
		method = "onPlaySoundFromEntity(Lnet/minecraft/network/packet/s2c/play/PlaySoundFromEntityS2CPacket;)V",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/client/world/ClientWorld;getEntityById(I)Lnet/minecraft/entity/Entity;"),
		locals = LocalCapture.CAPTURE_FAILSOFT)
	public void playerHurtSoundOverride(PlaySoundFromEntityS2CPacket packet, CallbackInfo ci, Entity entity3) {
		if(entity3 == Chibi.instance.player) {
			SoundEvent soundEvent = packet.getSound();
			if(soundEvent == SoundEvents.ENTITY_PLAYER_HURT
				|| soundEvent == SoundEvents.ENTITY_PLAYER_HURT_DROWN
				|| soundEvent == SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE
				|| soundEvent == SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH)
				Chibi.instance.world.playSoundFromEntity(Chibi.instance.player, entity3, SoundEvents.ENTITY_RABBIT_HURT, packet.getCategory(), packet.getVolume(), packet.getPitch());
			if(soundEvent == SoundEvents.ENTITY_PLAYER_DEATH)
				Chibi.instance.world.playSoundFromEntity(Chibi.instance.player, entity3, SoundEvents.ENTITY_RABBIT_DEATH, packet.getCategory(), packet.getVolume(), packet.getPitch());
		}
		return;
	}
}