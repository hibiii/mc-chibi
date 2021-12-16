package hibi.chibi;

import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;

public class ChibiModmenu implements ModMenuApi {
	
	// Cloth Config is holding my hand through this, thx :)
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> Chibi.config.getScreen(parent);
	}
}
