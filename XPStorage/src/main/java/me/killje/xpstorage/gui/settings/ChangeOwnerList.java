package me.killje.xpstorage.gui.settings;

import java.util.UUID;
import me.killje.gui.InventoryUtils;
import me.killje.gui.guiElement.GuiElement;
import me.killje.util.GuiSettingsFromFile;
import me.killje.gui.list.PlayerList;
import me.killje.xpstorage.permission.Permissions;
import me.killje.xpstorage.xpsign.AbstractXpSign;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import me.killje.gui.list.PlayerListElementFetcher;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class ChangeOwnerList implements GuiElement, PlayerListElementFetcher {

    private final Player player;
    private final AbstractXpSign sign;

    public ChangeOwnerList(Player player, AbstractXpSign sign) {
        this.player = player;
        this.sign = sign;
    }

    @Override
    public ItemStack getItemStack() {
        return GuiSettingsFromFile.getItemStack("changeOwner");
    }

    @Override
    public void onInventoryClickEvent(InventoryUtils currentInventoryUtils, InventoryClickEvent event) {
        
        if (!player.hasPermission(Permissions.CHANGE_OWNER.getPermission())) {
            return;
        }
        
        InventoryUtils inventoryUtils = new PlayerList(player, this);
        
        currentInventoryUtils.openNewInventory(player, inventoryUtils);
        
    }

    @Override
    public GuiElement getGuiElement(UUID offlinePlayer) {
        return new ChangeOwner(offlinePlayer, sign);
    }
    
}
