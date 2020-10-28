package hibiii.chibi;
import net.fabricmc.api.ClientModInitializer;

public class Chibi implements ClientModInitializer {

	// PreventWeaponSwing
	public enum OptionPreventSwing {
		OFF,
		LENIENT,
		STRICT
	}
	public static OptionPreventSwing optionPreventSwing = OptionPreventSwing.OFF;
	
	public static double tpsRate = 0.0d;
	public static double tpsMspt = 0.0d;
	
	@Override
	public void onInitializeClient() {
	}
}
