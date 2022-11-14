package me.cornholio.scrapeac.checks;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import me.cornholio.scrapeac.ScrapeAC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Locale;

public class Check {

    public final String name, description, type;
    public final Category category;
    protected final Player player;
    private final DataManager data = ScrapeAC.getInstance().getDataManager();

    public Check(final Player player) {
        if (getClass().isAnnotationPresent(CheckData.class)) {
            final CheckData data = getClass().getAnnotation(CheckData.class);
            this.name = data.name();
            this.category = data.category();
            this.player = player;
            this.description = data.description();
            this.type = data.type();
        } else {
            throw new IllegalStateException("@CheckData not found!");
        }
    }

    protected void flag() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(data.getAlerts(p)) {
                if(!p.hasPermission("scrapeac.notify")) {
                    data.setAlerts(p, false);
                    return;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        String.format("&7[&8ScrapeAC&7] failed %s.%s (Type %s)", category.name().toLowerCase(Locale.ROOT), description, type)));
            }
        });
    }

    public void onPacketEvent(final PacketReceiveEvent event) { }

}
