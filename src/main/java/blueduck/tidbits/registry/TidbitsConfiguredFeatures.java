package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public class TidbitsConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> CONFIGURED_FLINT_ORE = Feature.ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.GRAVEL), TidbitsBlocks.FLINT_ORE.get().defaultBlockState(), 13)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(56, 56, 130)))
            .squared()
            .count(20);
    public static final ConfiguredFeature<?, ?> CONFIGURED_SULFUR_ORE = Feature.ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.NETHERRACK), TidbitsBlocks.SULFUR_ORE.get().defaultBlockState(), 5)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(5, 5, 128)))
            .squared()
            .count(15);
    public static final ConfiguredFeature<?, ?> CONFIGURED_PRISMARINE_OCEAN_STONE = Feature.ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Blocks.STONE), Blocks.PRISMARINE.defaultBlockState(), 33)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(5, 5, 72)))
            .squared()
            .count(16);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(Tidbits.MODID, "flint_ore"), CONFIGURED_FLINT_ORE);
        Registry.register(registry, new ResourceLocation(Tidbits.MODID, "sulfur_ore"), CONFIGURED_SULFUR_ORE);
    }



}
