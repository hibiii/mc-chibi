package hibiii.chibi;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import me.shedaniel.math.Color;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.serializer.ConfigSerializer.SerializationException;

// Configuration
// Partially operated by AutoConfig, serializable, a frankenstein.
@Config(name = "chibi")
public class ChibiConfig implements ConfigData {
	
	// PreventWeaponSwing
	public enum PreventSwing {
		OFF,
		LENIENT,
		STRICT
	}
	public PreventSwing preventSwing = PreventSwing.OFF;
	
	// SyncAttack
	public enum SyncAttack {
		OFF,
		TICK_SCALING,
		COOLDOWN_CORRECTION,
		HYBRID
	}
	public SyncAttack syncAttack = SyncAttack.OFF;
	
	// ShowOwnName
	public boolean hideOwnName = true;
	
	// WaveAway
	public boolean rightClickWave = false;
	
	// PlayerParticles
	public boolean playerParticles = false;
	public enum ParticleType {
		ASH,
		ENDER_SMOKE,     // Portal
		GREEN_SPARKLES,  // Composter
		HEART,
		MYCELIUM,
		PURPLE_SPARKLES, // Witch
		WHITE_ASH,
		WHITE_SPARKLES,  // End rod
		CUSTOM
	}
	public ParticleType particleType = ParticleType.HEART;
	public int particleInterval = 11;
	public double particleVelocityX = 0.0;
	public double particleVelocityY = 0.0;
	public double particleVelocityZ = 0.0;
	public class CustomParticle {
		public float r = 0.5f;
		public float g = 0.5f;
		public float b = 0.5f;
		public float scale = 1.0f;		
	}
	public CustomParticle customParticle = new CustomParticle();
	public boolean particlesOverworldOnly = false;
	
	// OverAllergic
	public boolean overworldAllergyNot = true;
	
