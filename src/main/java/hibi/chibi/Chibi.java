package hibi.chibi;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;

// Used mostly as data storage
public class Chibi implements ClientModInitializer {
	
	// WalkAway
	public static boolean walkModifier = false;
	private static KeyBinding walkKeyBind;
	
	// WaveAway
	private static KeyBinding bindWaveMainHand;
	private static KeyBinding bindWaveOffHand;
	
	// PlayerHurtOverride
	public static SoundEvent playerHurtSound;
	public static SoundEvent playerDeathSound;
	
	// Akimbo
	public static boolean canAkimbo = false;
	
	public static MinecraftClient instance;
	
	@Override
	public void onInitializeClient() {
		instance = MinecraftClient.getInstance();
		Config.load();

		// Register the walk modifier key
		walkKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"chibi.key.walk_modifier",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_ALT,
			"key.categories.movement"
		));

		// Register the wave hand keys
		bindWaveMainHand = KeyBindingHelper.registerKeyBinding(new KeyBinding (
				"chibi.key.wave_main_hand",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_UNKNOWN,
				"key.categories.multiplayer"
		));
		bindWaveOffHand = KeyBindingHelper.registerKeyBinding(new KeyBinding (
				"chibi.key.wave_off_hand",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_UNKNOWN,
				"key.categories.multiplayer"
		));
		
		// Don't forget to update the walk modifier flag!
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			walkModifier = walkKeyBind.isPressed();
			if(bindWaveMainHand.wasPressed() && Config.waving)
				instance.player.swingHand(Hand.MAIN_HAND);
			if(bindWaveOffHand.wasPressed() && Config.waving)
				instance.player.swingHand(Hand.OFF_HAND);
		});
	}
}
