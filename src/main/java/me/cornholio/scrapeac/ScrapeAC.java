package me.cornholio.scrapeac;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.util.TimeStampMode;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import me.cornholio.scrapeac.checks.DataManager;
import me.cornholio.scrapeac.command.AlertsCommand;
import me.cornholio.scrapeac.listener.EventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ScrapeAC extends JavaPlugin {

    @Getter
    private static ScrapeAC instance;
    @Getter
    private DataManager dataManager;

    @Override
    public void onLoad(){
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {

        PacketEvents.getAPI().getSettings().debug(false).bStats(false)
                .checkForUpdates(true).timeStampMode(TimeStampMode.MILLIS).readOnlyListeners(false);
        PacketEvents.getAPI().init();

        instance = this;
        dataManager = new DataManager();

        final EventListener eventListener = new EventListener();
        PacketEvents.getAPI().getEventManager().registerListener(eventListener);

        Objects.requireNonNull(this.getServer().getPluginCommand("salerts")).setExecutor(new AlertsCommand());

    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
