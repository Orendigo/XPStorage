package me.killje.xpstorage.gui.editplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.killje.spigotgui.guielement.Exit;
import me.killje.spigotgui.guielement.GuiElement;
import me.killje.spigotgui.guielement.SimpleGuiElement;
import me.killje.spigotgui.util.InventoryBase;
import me.killje.xpstorage.XPStorage;
import me.killje.xpstorage.group.GroupRights;
import me.killje.xpstorage.util.PlayerInformation;
import me.killje.xpstorage.xpsign.AbstractGroupSign;
import org.bukkit.Bukkit;

/**
 *
 * @author Patrick Beuks (killje) <patrick.beuks@gmail.com>
 */
public class EditPlayerOptions extends InventoryBase {

    private final AbstractGroupSign sign;
    private final UUID playerToEdit;
    private final UUID playerEditing;

    public EditPlayerOptions(AbstractGroupSign sign, UUID playerToEdit, UUID playerEditing) {
        super(XPStorage.getGuiSettings());
        this.sign = sign;
        this.playerToEdit = playerToEdit;
        this.playerEditing = playerEditing;
    }

    protected ArrayList<GuiElement> getElements() {
        ArrayList<GuiElement> guiElements = new ArrayList<>();

        if (playerToEdit.equals(sign.getOwner())) {
            this.addGuiElement(new SimpleGuiElement(getGuiSettings().getItemStack("selected.owner")));
        } else if (playerToEdit.equals(playerEditing)) {
            this.addGuiElement(new SimpleGuiElement(getGuiSettings().getItemStack("selected.yourself")));
        } else {
            PlayerInformation playerInformationPlayerEditing = PlayerInformation.getPlayerInformation(playerEditing);
            if (playerEditing.equals(sign.getOwner())) {
                this.addGuiElement(new SetNewOwner(playerToEdit, sign));
            }
            if (playerEditing.equals(sign.getOwner())
                    || (playerInformationPlayerEditing != null
                    && playerInformationPlayerEditing.getGroupRights(sign.getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_EDIT_PLAYERS))) {
                if (PlayerInformation.getPlayerInformation(playerToEdit).getGroupRights(sign.getGroup().getGroupUuid()).hasRight(GroupRights.Right.CAN_EDIT_PLAYERS)) {
                    this.addGuiElement(new RemovePlayerEditRights(playerToEdit, sign));
                } else {
                    this.addGuiElement(new AddPlayerEditRights(playerToEdit, sign));
                }
            }

        }

        return guiElements;
    }

    @Override
    protected void initInventory() {

        ArrayList<GuiElement> guiElements = getElements();

        if (guiElements != null && guiElements.size() > 0) {
            for (GuiElement guiElement : guiElements) {
                this.addGuiElement(guiElement);
            }
        }

        if (!playerToEdit.equals(sign.getOwner()) && !playerToEdit.equals(playerEditing)) {
            this.addGuiElement(new RemovePlayer(playerToEdit, sign));
        }

        this.addGuiElement(new Exit(), 8);

        Map<String, String> replaceList = new HashMap<>();

        replaceList.put("PLAYER_NAME", Bukkit.getOfflinePlayer(playerToEdit).getName());

        this.setInventoryName(getGuiSettings().getText("editPlayer", replaceList));

    }

}