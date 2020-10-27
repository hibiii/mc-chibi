package hibiii.hibex;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.*;

public class HibexModmenu implements ModMenuApi {
	// Sensible19
	public enum WeaponSwingControl {
		NONE,
		LENIENT,
		STRICT
	}
	public static WeaponSwingControl weaponSwingThreshold;
	
	public HibexModmenu() {
		weaponSwingThreshold = WeaponSwingControl.NONE;
	}
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			
			ConfigBuilder builder = ConfigBuilder.create();
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
			builder.setParentScreen(parent).setTitle(Text.of("title.hibex.config"));
			builder.getOrCreateCategory(Text.of("category.examplemod.general"))
			.addEntry(entryBuilder
					.startEnumSelector(Text.of("options.hibex.weaponSwingThreshold"),WeaponSwingControl.class,weaponSwingThreshold)
					.setDefaultValue(WeaponSwingControl.NONE)
					.setSaveConsumer(newValue -> weaponSwingThreshold = newValue)
					.build());
			return builder.build();
		};
	}
}
