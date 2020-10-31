package hibiii.chibi.mixin;
import hibiii.chibi.Chibi;

import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class PlayerParticles {

	@Shadow
	public ClientWorld world;
	
	@Shadow
	public ClientPlayerEntity player;
	
	protected final Random random = new Random();
	
	@Inject(method = "tick", at = @At("HEAD"))
	private void addParticles(CallbackInfo info) {
		if (!Chibi.config.playerParticles || world == null || player == null || player.isInvisible())
			return;
		world.addParticle(ParticleTypes.WITCH,
				player.getX() + this.random.nextGaussian() * 0.3,
				player.getY() + this.random.nextDouble() * player.getBoundingBox().getYLength(),
				player.getZ() + this.random.nextGaussian() * 0.3,
				0.0, 0.0, 0.0);
	}
}
