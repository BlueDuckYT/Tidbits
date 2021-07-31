package blueduck.tidbits.registry;

import blueduck.tidbits.Tidbits;
import blueduck.tidbits.blocks.TidbitsOreBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TidbitsBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Tidbits.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Tidbits.MODID);

    public static final RegistryObject<Block> CHARCOAL_BLOCK = BLOCKS.register("charcoal_block", () -> new Block(Block.Properties.copy(Blocks.COAL_BLOCK)));
    public static final RegistryObject<Item> CHARCOAL_BLOCK_ITEM = ITEMS.register("charcoal_block", () -> new BlockItem(CHARCOAL_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Block> SULFUR_BLOCK = BLOCKS.register("sulfur_block", () -> new Block(Block.Properties.copy(Blocks.COAL_BLOCK)));
    public static final RegistryObject<Item> SULFUR_BLOCK_ITEM = ITEMS.register("sulfur_block", () -> new BlockItem(SULFUR_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));


    public static final RegistryObject<Block> FLINT_ORE = BLOCKS.register("flint_ore", () -> new FallingBlock(Block.Properties.copy(Blocks.GRAVEL).harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Item> FLINT_ORE_ITEM = ITEMS.register("flint_ore", () -> new BlockItem(FLINT_ORE.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Block> SULFUR_ORE = BLOCKS.register("sulfur_ore", () -> new TidbitsOreBlock(Block.Properties.copy(Blocks.NETHER_GOLD_ORE)));
    public static final RegistryObject<Item> SULFUR_ORE_ITEM = ITEMS.register("sulfur_ore", () -> new BlockItem(SULFUR_ORE.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Block> REDSTONE_WORKBENCH = BLOCKS.register("redstone_workbench", () -> new Block(Block.Properties.copy(Blocks.CARTOGRAPHY_TABLE)));
    public static final RegistryObject<Item> REDSTONE_WORKBENCH_ITEM = ITEMS.register("redstone_workbench", () -> new BlockItem(REDSTONE_WORKBENCH.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

    public static final RegistryObject<Block> LOG_SPLITTING_TABLE = BLOCKS.register("log_splitting_table", () -> new Block(Block.Properties.copy(Blocks.CARTOGRAPHY_TABLE)));
    public static final RegistryObject<Item> LOG_SPLITTING_TABLE_ITEM = ITEMS.register("log_splitting_table", () -> new BlockItem(LOG_SPLITTING_TABLE.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
