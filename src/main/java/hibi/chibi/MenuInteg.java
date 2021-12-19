package hibi.chibi;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class MenuInteg implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(new TranslatableText("chibi.config.title"));
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
			builder.getOrCreateCategory(new TranslatableText("chibi.config.cat.general"))
				.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("chibi.config.ign_display"), Config.ignDisplay)
					.setDefaultValue(false)
					.setSaveConsumer(val -> Config.ignDisplay = val)
					.setTooltip(new TranslatableText("chibi.config.ign_display.tooltip"))
					.build())
				.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("chibi.config.waving"), Config.waving)
					.setDefaultValue(false)
					.setSaveConsumer(val -> Config.waving = val)
					.setTooltip(new TranslatableText("chibi.config.waving.tooltip"))
					.build())
				.addEntry(entryBuilder.startTextDescription(new TranslatableText("chibi.config.waving.keys"))
					.build())
				.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("chibi.config.akimbo"), Config.akimbo)
					.setDefaultValue(false)
					.setSaveConsumer(val -> Config.akimbo = val)
					.setTooltip(new TranslatableText("chibi.config.akimbo.tooltip"), new TranslatableText("chibi.config.tooltip.dont_cheat").formatted(Formatting.RED), new TranslatableText("chibi.config.tooltip.wip").formatted(Formatting.RED))
					.build())
				.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("chibi.config.straferunning"), Config.straferunning)
					.setDefaultValue(false)
					.setSaveConsumer(val -> Config.straferunning = val)
					.setTooltip(new TranslatableText("chibi.config.straferunning.tooltip"), new TranslatableText("chibi.config.tooltip.dont_cheat").formatted(Formatting.RED))
					.build())
				.addEntry(entryBuilder.startBooleanToggle(new TranslatableText("chibi.config.save_anywhere"), Config.saveAnywhere)
					.setDefaultValue(false)
					.setSaveConsumer(val -> Config.saveAnywhere = val)
					.setTooltip(new TranslatableText("chibi.config.save_anywhere.tooltip"))
					.build())
				.setBackground(new Identifier("chibi", "menu_background.png"));
				builder.setSavingRunnable(() -> Config.save());
			return builder.build();
		};
	}
}