	// grondag the barbarian? grondag the helpful renderer guy :)
	// Code made after studying Canvas's menu code
	public Screen getScreen (Screen previous) {
		final ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(previous)
				.setTitle(new TranslatableText("chibi.menu.title"))
				.setDefaultBackgroundTexture(new Identifier("chibi:menu_background.png"))
				.setAlwaysShowTabs(false)
				.setDoesConfirmSave(false);
		builder.setGlobalized(true);           // Makes the config a single page and adds the sidebar
		builder.setGlobalizedExpanded(false);
		
		final ConfigEntryBuilder entryBuilder = builder.entryBuilder();
		
		// --- Combat --
		builder.getOrCreateCategory(new TranslatableText("chibi.menu.category.combat"))
		
			// Prevent Swing
			.addEntry(entryBuilder.startEnumSelector(
					new TranslatableText("chibi.option.prevent_swing"),
					PreventSwing.class,
					preventSwing)
				.setDefaultValue(PreventSwing.OFF)
				.setEnumNameProvider( value -> new TranslatableText("chibi.option.prevent_swing." + value.toString()))
				.setTooltip(new TranslatableText("chibi.option.prevent_swing.tooltip"))
				.setSaveConsumer(newValue -> preventSwing = newValue)
				.build())
			
			// Sync Attack
			.addEntry(entryBuilder.startEnumSelector(
					new TranslatableText("chibi.option.sync_attack"),
					SyncAttack.class,
					syncAttack)
				.setDefaultValue(SyncAttack.OFF)
				.setEnumNameProvider(value -> new TranslatableText("chibi.option.sync_attack." + value.toString()))
				.setTooltip(
					new TranslatableText("chibi.option.sync_attack.tooltip"),
					new TranslatableText("chibi.warn.experimental").formatted(Formatting.RED))
				.setSaveConsumer(newValue -> syncAttack = newValue)
				.build())
			
			// Wave Away
			.addEntry(entryBuilder.startBooleanToggle(
					new TranslatableText("chibi.option.wave_away"),
					rightClickWave)
				.setDefaultValue(false)
				.setTooltip(
					new TranslatableText("chibi.option.wave_away.tooltip"),
					new TranslatableText("chibi.warn.generic.dont_cheat").formatted(Formatting.YELLOW).formatted(Formatting.UNDERLINE))
				.setSaveConsumer(newValue -> rightClickWave = newValue)
				.build())
			
			// Warning
			.addEntry(entryBuilder.startTextDescription(
					new TranslatableText ("chibi.option.combat.warning"))
				.setTooltip(
					new TranslatableText("chibi.warn.combat.line1"),
					new TranslatableText("chibi.warn.combat.line2"),
					new TranslatableText("chibi.warn.combat.line3"),
					new TranslatableText("chibi.warn.combat.line4"),
					new TranslatableText("chibi.warn.generic.dont_cheat").formatted(Formatting.YELLOW).formatted(Formatting.UNDERLINE))
				.build());
		
		// --- Cosmetic --

		// We are building the Particle subcategory first just so we don't have to store the cosmetic
		// category in its own field. C-tier memory management!!!q
		SubCategoryBuilder particleSubcategory = entryBuilder.startSubCategory(new TranslatableText("chibi.option.particles"))
				.setExpanded(false);

		// Particle Types
		particleSubcategory.add(entryBuilder.startEnumSelector(
				new TranslatableText("chibi.option.particle_type"),
				ParticleType.class,
				particleType)
			.setDefaultValue(ParticleType.HEART)
			.setEnumNameProvider(value -> new TranslatableText("chibi.option.particle." + value.toString()))
			.setSaveConsumer(newValue -> particleType = newValue)
			.build());
		
		// Particle Intensity
		particleSubcategory.add(entryBuilder.startIntSlider(
				new TranslatableText("chibi.option.particle_intensity"),
				21 - particleInterval,
				1, 20)
			.setDefaultValue(10)
			.setSaveConsumer(newValue -> particleInterval = 21 - newValue)
			.build());

		// Particle Velocity
		particleSubcategory.add(entryBuilder.startDoubleField(
				new TranslatableText("chibi.option.particle_velocity", Text.of("X")),
				particleVelocityX)
			.setDefaultValue(0.0)
			.setMin(-10.0)
			.setMax(10.0)
			.setSaveConsumer(newValue -> particleVelocityX = newValue)
			.build());
		particleSubcategory.add(entryBuilder.startDoubleField(
				new TranslatableText("chibi.option.particle_velocity", Text.of("Y")),
				particleVelocityY)
			.setDefaultValue(0.0)
			.setMin(-10.0)
			.setMax(10.0)
			.setSaveConsumer(newValue -> particleVelocityY = newValue)
			.build());
		particleSubcategory.add(entryBuilder.startDoubleField(
				new TranslatableText("chibi.option.particle_velocity", Text.of("Z")),
				particleVelocityZ)
			.setDefaultValue(0.0)
			.setMin(-10.0)
			.setMax(10.0)
			.setSaveConsumer(newValue -> particleVelocityZ = newValue)
			.build());
		
		// Custom Particle Colour
		particleSubcategory.add(0, entryBuilder.startColorField(
				new TranslatableText("chibi.option.custom_particle.color"),
				Color.ofRGB(customParticle.r, customParticle.g, customParticle.b))
			.setDefaultValue(0x808080)
			.setSaveConsumer2(newValue -> {
				customParticle.r = (float)newValue.getRed() / 255;
				customParticle.g = (float)newValue.getGreen() / 255;
				customParticle.b = (float)newValue.getBlue() / 255; })
			.build());
		
		// Custom Particle Scale
		particleSubcategory.add(1, entryBuilder.startFloatField(
				new TranslatableText("chibi.option.custom_particle.scale"),
				customParticle.scale)
			.setDefaultValue(1.0f)
			.setMin(0.1f)
			.setMax(4.0f)
			.setSaveConsumer(newValue -> customParticle.scale = newValue)
			.build());
		
		// The category itself
		builder.getOrCreateCategory(new TranslatableText("chibi.menu.category.cosmetic"))
		
			// Shown Own Name
			.addEntry(entryBuilder.startBooleanToggle(
					new TranslatableText("chibi.option.show_own_name"),
					!hideOwnName)
				.setDefaultValue(false)
				.setTooltip(
						new TranslatableText("chibi.option.show_own_name.tooltip"))
				.setSaveConsumer(newValue -> hideOwnName = !newValue)
				.build())
			
			// Player Particles
			.addEntry(entryBuilder.startBooleanToggle(
					new TranslatableText("chibi.option.player_particles"),
					playerParticles)
				.setDefaultValue(false)
				.setTooltip(
						new TranslatableText("chibi.option.player_particles.tooltip"),
						new TranslatableText("chibi.warn.generic.client_side").formatted(Formatting.GRAY))
				.setSaveConsumer(newValue -> playerParticles = newValue)
				.build())
			
			// Particles subcategory
			.addEntry(particleSubcategory.build())
			.addEntry(entryBuilder.startBooleanToggle(
					new TranslatableText("chibi.option.overworld_allergy"),
					!overworldAllergyNot)
				.setDefaultValue(false)
				.setTooltip(
						new TranslatableText("chibi.option.overworld_allergy.tooltip"),
						new TranslatableText("chibi.warn.generic.client_side").formatted(Formatting.GRAY))
				.setSaveConsumer(newValue -> overworldAllergyNot = !newValue)
				.build());
			
		// Since I'm not using AutoConfig properly, this will do.
		builder.setSavingRunnable(() -> {
			try {
				Chibi.configSerializer.serialize(this);
			} catch (SerializationException e) {
				e.printStackTrace();
			}
		});
		
		return builder.build();
	}
}
