package hibiii.chibi.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibiii.chibi.Chibi;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;


@Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
public class TpsReader {
	
	private static long worldTickLast = 0l;
	private static long realTimeLast = 0l;

	@Inject(method = "onWorldTimeUpdate", at = @At("HEAD"))
	private void onTick(WorldTimeUpdateS2CPacket packet, CallbackInfo info) {
		
		long worldTick = packet.getTime();
		long realTime = System.nanoTime();
		
		long deltaTick = worldTick - worldTickLast;

		// prevent bad connection from messing this up
		if(deltaTick <= 0) {
			return;
		}

		Chibi.tpsMspt = ((realTime - realTimeLast) / deltaTick) / 1000000d;
		Chibi.tpsRate = Chibi.tpsMspt < 50.0d ? 20.0d : (1000d / Chibi.tpsMspt);
		worldTickLast = worldTick;
		realTimeLast = realTime;
	}
}