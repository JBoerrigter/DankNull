package p455w0rd.danknull.init;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import p455w0rd.danknull.network.*;

/**
 * @author p455w0rd
 *
 */
public class ModNetworking {

	private static int packetId = 0;
	private static SimpleNetworkWrapper INSTANCE = null;

	private static int nextID() {
		return packetId++;
	}

	public static SimpleNetworkWrapper getInstance() {
		if (INSTANCE == null) {
			INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModGlobals.MODID);
		}
		return INSTANCE;
	}

	public static void registerMessages() {
		getInstance().registerMessage(PacketChangeMode.Handler.class, PacketChangeMode.class, nextID(), Side.SERVER);
		getInstance().registerMessage(PacketConfigSync.Handler.class, PacketConfigSync.class, nextID(), Side.CLIENT);
		getInstance().registerMessage(PacketEmptyDock.Handler.class, PacketEmptyDock.class, nextID(), Side.CLIENT);
		getInstance().registerMessage(PacketSetDankNullInDock.Handler.class, PacketSetDankNullInDock.class, nextID(), Side.CLIENT);
		getInstance().registerMessage(PacketUpdateSlot.Handler.class, PacketUpdateSlot.class, nextID(), Side.CLIENT);
	}
}
