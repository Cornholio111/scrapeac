package me.cornholio.scrapeac.command;

import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import me.cornholio.scrapeac.ScrapeAC;
import me.cornholio.scrapeac.checks.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AlertsCommand implements CommandExecutor {

    private final DataManager data = ScrapeAC.getInstance().getDataManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            data.setAlerts((Player) sender, !data.getAlerts((Player) sender));
            final String message = ChatColor.translateAlternateColorCodes('&',
                    String.format("&8[&7ScrapeAC&8] &7Alerts are now %s", data.getAlerts((Player) sender) ? "enabled." : "disabled."));
            sender.sendMessage(message);
            return true;
        }
        return false;
    }

}
