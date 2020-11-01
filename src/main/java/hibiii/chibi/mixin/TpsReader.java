package hibiii.chibi.mixin;
import hibiii.chibi.Chibi;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TpsReader, a simple utility for reading a server's TPS.
// It's obviously not dead accurate, but a rough estimate is given.
@Environment(EnvType.CLIENT)
@Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
public class TpsReader {
	
	private static long worldTickLast = 0l;
	private static long realTimeLast = 0l;

	@Inject(method = "onWorldTimeUpdate", at = @At("HEAD"))
	private void onTick(WorldTimeUpdateS2CPacket packet, CallbackInfo info) {
		
		// tickrate = Δtick / Δtime
		// Doing this directly doesn't work.
		// tickrate = Δtime / timePerTickAvg
		// timePerTickAvg = Δtime / Δtick

		long worldTick = packet.getTime();
		long realTime = System.nanoTime();
		
		long deltaTick = worldTick - worldTickLast;
		//deltaTime would be used only once.

		// prevent bad connections from messing this up
		if(deltaTick <= 0) {
			return;
		}

		Chibi.tpsMspt = ((realTime - realTimeLast) / deltaTick) / 1000000d;
		Chibi.tpsRate = Chibi.tpsMspt < 50 ? 20.0d : (1000d / Chibi.tpsMspt);
		worldTickLast = worldTick;
		realTimeLast = realTime;

		// Reading MSPT is useless unless it's laggy because I'm not measuring the workload on the server.
	}
}