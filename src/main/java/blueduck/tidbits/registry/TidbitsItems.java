package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import blueduck.tidbits.blocks.SulfurBlock;
import blueduck.tidbits.blocks.TidbitsOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TidbitsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tidbits.MODID);


    public static final RegistryObject<Item> CHAINMAIL = conditionallyRegisterItem("chainmail", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)), () -> Tidbits.CONFIG.CHAINMAIL.get());

    //Discs
    public static final RegistryObject<Item> MUSIC_DISC_DUNES = conditionallyRegisterItem("music_disc_dunes", () -> new MusicDiscItem(1, () -> TidbitsSounds.DISC_DUNES.get(), new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC).rarity(Rarity.RARE)), () -> Tidbits.CONFIG.BIOME_DISCS.get());
    public static final RegistryObject<Item> MUSIC_DISC_KELP = conditionallyRegisterItem("music_disc_kelp", () -> new MusicDiscItem(1, () -> TidbitsSounds.DISC_KELP.get(), new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC).rarity(Rarity.RARE)), () -> Tidbits.CONFIG.BIOME_DISCS.get());
    public static final RegistryObject<Item> MUSIC_DISC_TROPIC = conditionallyRegisterItem("music_disc_tropic", () -> new MusicDiscItem(1, () -> TidbitsSounds.DISC_TROPIC.get(), new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC).rarity(Rarity.RARE)), () -> Tidbits.CONFIG.BIOME_DISCS.get());
    public static final RegistryObject<Item> MUSIC_DISC_BLIZZARD = conditionallyRegisterItem("music_disc_blizzard", () -> new MusicDiscItem(1, () -> TidbitsSounds.DISC_BLIZZARD.get(), new Item.Properties().stacksTo(1).tab(ItemGroup.TAB_MISC).rarity(Rarity.RARE)), () -> Tidbits.CONFIG.BIOME_DISCS.get());


    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static RegistryObject<Item> conditionallyRegisterItem(String registryName, Supplier<Item> item, Supplier<Boolean> condition) {
        if (condition.get())
            return ITEMS.register(registryName, item);
        return null;
    }

}
