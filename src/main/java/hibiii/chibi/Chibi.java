package hibiii.chibi;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.lwjgl.glfw.GLFW;

import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer.SerializationException;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;

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
	
	// RenderHat
	public static boolean renderHat = false;
	
	// Config
	public static ChibiConfig config;
	
	public static Toml4jConfigSerializer<ChibiConfig> configSerializer =
			new Toml4jConfigSerializer<ChibiConfig>(ChibiConfig.class.getAnnotation(Config.class), ChibiConfig.class);
	
	public static MinecraftClient instance;
	

	public static final Identifier CUSTOM_HURT_SOUND_ID = new Identifier("chibi", "custom_hurt");
	public static final SoundEvent CUSTOM_HURT_SOUND = new SoundEvent(CUSTOM_HURT_SOUND_ID);	
	public static final Identifier CUSTOM_DEATH_SOUND_ID = new Identifier("chibi", "custom_death");
	public static final SoundEvent CUSTOM_DEATH_SOUND = new SoundEvent(CUSTOM_DEATH_SOUND_ID);

	public static final Item TEST_HAT = new Item(new FabricItemSettings().maxCount(1).equipmentSlot((stack) -> {return EquipmentSlot.HEAD;}));
	public static ItemStack testHat;
	
	@Override
	public void onInitializeClient() {
		try {
			config = configSerializer.deserialize();
		} catch (SerializationException e) {
			e.printStackTrace();
		}
		config.setPlayerHurtOverride(config.hurtSoundType);
		
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
			if(bindWaveMainHand.wasPressed())
				instance.player.swingHand(Hand.MAIN_HAND);
			if(bindWaveOffHand.wasPressed())
				instance.player.swingHand(Hand.OFF_HAND);
		});
		
		instance = MinecraftClient.getInstance();
		Registry.register(Registry.ITEM, new Identifier("chibi","test_hat"), TEST_HAT);
		testHat = new ItemStack(TEST_HAT);
	}
}
