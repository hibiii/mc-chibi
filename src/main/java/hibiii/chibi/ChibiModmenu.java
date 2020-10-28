package hibiii.chibi;

import hibiii.chibi.Chibi.OptionPreventSwing;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ChibiModmenu implements ModMenuApi {
	
	// Cloth Config is holding my hand through this, thx :)
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create();
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
			builder.setParentScreen(parent).setTitle(new TranslatableText("chibi.menu.title"));
			
			// This is dumb (chaining methods), but it's also really cool.
			builder.getOrCreateCategory(new TranslatableText("chibi.menu.general"))
			
			// Prevent Weapon Swing
			.addEntry(entryBuilder
					.startEnumSelector(new TranslatableText("chibi.option.weapon_swing_threshold"),OptionPreventSwing.class,Chibi.optionPreventSwing)
					.setDefaultValue(OptionPreventSwing.OFF)
					.setSaveConsumer(newValue -> Chibi.optionPreventSwing = newValue)
					.setEnumNameProvider(enumKey -> {
						switch ((Chibi.OptionPreventSwing)enumKey) {
							case OFF: return new TranslatableText("chibi.option.weapon_swing_threshold.none");
							case LENIENT: return new TranslatableText("chibi.option.weapon_swing_threshold.lenient");
							case STRICT: return new TranslatableText("chibi.option.weapon_swing_threshold.strict");
						}
						return Text.of("InvalidEnumVal");})
					.build())
			
			// PWS Warning
			.addEntry(entryBuilder.startTextDescription(new TranslatableText("chibi.option.weapon_swing_threshold.warning"))
					.build());
			
			return builder.build();
		};
	}
}
