package hibiii.chibi;

import io.github.prospector.modmenu.api.ModMenuApi;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ChibiModmenu implements ModMenuApi {
	
	// Cloth Config is holding my hand through this, thx :)
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> ChibiConfig.getScreen(parent);
	}
}
