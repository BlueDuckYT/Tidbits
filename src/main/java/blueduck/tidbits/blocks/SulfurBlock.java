package blueduck.tidbits.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class SulfurBlock extends Block {

    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    public SulfurBlock(Properties p_i48357_1_) {
        super(p_i48357_1_);
    }

    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable net.minecraft.util.Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos, igniter);
    }

    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    private static void explode(World p_196535_0_, BlockPos pos, @Nullable LivingEntity p_196535_2_) {
        if (!p_196535_0_.isClientSide) {
            p_196535_0_.explode(p_196535_2_, pos.getX(), pos.getY((long) 0.0625), pos.getZ(), 3.0F, Explosion.Mode.BREAK);
        }
        else {
            p_196535_0_.addParticle(ParticleTypes.SMOKE, pos.getX(), pos.getY() + 0.5D, pos.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        ItemStack itemstack = p_225533_4_.getItemInHand(p_225533_5_);
        Item item = itemstack.getItem();
        if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
            return super.use(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
        } else {
            catchFire(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_6_.getDirection(), p_225533_4_);
            p_225533_2_.setBlock(p_225533_3_, Blocks.AIR.defaultBlockState(), 11);
            if (!p_225533_4_.isCreative()) {
                if (item == Items.FLINT_AND_STEEL) {
                    itemstack.hurtAndBreak(1, p_225533_4_, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(p_225533_5_);
                    });
                } else {
                    itemstack.shrink(1);
                }
            }

            return ActionResultType.sidedSuccess(p_225533_2_.isClientSide);
        }
    }

    public void onProjectileHit(World p_220066_1_, BlockState p_220066_2_, BlockRayTraceResult p_220066_3_, ProjectileEntity p_220066_4_) {
        if (!p_220066_1_.isClientSide) {
            Entity entity = p_220066_4_.getOwner();
            if (p_220066_4_.isOnFire()) {
                BlockPos blockpos = p_220066_3_.getBlockPos();
                catchFire(p_220066_2_, p_220066_1_, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
                p_220066_1_.removeBlock(blockpos, false);
            }
        }

    }

    public boolean dropFromExplosion(Explosion p_149659_1_) {
        return false;
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(UNSTABLE);
    }

}
