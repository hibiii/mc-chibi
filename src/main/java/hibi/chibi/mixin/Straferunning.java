package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import hibi.chibi.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
@Mixin(Entity.class)
public class Straferunning {
	@Redirect(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/math/Vec3d;normalize()Lnet/minecraft/util/math/Vec3d;"
		),
		method = "movementInputToVelocity(Lnet/minecraft/util/math/Vec3d;FF)Lnet/minecraft/util/math/Vec3d;"
	)
	private static Vec3d noNormalize(Vec3d that) {
		if(Config.straferunning)
			return that;
		return that.normalize();
	}
}
