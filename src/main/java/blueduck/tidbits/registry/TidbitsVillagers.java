package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TidbitsVillagers {

    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Tidbits.MODID);
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Tidbits.MODID);

    public static final RegistryObject<PointOfInterestType> ENGINEER_POI = POI_TYPES.register("engineer",
            () -> new PointOfInterestType("engineer", PointOfInterestType.getBlockStates(TidbitsBlocks.REDSTONE_WORKBENCH.get()), 1, 1)
    );

    public static final RegistryObject<VillagerProfession> ENGINEER = condRegister("engineer",
            () -> new VillagerProfession("engineer", ENGINEER_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(TidbitsBlocks.REDSTONE_WORKBENCH.get()), null), () -> Tidbits.CONFIG.ENGINEER.get()
    );

    public static final RegistryObject<PointOfInterestType> LUMBERJACK_POI = POI_TYPES.register("lumberjack",
            () -> new PointOfInterestType("lumberjack", PointOfInterestType.getBlockStates(TidbitsBlocks.LOG_SPLITTING_TABLE.get()), 1, 1)
    );

    public static final RegistryObject<VillagerProfession> LUMBERJACK = condRegister("lumberjack",
            () -> new VillagerProfession("lumberjack", LUMBERJACK_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(TidbitsBlocks.LOG_SPLITTING_TABLE.get()), null), () -> Tidbits.CONFIG.LUMBERJACK.get()
    );

    public static final RegistryObject<PointOfInterestType> CONTRACTOR_POI = POI_TYPES.register("contractor",
            () -> new PointOfInterestType("contractor", PointOfInterestType.getBlockStates(TidbitsBlocks.BLUEPRINT_TABLE.get()), 1, 1)
    );

    public static final RegistryObject<VillagerProfession> CONTRACTOR = condRegister("contractor",
            () -> new VillagerProfession("contractor", CONTRACTOR_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(TidbitsBlocks.BLUEPRINT_TABLE.get()), null), () -> Tidbits.CONFIG.CONTRACTOR.get()
    );

    public static final RegistryObject<PointOfInterestType> DISC_JOCKEY_POI = POI_TYPES.register("disc_jockey",
            () -> new PointOfInterestType("disc_jockey", PointOfInterestType.getBlockStates(Blocks.JUKEBOX), 1, 1)
    );

    public static final RegistryObject<VillagerProfession> DISC_JOCKEY = condRegister("disc_jockey",
            () -> new VillagerProfession("disc_jockey", DISC_JOCKEY_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(Blocks.JUKEBOX), null), () -> Tidbits.CONFIG.DISC_JOCKEY.get()
    );

    public static void init() {
        PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POI_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        addHouses();
    }

    public static RegistryObject<VillagerProfession> condRegister (String registryName, Supplier<VillagerProfession> profession, Supplier<Boolean> condition) {
        if (condition.get())
            return PROFESSIONS.register(registryName, profession);
        return null;
    }

    public static void addHouses() {
        PlainsVillagePools.bootstrap();
        TaigaVillagePools.bootstrap();
        SnowyVillagePools.bootstrap();
        DesertVillagePools.bootstrap();
        SavannaVillagePools.bootstrap();

        if (Tidbits.CONFIG.ENGINEER.get()) {
            addToPool(new ResourceLocation("village/plains/houses"), new ResourceLocation("tidbits:village/engineer_plains"), 8);
            addToPool(new ResourceLocation("village/desert/houses"), new ResourceLocation("tidbits:village/engineer_desert"), 10);
            addToPool(new ResourceLocation("village/savanna/houses"), new ResourceLocation("tidbits:village/engineer_savanna"), 10);
            addToPool(new ResourceLocation("village/taiga/houses"), new ResourceLocation("tidbits:village/engineer_taiga"), 8);
            addToPool(new ResourceLocation("village/snowy/houses"), new ResourceLocation("tidbits:village/engineer_snowy"), 8);

        }

        if (Tidbits.CONFIG.LUMBERJACK.get()) {
            addToPool(new ResourceLocation("village/plains/houses"), new ResourceLocation("tidbits:village/lumberjack_plains"), 6);
            addToPool(new ResourceLocation("village/snowy/houses"), new ResourceLocation("tidbits:village/lumberjack_snowy"), 10);
            addToPool(new ResourceLocation("village/taiga/houses"), new ResourceLocation("tidbits:village/lumberjack_taiga"), 30);
            addToPool(new ResourceLocation("village/desert/houses"), new ResourceLocation("tidbits:village/lumberjack_desert"), 20);
            addToPool(new ResourceLocation("village/savanna/houses"), new ResourceLocation("tidbits:village/lumberjack_savanna"), 20);
        }

        if (Tidbits.CONFIG.CONTRACTOR.get()) {

            addToPool(new ResourceLocation("village/plains/houses"), new ResourceLocation("tidbits:village/contractor_plains"), 6);
            addToPool(new ResourceLocation("village/desert/houses"), new ResourceLocation("tidbits:village/contractor_desert"), 16);
            addToPool(new ResourceLocation("village/savanna/houses"), new ResourceLocation("tidbits:village/contractor_savanna"), 10);
            addToPool(new ResourceLocation("village/taiga/houses"), new ResourceLocation("tidbits:village/contractor_taiga"), 10);
            addToPool(new ResourceLocation("village/snowy/houses"), new ResourceLocation("tidbits:village/contractor_snowy"), 10);
        }

        if (Tidbits.CONFIG.DISC_JOCKEY.get() && Tidbits.CONFIG.DISCO_TILES.get()) {
            addToPool(new ResourceLocation("village/plains/houses"), new ResourceLocation("tidbits:village/dj_stage_plains"), 8);
            addToPool(new ResourceLocation("village/snowy/houses"), new ResourceLocation("tidbits:village/dj_stage_snowy"), 8);
            addToPool(new ResourceLocation("village/taiga/houses"), new ResourceLocation("tidbits:village/dj_stage_taiga"), 8);
            addToPool(new ResourceLocation("village/desert/houses"), new ResourceLocation("tidbits:village/dj_stage_desert"), 8);
            addToPool(new ResourceLocation("village/savanna/houses"), new ResourceLocation("tidbits:village/dj_stage_savanna"), 8);
        }

    }



    private static void addToPool(ResourceLocation pool, ResourceLocation toAdd, int weight) {
        JigsawPattern old = WorldGenRegistries.TEMPLATE_POOL.get(pool);
        List<JigsawPiece> shuffled = old != null ? old.getShuffledTemplates(new Random()) : ImmutableList.of();
        List<Pair<JigsawPiece, Integer>> newPieces = shuffled.stream().map(p -> new Pair<>(p, 1)).collect(Collectors.toList());
        newPieces.add(new Pair<>(new LegacySingleJigsawPiece(Either.left(toAdd), () -> ProcessorLists.EMPTY, JigsawPattern.PlacementBehaviour.RIGID), weight));
        ResourceLocation name = old.getName();
        Registry.register(WorldGenRegistries.TEMPLATE_POOL, pool, new JigsawPattern(pool, name, newPieces));
    }
}
