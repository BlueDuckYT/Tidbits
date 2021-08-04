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

        builder.pop();

        builder.pop();
    }

}
