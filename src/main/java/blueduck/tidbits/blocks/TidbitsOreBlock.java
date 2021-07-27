package blueduck.tidbits.blocks;

import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class TidbitsOreBlock extends OreBlock {
    public TidbitsOreBlock(Properties p_i48357_1_) {
        super(p_i48357_1_);
    }

    public int xpOnDrop(Random p_220281_1_) {
        return MathHelper.nextInt(p_220281_1_, 1, 4);
    }
}
