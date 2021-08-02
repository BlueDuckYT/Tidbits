package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

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
    }

    public static RegistryObject<VillagerProfession> condRegister (String registryName, Supplier<VillagerProfession> profession, Supplier<Boolean> condition) {
        if (condition.get())
            return PROFESSIONS.register(registryName, profession);
        return null;
    }
}
