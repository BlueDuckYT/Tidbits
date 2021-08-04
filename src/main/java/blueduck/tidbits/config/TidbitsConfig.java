package blueduck.tidbits.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TidbitsConfig {


    public ConfigHelper.ConfigValueListener<Boolean> ENGINEER;
    public ConfigHelper.ConfigValueListener<Boolean> LUMBERJACK;
    public ConfigHelper.ConfigValueListener<Boolean> CONTRACTOR;
    public ConfigHelper.ConfigValueListener<Boolean> DISC_JOCKEY;

    public ConfigHelper.ConfigValueListener<Boolean> FLINT_ORE;
    public ConfigHelper.ConfigValueListener<Boolean> SULFUR_ORE;

    public ConfigHelper.ConfigValueListener<Boolean> PRISMARINE_IN_OCEANS;

    public ConfigHelper.ConfigValueListener<Boolean> CHARCOAL_BLOCK;

    public ConfigHelper.ConfigValueListener<Boolean> VILLAGER_EAT_PRODUCE;
    public ConfigHelper.ConfigValueListener<Boolean> VILLAGER_EAT_BAKED_GOODS;
    public ConfigHelper.ConfigValueListener<Boolean> VILLAGER_EAT_GOLDEN_FOOD;


    public TidbitsConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber) {
        builder.push("General");

        builder.push("Villager Professions");
        this.ENGINEER= subscriber.subscribe(builder
                .comment("Enable the Engineer?")
                .define("engineer", true, o -> o instanceof Boolean));
        this.LUMBERJACK= subscriber.subscribe(builder
                .comment("Enable the Lumberjack?")
                .define("lumberjack", true, o -> o instanceof Boolean));
        this.CONTRACTOR= subscriber.subscribe(builder
                .comment("Enable the Contractor?")
                .define("contractor", true, o -> o instanceof Boolean));
        this.DISC_JOCKEY= subscriber.subscribe(builder
                .comment("Enable the Disc Jockey?")
                .define("disc_jockey", true, o -> o instanceof Boolean));

        builder.pop();

        builder.push("Ores");

        this.FLINT_ORE= subscriber.subscribe(builder
                .comment("Enable Flint Ore?")
                .define("flint_ore", true, o -> o instanceof Boolean));
        this.SULFUR_ORE= subscriber.subscribe(builder
                .comment("Enable Sulfur Ore?")
                .define("sulfur_ore", true, o -> o instanceof Boolean));


        builder.pop();
        builder.push("Worldgen");

        this.PRISMARINE_IN_OCEANS= subscriber.subscribe(builder
                .comment("Enable Prismarine spawning in Ocean biomes?")
                .define("prismarine_oceans", true, o -> o instanceof Boolean));

        builder.pop();

        builder.push("Miscellaneous");

        this.CHARCOAL_BLOCK= subscriber.subscribe(builder
                .comment("Enable Charcoal Blocks?")
                .define("charcoal_block", true, o -> o instanceof Boolean));

        builder.push("Villager Breeding Foods");

        this.VILLAGER_EAT_PRODUCE= subscriber.subscribe(builder
                .comment("Enable Villagers eating Apples and Melons?")
                .define("villager_produce", true, o -> o instanceof Boolean));
        this.VILLAGER_EAT_BAKED_GOODS= subscriber.subscribe(builder
                .comment("Enable Villagers eating Baked Goods?")
                .define("villager_baked_goods", true, o -> o instanceof Boolean));
        this.VILLAGER_EAT_BAKED_GOODS= subscriber.subscribe(builder
                .comment("Enable Villagers being able to instantly breed when fed a golden food item?")
                .define("villager_golden_food", true, o -> o instanceof Boolean));

        builder.pop();

        builder.pop();

        builder.pop();
    }

}
