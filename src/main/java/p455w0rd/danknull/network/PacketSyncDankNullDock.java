package p455w0rd.danknull.network;

import javax.annotation.Nonnull;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.Side;
import p455w0rd.danknull.blocks.tiles.TileDankNullDock;
import p455w0rdslib.util.EasyMappings;

/**
 * @author p455w0rd
 *
 */
@Deprecated
public class PacketSyncDankNullDock implements IMessage {

	private BlockPos dockPos;
	private ItemStack dankNull;
	private int[] stackSizes;

	@Override
	public void fromBytes(final ByteBuf buf) {
		dockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		dankNull = ByteBufUtils.readItemStack(buf);
		stackSizes = new int[buf.readInt()];
		for (int i = 0; i < stackSizes.length - 1; i++) {
			stackSizes[i] = buf.readInt();
		}
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		buf.writeInt(dockPos.getX());
		buf.writeInt(dockPos.getY());
		buf.writeInt(dockPos.getZ());
		ByteBufUtils.writeItemStack(buf, dankNull);
//		final InventoryDankNull inv = DankNullUtils.getNewDankNullInventory(dankNull);
//		buf.writeInt(inv.getSizeInventory());
//		for (int i = 0; i < inv.getSizeInventory(); i++) {
//			buf.writeInt(inv.getSizeForSlot(i));
//		}
	}

	public PacketSyncDankNullDock() {
	}

	public PacketSyncDankNullDock(@Nonnull final TileDankNullDock dockingStation, final ItemStack dankNull) {
		dockPos = dockingStation.getPos();
		this.dankNull = dankNull;
	}

	public static class Handler implements IMessageHandler<PacketSyncDankNullDock, IMessage> {
		@Override
		public IMessage onMessage(final PacketSyncDankNullDock message, final MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
				handle(message, ctx.side == Side.SERVER ? ctx.getServerHandler().player : EasyMappings.player(), ctx.side);
			});
			return null;
		}

		private void handle(final PacketSyncDankNullDock message, final EntityPlayer player, final Side side) {
			final World world = player.getEntityWorld();
			if (world != null && world.getTileEntity(message.dockPos) != null && world.getTileEntity(message.dockPos) instanceof TileDankNullDock) {
				final TileDankNullDock dankDock = (TileDankNullDock) world.getTileEntity(message.dockPos);
				final ItemStack dankNull = message.dankNull;
				dankDock.setDankNull(dankNull);
				dankDock.markDirty();
				final IBlockState s = world.getBlockState(dankDock.getPos());
				world.notifyBlockUpdate(dankDock.getPos(), s, s, 3);
			}
		}
	}

}