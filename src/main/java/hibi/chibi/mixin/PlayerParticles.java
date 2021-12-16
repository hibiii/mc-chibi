package hibi.chibi.mixin;
import hibi.chibi.Chibi;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
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
	
	@Shadow
	private boolean paused;
	
	private ParticleEffect selectedParticle;
	
	private short tickCount = 0;
	
	@Inject(method = "tick", at = @At("HEAD"))
	private void addParticles(CallbackInfo info) {
		if (!Chibi.config.playerParticles || world == null || paused || player == null || player.isInvisible())
			return;
		tickCount++;
		if (tickCount % Chibi.config.particleInterval == 0) {
			// I don't need to reset tickCount as its amplitude doesn't matter, I can let it overflow in 54 min and some
			switch (Chibi.config.particleType) {
				case ASH: selectedParticle = ParticleTypes.ASH; break;
				case ENDER_SMOKE: selectedParticle = ParticleTypes.PORTAL; break;
				case GREEN_SPARKLES: selectedParticle = ParticleTypes.COMPOSTER; break;
				case HEART: selectedParticle = ParticleTypes.HEART; break;
				case MYCELIUM: selectedParticle = ParticleTypes.MYCELIUM; break;
				case PURPLE_SPARKLES: selectedParticle = ParticleTypes.WITCH; break;
				case WHITE_ASH: selectedParticle = ParticleTypes.WHITE_ASH; break;
				case WHITE_SPARKLES: selectedParticle = ParticleTypes.END_ROD; break;
				case CUSTOM: selectedParticle = new DustParticleEffect(
					Chibi.config.customParticle.r,
					Chibi.config.customParticle.g,
					Chibi.config.customParticle.b,
					Chibi.config.customParticle.scale); break;
			}
			world.addParticle(selectedParticle,
				player.getX() + world.random.nextGaussian() * 0.3,
				player.getY() + world.random.nextDouble() * player.getBoundingBox().getYLength(),
				player.getZ() + world.random.nextGaussian() * 0.3,
				0, 0, 0);
		}
	}
}
