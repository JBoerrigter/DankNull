package p455w0rd.danknull.integration;

import com.google.common.collect.Lists;
import p455w0rdslib.LibGlobals.Mods;

/**
 * @author p455w0rd
 */
public class ItemScroller {

    public static void blackListSlots() {
        if (Mods.ITEMSCROLLER.isLoaded()) {
            fi.dy.masa.itemscroller.config.Configs.SLOT_BLACKLIST.addAll(Lists.newArrayList("p455w0rd.danknull.inventory.slot.SlotDankNull", "p455w0rd.danknull.inventory.slot.SlotDankNullDock"));
        }
    }

}
