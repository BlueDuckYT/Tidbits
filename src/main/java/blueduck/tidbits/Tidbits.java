package blueduck.tidbits;

import blueduck.tidbits.config.ConfigHelper;
import blueduck.tidbits.config.TidbitsConfig;
import blueduck.tidbits.registry.TidbitsBlocks;
import blueduck.tidbits.registry.TidbitsConfiguredFeatures;
import blueduck.tidbits.registry.TidbitsItems;
import blueduck.tidbits.registry.TidbitsVillagers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.monster.CaveSpiderEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("tidbits")
public class Tidbits
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static TidbitsConfig CONFIG;

    public static String MODID = "tidbits";

    public Tidbits() {

        CONFIG = ConfigHelper.register(ModConfig.Type.COMMON, TidbitsConfig::new);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        TidbitsBlocks.init();
        TidbitsItems.init();
        TidbitsVillagers.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());


        if (CONFIG.VILLAGER_EAT_PRODUCE.get()) {
            ImmutableMap.Builder<Item, Integer> FOOD_VALUES_BUILDER = ImmutableMap.builder();
            ImmutableSet.Builder<Item> ALLOWED_ITEMS_BUILDER = ImmutableSet.builder();

            VillagerEntity.FOOD_POINTS = FOOD_VALUES_BUILDER.putAll(VillagerEntity.FOOD_POINTS).put(Items.APPLE, 2).put(Items.MELON_SLICE, 1).build();
            VillagerEntity.WANTED_ITEMS = ALLOWED_ITEMS_BUILDER.addAll(VillagerEntity.WANTED_ITEMS).add(Items.APPLE).add(Items.MELON_SLICE).build();
        }
        if (CONFIG.VILLAGER_EAT_BAKED_GOODS.get()) {
            ImmutableMap.Builder<Item, Integer> FOOD_VALUES_BUILDER = ImmutableMap.builder();
            ImmutableSet.Builder<Item> ALLOWED_ITEMS_BUILDER = ImmutableSet.builder();

            VillagerEntity.FOOD_POINTS = FOOD_VALUES_BUILDER.putAll(VillagerEntity.FOOD_POINTS).put(Items.CAKE, 12).put(Items.PUMPKIN_PIE, 8).put(Items.COOKIE, 2).build();
            VillagerEntity.WANTED_ITEMS = ALLOWED_ITEMS_BUILDER.addAll(VillagerEntity.WANTED_ITEMS).add(Items.CAKE).add(Items.PUMPKIN_PIE).add(Items.COOKIE).build();
        }
        if (CONFIG.VILLAGER_EAT_GOLDEN_FOOD.get()) {
            ImmutableMap.Builder<Item, Integer> FOOD_VALUES_BUILDER = ImmutableMap.builder();
            ImmutableSet.Builder<Item> ALLOWED_ITEMS_BUILDER = ImmutableSet.builder();

            VillagerEntity.FOOD_POINTS = FOOD_VALUES_BUILDER.putAll(VillagerEntity.FOOD_POINTS).put(Items.GOLDEN_APPLE, 24).put(Items.ENCHANTED_GOLDEN_APPLE, 120).put(Items.GOLDEN_CARROT, 24).build();
            VillagerEntity.WANTED_ITEMS = ALLOWED_ITEMS_BUILDER.addAll(VillagerEntity.WANTED_ITEMS).add(Items.GOLDEN_APPLE).add(Items.ENCHANTED_GOLDEN_APPLE).add(Items.GOLDEN_CARROT).build();
        }

        event.enqueueWork(() -> {
            TidbitsConfiguredFeatures.registerConfiguredFeatures();
        });

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    @SubscribeEvent
    public static void onSpawnEntity(LivingSpawnEvent.SpecialSpawn event) {
        if (!event.getWorld().isClientSide()) {
            if (CONFIG.CAVE_JOCKEY.get() && (event.getWorld().getRandom().nextDouble() < 0.075) && (event.getEntity() instanceof ZombieEntity && ((ZombieEntity) event.getEntity()).isBaby() && event.getEntity().getY() <= 56)) {
                CaveSpiderEntity entity = EntityType.CAVE_SPIDER.create((World) event.getWorld());
                entity.moveTo(event.getX(), event.getY(), event.getZ(), event.getEntity().yRotO, 0.0F);
                entity.finalizeSpawn((IServerWorld) event.getWorld(), event.getWorld().getCurrentDifficultyAt(new BlockPos(event.getX(), event.getY(), event.getZ())), SpawnReason.JOCKEY, (ILivingEntityData) null, (CompoundNBT) null);
                event.getEntity().startRiding(entity);

            }

        }
    }


    @Mod.EventBusSubscriber(modid = "tidbits")
    public static class ModEvents {

        @SubscribeEvent
        public static void onBiomeLoad(BiomeLoadingEvent event) {
            if (Tidbits.CONFIG.FLINT_ORE.get()) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> TidbitsConfiguredFeatures.CONFIGURED_FLINT_ORE);
            }


            if (event.getCategory().equals(Biome.Category.NETHER) && Tidbits.CONFIG.SULFUR_ORE.get()) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> TidbitsConfiguredFeatures.CONFIGURED_SULFUR_ORE);
            }

            if (event.getCategory().equals(Biome.Category.OCEAN) && Tidbits.CONFIG.PRISMARINE_IN_OCEANS.get()) {
                event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> TidbitsConfiguredFeatures.CONFIGURED_PRISMARINE_OCEAN_STONE);
            }
        }

        @SubscribeEvent
        public static void villagerTrades(final VillagerTradesEvent event) {
            if (CONFIG.DISC_JOCKEY.get() && event.getType() == TidbitsVillagers.DISC_JOCKEY.get()) {
                for (Item i : ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("minecraft:music_discs")).getValues()) {
                    event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(i, 1), new ItemStack(Items.EMERALD, 8), 5, 10, 0.05F));
                    event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(i, 1), new ItemStack(Items.EMERALD, 12), 5, 10, 0.05F));
                    event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(i, 1), new ItemStack(Items.EMERALD, 16), 5, 10, 0.05F));
                    event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 64), new ItemStack(i, 1), 5, 10, 0.05F));

                }
                event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.NOTE_BLOCK, 2), new ItemStack(Items.EMERALD, 3), 5, 10, 0.05F));
                event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.JUKEBOX, 1), new ItemStack(Items.EMERALD, 8), 5, 10, 0.05F));

            }

            if (CONFIG.LUMBERJACK.get() && event.getType() == TidbitsVillagers.LUMBERJACK.get()) {
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.IRON_AXE, 1), new ItemStack(Items.EMERALD, 5), 2, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.STONE_AXE, 1), new ItemStack(Items.EMERALD, 3), 2, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.IRON_HOE, 1), new ItemStack(Items.EMERALD, 3), 1, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.STONE_HOE, 1), new ItemStack(Items.EMERALD, 2), 1, 10, 0.05F));

                for (Item i : ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("minecraft:logs")).getValues()) {
                    event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(i, 8), 16, 10, 0.05F));
                }

                for (Item i : ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("minecraft:planks")).getValues()) {
                    event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(i, 32), 4, 10, 0.05F));
                }

                for (Item i : ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("minecraft:leaves")).getValues()) {
                    event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(i, 16), 4, 10, 0.05F));
                }
                event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Blocks.NETHER_WART_BLOCK, 10), 4, 10, 0.05F));
                event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Blocks.WARPED_WART_BLOCK, 10), 4, 10, 0.05F));

                for (Item i : ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("minecraft:saplings")).getValues()) {
                    event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(i, 1), 4, 10, 0.05F));
                }
            }


            if (CONFIG.ENGINEER.get() && event.getType() == TidbitsVillagers.ENGINEER.get()) {
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.REDSTONE, 12), new ItemStack(Items.EMERALD, 1), 8, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.REDSTONE_TORCH, 3), new ItemStack(Items.EMERALD, 1), 4, 10, 0.05F));

                event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(Items.TARGET, 1), 9, 10, 0.05F));
                event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(Items.REDSTONE_LAMP, 1), 12, 10, 0.05F));
                event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.NOTE_BLOCK, 1), 8, 10, 0.05F));

                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(Items.HOPPER, 1), 8, 10, 0.05F));
                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 5), new ItemStack(Items.DISPENSER, 1), 5, 10, 0.05F));
                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(Items.DROPPER, 1), 5, 10, 0.05F));

                event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(Items.REPEATER, 1), 8, 10, 0.05F));
                event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(Items.COMPARATOR, 1), 8, 10, 0.05F));
                event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(Items.DAYLIGHT_DETECTOR, 1), 8, 10, 0.05F));
                event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(Items.OBSERVER, 1), 8, 10, 0.05F));

                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(Items.PISTON, 1), 8, 10, 0.05F));
                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(Items.STICKY_PISTON, 1), 8, 10, 0.05F));
                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 6), new ItemStack(Items.SLIME_BLOCK, 1), 10, 10, 0.05F));
                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 4), new ItemStack(Items.HONEY_BLOCK, 1), 10, 10, 0.05F));

            }

            if (CONFIG.CONTRACTOR.get() && event.getType() == TidbitsVillagers.CONTRACTOR.get()) {
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.BRICK, 8), new ItemStack(Items.EMERALD, 1), 8, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.NETHER_BRICK, 8), new ItemStack(Items.EMERALD, 1), 6, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.CHAIN, 4), new ItemStack(Items.EMERALD, 1), 6, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.COBBLESTONE, 24), new ItemStack(Items.EMERALD, 1), 4, 10, 0.05F));
                event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.BLACKSTONE, 16), new ItemStack(Items.EMERALD, 1), 4, 10, 0.05F));

                List<Item> powders = ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("tidbits:concrete_powder")).getValues();
                List<Item> concrete = ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("tidbits:concrete")).getValues();

                for (int i = 0; i < powders.size(); i++) {
                    int finalI = i;
                    event.getTrades().get(2).add((entity, random) -> new MerchantOffer(new ItemStack(powders.get(finalI), 16), new ItemStack(Items.EMERALD, 1), new ItemStack(concrete.get(finalI), 16), 8, 25, 0.05F));
                }

                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.BRICKS, 4), 16, 10, 0.05F));
                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.NETHER_BRICKS, 4), 16, 10, 0.05F));
                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.RED_NETHER_BRICKS, 4), 16, 10, 0.05F));
                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.STONE_BRICKS, 8), 8, 10, 0.05F));
                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.QUARTZ_BRICKS, 4), 16, 10, 0.05F));
                event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.POLISHED_BLACKSTONE_BRICKS, 8), 8, 10, 0.05F));

                for (Item i : ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("tidbits:terracotta")).getValues()) {
                    event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(i, 8), 12, 10, 0.05F));
                }
                for (Item i : ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("tidbits:concrete")).getValues()) {
                    event.getTrades().get(4).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(i, 8), 12, 10, 0.05F));
                }

                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.SMOOTH_SANDSTONE, 12), 8, 10, 0.05F));
                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.SMOOTH_RED_SANDSTONE, 12), 12, 10, 0.05F));
                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.SMOOTH_STONE, 12), 12, 10, 0.05F));
                event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.GLASS, 8), 16, 10, 0.05F));


                if (ModList.get().isLoaded("dustrial_decor")) {
                    try {
                        event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:cinder_block")), 4), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05F));
                        event.getTrades().get(1).add((entity, random) -> new MerchantOffer(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:sheet_metal")), 3), new ItemStack(Items.EMERALD, 1), 16, 10, 0.05F));


                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:cinder_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:sheet_metal_siding")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:sheet_metal_walling")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:sheet_metal_treading")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:sheet_metal_paneling")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("dustrial_decor:wrapped_chains")), 1), 12, 10, 0.05F));
                    }
                    catch(Exception e) {

                    }
                }

                if (ModList.get().isLoaded("infernalexp")) {
                    try {
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("infernalexp:glowstone_bricks")), 4), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("infernalexp:dimstone_bricks")), 4), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("infernalexp:dullstone_bricks")), 4), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("infernalexp:basalt_bricks")), 4), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("infernalexp:soul_stone_bricks")), 4), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("infernalexp:glowdust_stone_bricks")), 4), 16, 10, 0.05F));
                    }
                    catch(Exception e) {

                    }

                }

                if (ModList.get().isLoaded("quark")) {
                    try {
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:sandstone_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:red_sandstone_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:soul_sandstone_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:granite_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:andesite_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:diorite_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:marble_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:jasper_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:limestone_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:slate_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:deepslate_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:basalt_bricks")), 8), 16, 10, 0.05F));
                        event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("quark:blue_nether_bricks")), 8), 16, 10, 0.05F));
                        if (ModList.get().isLoaded("environmental")) {
                            event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("environmental:arid_sandstone_bricks")), 8), 16, 10, 0.05F));
                            event.getTrades().get(3).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("environmental:red_arid_sandstone_bricks")), 8), 16, 10, 0.05F));

                            event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("environmental:smooth_arid_sandstone")), 8), 16, 10, 0.05F));
                            event.getTrades().get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("environmental:smooth_red_arid_sandstone")), 8), 16, 10, 0.05F));

                        }
                    }
                    catch(Exception e) {

                    }

                }

            }
            }
    }
}
