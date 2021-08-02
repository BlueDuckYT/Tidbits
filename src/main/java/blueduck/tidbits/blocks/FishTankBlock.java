package blueduck.tidbits.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class FishTankBlock extends Block implements IWaterLoggable {

    public static final IntegerProperty NORTH = IntegerProperty.create("north", 0, 2);
    public static final IntegerProperty EAST = IntegerProperty.create("east", 0, 2);
    public static final IntegerProperty SOUTH = IntegerProperty.create("south", 0, 2);
    public static final IntegerProperty WEST = IntegerProperty.create("west", 0, 2);
    public static final IntegerProperty UP = IntegerProperty.create("up", 0, 2);
    public static final IntegerProperty DOWN = IntegerProperty.create("down", 0, 2);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape BOTTOM_AABB = Block.box(0F, 0F, 0F, 16F, 2F, 16F);
    private static final VoxelShape TOP_AABB = Block.box(0F, 15F, 0F, 16F, 16F, 16F);
    private static final VoxelShape NORTH_AABB = Block.box(0F, 0F, 0F, 16F, 16F, 1F);
    private static final VoxelShape SOUTH_AABB = Block.box(0F, 0F, 15F, 16F, 16F, 16F);
    private static final VoxelShape EAST_AABB = Block.box(15F, 0F, 0F, 16F, 16F, 16F);
    private static final VoxelShape WEST_AABB = Block.box(0F, 0F, 0F, 1F, 16F, 16F);

    public FishTankBlock(AbstractBlock.Properties p_i48357_1_) {
        super(p_i48357_1_);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(NORTH, Integer.valueOf(0))
                .setValue(EAST, Integer.valueOf(0))
                .setValue(SOUTH, Integer.valueOf(0))
                .setValue(WEST, Integer.valueOf(0))
                .setValue(UP, Integer.valueOf(0))
                .setValue(DOWN, Integer.valueOf(0))
                .setValue(WATERLOGGED, false)
        );
    }



    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, DOWN, UP, WATERLOGGED);
    }


    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockState blockstate = iblockreader.getBlockState(blockpos1);
        BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
        return super.getStateForPlacement(context).setValue(NORTH, Integer.valueOf(this.canFenceConnectTo(blockstate, false, Direction.SOUTH))).setValue(EAST, Integer.valueOf(this.canFenceConnectTo(blockstate1, false, Direction.WEST))).setValue(SOUTH, Integer.valueOf(this.canFenceConnectTo(blockstate2, false, Direction.NORTH))).setValue(WEST, Integer.valueOf(this.canFenceConnectTo(blockstate3, false, Direction.EAST))).setValue(WATERLOGGED, isWater(ifluidstate));
    }

    public int canFenceConnectTo(BlockState p_220111_1_, boolean p_220111_2_, Direction p_220111_3_) {
        return 1;
    }

    public boolean isWater(FluidState ifluidstate) {
        return ifluidstate.is(FluidTags.WATER) && ifluidstate.getOwnHeight() == 8;
    }


    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape shape1 = Block.box(0, 0, 0, 0, 0, 0);
            if (state.getValue(UP) == 0) {
                shape1 = VoxelShapes.join(shape1, TOP_AABB, IBooleanFunction.OR);
            }
            if (state.getValue(DOWN) == 0) {
                shape1 = VoxelShapes.join(shape1, BOTTOM_AABB, IBooleanFunction.OR);
            }
            if (state.getValue(NORTH) == 0) {
                shape1 = VoxelShapes.join(shape1, NORTH_AABB, IBooleanFunction.OR);
            }
            if (state.getValue(SOUTH) == 0) {
                shape1 = VoxelShapes.join(shape1, SOUTH_AABB, IBooleanFunction.OR);
            }
            if (state.getValue(WEST) == 0) {
                shape1 = VoxelShapes.join(shape1, WEST_AABB, IBooleanFunction.OR);
            }
            if (state.getValue(EAST) == 0) {
                shape1 = VoxelShapes.join(shape1, EAST_AABB, IBooleanFunction.OR);
            }

        return shape1;
    }
}
