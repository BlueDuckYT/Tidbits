package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TidbitsVillagers {

    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Tidbits.MODID);
    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Tidbits.MODID);

    public static final RegistryObject<PointOfInterestType> LUMBERJACK_POI = POI_TYPES.register("lumberjack",
            () -> new PointOfInterestType("lumberjack", PointOfInterestType.getBlockStates(TidbitsBlocks.LUMBERJACK_WORKSTATION.get()), 1, 1)
    );

    public static final RegistryObject<VillagerProfession> LUMBERJACK = PROFESSIONS.register("lumberjack",
            () -> new VillagerProfession("lumberjack", LUMBERJACK_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(TidbitsBlocks.LUMBERJACK_WORKSTATION.get()), null)
    );

    public static final RegistryObject<PointOfInterestType> DISC_JOCKEY_POI = POI_TYPES.register("disc_jockey",
            () -> new PointOfInterestType("disc_jockey", PointOfInterestType.getBlockStates(Blocks.JUKEBOX), 1, 1)
    );

    public static final RegistryObject<VillagerProfession> DISC_JOCKEY = PROFESSIONS.register("disc_jockey",
            () -> new VillagerProfession("disc_jockey", DISC_JOCKEY_POI.get(),
                    ImmutableSet.of(),
                    ImmutableSet.of(Blocks.JUKEBOX), null)
    );

    public static void init() {
        PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POI_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
