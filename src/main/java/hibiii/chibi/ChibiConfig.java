package hibiii.chibi;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.sound.SoundEvents;
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
	public class CustomParticle {
		public float r = 0.5f;
		public float g = 0.5f;
		public float b = 0.5f;
		public float scale = 1.0f;		
	}
	public CustomParticle customParticle = new CustomParticle();
	
	// PlayerHurtOverride
	public enum PlayerHurtSound {
		DEFAULT, CUSTOM, BAT, BLAZE, CAT, ENDERMAN, FISH, FOX, IRON_GOLEM, PARROT, PHANTOM, RABBIT,
		SHULKER, SLIME, SPIDER, STRIDER, WOLF
	}
	public PlayerHurtSound hurtSoundType = PlayerHurtSound.DEFAULT;
	
	// Hat
	public boolean hat;
	
	// grondag the barbarian? grondag the helpful renderer guy :)
	// Code made after studying Canvas's menu code
	public Screen getScreen (Screen previous) {
		final ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(previous)
				.setTitle(new TranslatableText("chibi.menu.title"))
				.setDefaultBackgroundTexture(new Identifier("chibi:menu_background.png"))
				.setAlwaysShowTabs(false)
				.setDoesConfirmSave(false);
		
		final ConfigEntryBuilder entryBuilder = builder.entryBuilder();
		

		
		// --- Cosmetic --

		// We are building the Particle subcategory first just so we don't have to store the cosmetic
		// category in its own field. C-tier memory management!!!q
		SubCategoryBuilder particleSubcategory = entryBuilder.startSubCategory(new TranslatableText("chibi.option.custom_particles"))
				.setExpanded(particleType == ParticleType.CUSTOM);
		
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

			// Particle Types
			.addEntry(entryBuilder.startEnumSelector(
					new TranslatableText("chibi.option.particle_type"),
					ParticleType.class,
					particleType)
				.setDefaultValue(ParticleType.HEART)
				.setEnumNameProvider(value -> new TranslatableText("chibi.option.particle." + value.toString()))
				.setSaveConsumer(newValue -> particleType = newValue)
				.build())
			
			// Particle Intensity
			.addEntry(entryBuilder.startIntSlider(
					new TranslatableText("chibi.option.particle_intensity"),
					21 - particleInterval,
					1, 20)
				.setDefaultValue(10)
				.setSaveConsumer(newValue -> particleInterval = 21 - newValue)
				.build())
			
			// Particles subcategory
			.addEntry(particleSubcategory.build())
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
		.addEntry(entryBuilder.startEnumSelector(
				new TranslatableText("chibi.option.hurt_sound_override"),
				PlayerHurtSound.class,
				hurtSoundType)
			.setDefaultValue(PlayerHurtSound.DEFAULT)
			.setEnumNameProvider(value -> new TranslatableText("chibi.option.hurt_sound_override." + value.toString()))
			.setTooltip(
				new TranslatableText("chibi.option.hurt_sound_override.tooltip"),
			new TranslatableText("chibi.warn.generic.client_side").formatted(Formatting.GRAY))
			.setSaveConsumer(in -> setPlayerHurtOverride(in))
			.build())
		
		.addEntry(entryBuilder.startBooleanToggle(
				Text.of("Hat"),
				hat)
			.setDefaultValue(false)
			.setSaveConsumer(in -> hat = in)
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
	
	public void setPlayerHurtOverride(PlayerHurtSound in) {
		hurtSoundType = in;
		switch (in) {
		case BAT:
			Chibi.playerHurtSound = SoundEvents.ENTITY_BAT_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_BAT_DEATH;
			break;
		case BLAZE:
			Chibi.playerHurtSound = SoundEvents.ENTITY_BLAZE_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_BLAZE_DEATH;
			break;
		case CAT:
			Chibi.playerHurtSound = SoundEvents.ENTITY_CAT_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_CAT_DEATH;
			break;
		case ENDERMAN:
			Chibi.playerHurtSound = SoundEvents.ENTITY_ENDERMAN_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_ENDERMAN_DEATH;
			break;
		case FISH:
			Chibi.playerHurtSound = SoundEvents.ENTITY_COD_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_COD_DEATH;
			break;
		case FOX:
			Chibi.playerHurtSound = SoundEvents.ENTITY_FOX_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_FOX_DEATH;
			break;
		case IRON_GOLEM:
			Chibi.playerHurtSound = SoundEvents.ENTITY_IRON_GOLEM_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_IRON_GOLEM_DEATH;
			break;
		case PARROT:
			Chibi.playerHurtSound = SoundEvents.ENTITY_PARROT_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_PARROT_DEATH;
			break;
		case PHANTOM:
			Chibi.playerHurtSound = SoundEvents.ENTITY_PHANTOM_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_PHANTOM_DEATH;
			break;
		case RABBIT:
			Chibi.playerHurtSound = SoundEvents.ENTITY_RABBIT_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_RABBIT_DEATH;
			break;
		case SHULKER:
			Chibi.playerHurtSound = SoundEvents.ENTITY_SHULKER_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_SHULKER_DEATH;
			break;
		case SLIME:
			Chibi.playerHurtSound = SoundEvents.ENTITY_SLIME_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_SLIME_DEATH;
			break;
		case SPIDER:
			Chibi.playerHurtSound = SoundEvents.ENTITY_SPIDER_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_SPIDER_DEATH;
			break;
		case STRIDER:
			Chibi.playerHurtSound = SoundEvents.ENTITY_STRIDER_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_STRIDER_DEATH;
			break;
		case WOLF:
			Chibi.playerHurtSound = SoundEvents.ENTITY_WOLF_HURT;
			Chibi.playerDeathSound = SoundEvents.ENTITY_WOLF_DEATH;
			break;
		case DEFAULT:
			Chibi.playerHurtSound = null;
			Chibi.playerDeathSound = null;
			break;
		case CUSTOM:
			Chibi.playerHurtSound = Chibi.CUSTOM_HURT_SOUND;
			Chibi.playerDeathSound = Chibi.CUSTOM_DEATH_SOUND;
			break;
		}
	}
}
