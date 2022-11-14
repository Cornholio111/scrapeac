package me.cornholio.scrapeac.checks.world.badpackets;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import me.cornholio.scrapeac.checks.Category;
import me.cornholio.scrapeac.checks.Check;
import me.cornholio.scrapeac.checks.CheckData;
import me.cornholio.scrapeac.util.PacketUtil;
import org.bukkit.entity.Player;

@CheckData(name="BadPackets", type="A", category= Category.WORLD, description="badpackets.timer")
public class BadPacketsA extends Check {

    public BadPacketsA(final Player player) {
        super(player);
    }

    private int packets, buffer;
    private long timer;

    @Override
    public void onPacketEvent(final PacketReceiveEvent event) {
        if(PacketUtil.isFlying(event)) {
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
                    event.setCancelled(true);
                }

                buffer = Math.max(0, buffer - 6);
                timer = System.nanoTime();
                packets = 0;
            }

        }
    }

}
