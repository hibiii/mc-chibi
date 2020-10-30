package hibiii.chibi.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

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
		if (world == null || player == null)
			return;
		world.addParticle(ParticleTypes.WITCH,
				player.getX() + this.random.nextGaussian() * 0.12999999523162842,
				player.getY() + 0.9 + this.random.nextGaussian() * 0.12999999523162842,
				player.getZ() + this.random.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
	}
}
