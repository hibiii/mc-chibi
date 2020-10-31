package hibiii.chibi;

import net.fabricmc.api.ClientModInitializer;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer.SerializationException;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;

public class Chibi implements ClientModInitializer {
	
	// TpsReader
	public static double tpsRate = 0.0d;
	public static double tpsMspt = 0.0d;
	
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
	}
}
