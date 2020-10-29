package hibiii.chibi;

import net.fabricmc.api.ClientModInitializer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;

public class Chibi implements ClientModInitializer {
	
	// TpsReader
	public static double tpsRate = 0.0d;
	public static double tpsMspt = 0.0d;
	
	// Config
	public static ChibiConfig config;
	
	@Override
	public void onInitializeClient() {
		AutoConfig.register(ChibiConfig.class,Toml4jConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ChibiConfig.class).getConfig();
	}
}
