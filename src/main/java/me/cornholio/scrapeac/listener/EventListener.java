package me.cornholio.scrapeac.listener;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import me.cornholio.scrapeac.ScrapeAC;
import me.cornholio.scrapeac.checks.DataManager;
import org.bukkit.entity.Player;

public class EventListener extends PacketListenerAbstract {

    private final DataManager data = ScrapeAC.getInstance().getDataManager();

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        data.getChecks((Player) event.getPlayer()).forEach(c -> {
            if(data.getTime((Player) event.getPlayer()) > 120) {
                c.onPacketEvent(event);
            }
        });
    }

    @Override
    public void onPacketSend(final PacketSendEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getPacketType() == PacketType.Play.Server.DISCONNECT) {
            if (player != null) {
                data.removeChecks(player);
            }
        }
    }

}
