package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TidbitsSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Tidbits.MODID);


    public static final RegistryObject<SoundEvent> DISC_DUNES = register("music.tidbits.disc.dunes");
    public static final RegistryObject<SoundEvent> DISC_KELP = register("music.tidbits.disc.kelp");
    public static final RegistryObject<SoundEvent> DISC_TROPIC = register("music.tidbits.disc.tropic");
    public static final RegistryObject<SoundEvent> DISC_BLIZZARD = register("music.tidbits.disc.blizzard");




    private static RegistryObject<SoundEvent> register(String key) {
        return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(Tidbits.MODID, key)));
    }

    public static void init() {
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
