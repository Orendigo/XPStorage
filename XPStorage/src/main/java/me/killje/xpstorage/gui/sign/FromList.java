package me.killje.xpstorage.gui.sign;

import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.list.PlayerList;
import me.killje.spigotgui.list.PlayerListElementFetcher;
import me.killje.spigotgui.util.GuiSetting;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.gui.addplayer.AddPlayer;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class FromList implements GuiElement, PlayerListElementFetcher {

    private final AbstractGroupSign sign;

    public FromList(AbstractGroupSign sign) {
        this.sign = sign;
    }

    @Override
    public ItemStack getItemStack(GuiSetting guiSettings) {
        return guiSettings.getItemStack("fromList");
    }

    @Override
    public void onInventoryClickEvent(InventoryBase currentinventoryBase, InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        PlayerList playerList = new PlayerList(currentinventoryBase.getGuiSettings(), player, this);
        currentinventoryBase.openNewInventory(player, playerList);

    }

    @Override
    public GuiElement getGuiElement(OfflinePlayer offlinePlayer) {
        return new AddPlayer(offlinePlayer, sign);
    }

}