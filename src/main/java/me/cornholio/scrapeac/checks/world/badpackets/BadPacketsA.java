package me.cornholio.scrapeac.checks.world.badpackets;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import me.cornholio.scrapeac.checks.Category;
import me.cornholio.scrapeac.checks.Check;
import me.cornholio.scrapeac.checks.CheckData;
import org.bukkit.entity.Player;

@CheckData(name="BadPackets", type="A", category= Category.WORLD, description="badPackets.timer")
public class BadPacketsA extends Check {

    public BadPacketsA(Player player) {
        super(player);
    }

    private int packets, buffer;
    private long timer;

    @Override
    public void onPacketEvent(PacketReceiveEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION || event.getPacketType() == PacketType.Play.Client.PLAYER_FLYING) {
            ++this.packets;
            final long dif = System.nanoTime() - this.timer;
            if (dif >= 250000000) {
                final long value = (long)this.packets / (dif / 250000000);
                if (value > 5) {
                    if (value == 6 && ++this.buffer <= 12) {
                        timer = System.nanoTime();
                        packets = 0;
                        return;
                    }

                    flag();
                }

                buffer = Math.max(0, buffer - 6);
                timer = System.nanoTime();
                packets = 0;
            }

        }
    }

}
