package hibi.chibi.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import hibi.chibi.Config;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Inject(
		method = "addChatMessage(Lnet/minecraft/network/MessageType;Lnet/minecraft/text/Text;Ljava/util/UUID;)V",
		at = @At("HEAD"),
		cancellable = true)
	private void messageStuff(MessageType type, Text text, UUID sender, CallbackInfo info) {
		if(Config.noWhispers && text instanceof TranslatableText && ((TranslatableText)text).getKey() == "commands.message.display.incoming") {
			info.cancel();
		}
		if(Config.noLiterals && text instanceof LiteralText) {
			info.cancel();
		}
	}
}