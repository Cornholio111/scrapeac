package me.cornholio.scrapeac.listener;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import me.cornholio.scrapeac.ScrapeAC;
import org.bukkit.entity.Player;

public class EventListener extends PacketListenerAbstract {

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        ScrapeAC.getInstance().getDataManager().getChecks((Player) event.getPlayer()).forEach(c -> {
            if(ScrapeAC.getInstance().getDataManager().getTime((Player) event.getPlayer()) > 120) {
                c.onPacketEvent(event);
            }
        });
    }

}
