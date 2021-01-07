package hibiii.chibi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

import org.lwjgl.glfw.GLFW;

import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer.SerializationException;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;

// Used mostly as data storage
public class Chibi implements ClientModInitializer {
	
	// TpsReader
	public static double tpsRate = 0.0d;
	public static double tpsMspt = 0.0d;
	
	// SyncAttack
	public static long syncAttackElapsedTicks = 0l;
	
	// WalkAway
	public static boolean walkModifier = false;
	private static KeyBinding walkKeyBind;
	
	// Config
	public static ChibiConfig config;
	
	public static Toml4jConfigSerializer<ChibiConfig> configSerializer =
			new Toml4jConfigSerializer<ChibiConfig>(ChibiConfig.class.getAnnotation(Config.class), ChibiConfig.class);
	
	@Override
	public void onInitializeClient() {
		try {
			config = configSerializer.deserialize();
		} catch (SerializationException e) {
			e.printStackTrace();
		}
		
		// Register the walk modifier key
		walkKeyBind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.chibi.walk_modifier",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_ALT,
			"key.categories.movement"
		));
		
		// Don't forget to update the walk modifier flag!
		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			walkModifier = walkKeyBind.isPressed();
		});
	}
}
