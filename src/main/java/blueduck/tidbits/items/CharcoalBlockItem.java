package blueduck.tidbits.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class CharcoalBlockItem extends BlockItem {
    public CharcoalBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }

    public int getBurnTime(ItemStack itemStack)
    {
        return 16000;
    }
}
