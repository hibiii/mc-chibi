package hibiii.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import hibiii.chibi.Chibi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@Mixin(ClientPlayNetworkHandler.class)
public class PlayerHurtOverride {
	
	@Shadow
	public MinecraftClient client;
	
	@Inject(
		method = "onPlaySoundFromEntity(Lnet/minecraft/network/packet/s2c/play/PlaySoundFromEntityS2CPacket;)V",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/client/world/ClientWorld;getEntityById(I)Lnet/minecraft/entity/Entity;"),
		locals = LocalCapture.CAPTURE_FAILSOFT,
		cancellable = true)
	public void playerHurtSoundOverride(PlaySoundFromEntityS2CPacket packet, CallbackInfo ci, Entity entity3) {
		if(entity3 == client.player) {
			SoundEvent soundEvent = packet.getSound();
			if(soundEvent == SoundEvents.ENTITY_PLAYER_HURT
				|| soundEvent == SoundEvents.ENTITY_PLAYER_HURT_DROWN
				|| soundEvent == SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE
				|| soundEvent == SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH) {
				client.world.playSoundFromEntity(client.player, entity3, SoundEvents.ENTITY_RABBIT_HURT, packet.getCategory(), packet.getVolume(), packet.getPitch());
				ci.cancel();
			}
			if(soundEvent == SoundEvents.ENTITY_PLAYER_DEATH) {
				client.world.playSoundFromEntity(client.player, entity3, SoundEvents.ENTITY_RABBIT_DEATH, packet.getCategory(), packet.getVolume(), packet.getPitch());
				ci.cancel();
			}
		}
	}
}
