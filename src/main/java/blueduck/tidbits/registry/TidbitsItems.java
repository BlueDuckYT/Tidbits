package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import blueduck.tidbits.blocks.SulfurBlock;
import blueduck.tidbits.blocks.TidbitsOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TidbitsItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tidbits.MODID);


    public static final RegistryObject<Item> CHAINMAIL = conditionallyRegisterItem("chainmail", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)), () -> Tidbits.CONFIG.CHAINMAIL.get());


    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static RegistryObject<Item> conditionallyRegisterItem(String registryName, Supplier<Item> item, Supplier<Boolean> condition) {
        if (condition.get())
            return ITEMS.register(registryName, item);
        return null;
    }

}
