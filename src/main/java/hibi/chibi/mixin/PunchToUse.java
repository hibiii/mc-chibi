package hibi.chibi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import hibi.chibi.Chibi;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

@Mixin(MinecraftClient.class)
public class PunchToUse {

	@Shadow
	public ClientPlayerInteractionManager interactionManager;
	
	@Shadow
	public ClientWorld world;
	
	@Shadow
	public ClientPlayerEntity player;
	
	@Inject(
		method = "doAttack()V",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/util/hit/BlockHitResult;getBlockPos()Lnet/minecraft/util/math/BlockPos;"),
		locals = LocalCapture.CAPTURE_FAILSOFT)
	public void openDoorLikeTroglodyte (CallbackInfo info, BlockHitResult blockHitResult2) {
		if(Chibi.config.punchToUse) {
			Block temp = this.world.getBlockState(blockHitResult2.getBlockPos()).getBlock();
			if(temp instanceof BlockWithEntity && !(temp instanceof BedBlock || temp instanceof CampfireBlock || temp instanceof BellBlock))
				return;
			for(final Hand hond : Hand.values())
				if(this.interactionManager.interactBlock(player, world, hond, blockHitResult2).isAccepted())
					return;
		}
	}
}
