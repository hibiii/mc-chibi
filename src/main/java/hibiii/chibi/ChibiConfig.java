package hibiii.chibi;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.EnumHandler.EnumDisplayOption;

@Config(name = "chibi")
@Config.Gui.Background(value = "textures/environment/end_sky.png")
public class ChibiConfig implements ConfigData {
	
	// PreventWeaponSwing
	public enum PreventSwing {
		OFF,
		LENIENT,
		STRICT
	}

	@ConfigEntry.Category(value = "combat")
	@ConfigEntry.Gui.EnumHandler(option = EnumDisplayOption.BUTTON)
	public PreventSwing preventSwing = PreventSwing.OFF;
	
	// SyncAttack
	@ConfigEntry.Category(value = "combat")
	public boolean syncAttack = false;
}
