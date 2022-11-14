package me.cornholio.scrapeac.checks.world.badpackets;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPositionAndRotation;
import me.cornholio.scrapeac.checks.Category;
import me.cornholio.scrapeac.checks.Check;
import me.cornholio.scrapeac.checks.CheckData;
import org.bukkit.entity.Player;

@CheckData(name="BadPackets", type="B", category= Category.WORLD, description="badpackets.invalid")
public class BadPacketsB extends Check {

    public BadPacketsB(final Player player) {
        super(player);
    }

    private float lastYaw, lastPitch;

    @Override
    public void onPacketEvent(final PacketReceiveEvent event) {
        if(event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION
                || event.getPacketType() == PacketType.Play.Client.PLAYER_ROTATION) {

            final WrapperPlayClientPlayerPositionAndRotation wrapped = new WrapperPlayClientPlayerPositionAndRotation(event);

            // Impossible, likely C06 disabler
            if(lastYaw == wrapped.getYaw() && lastPitch == wrapped.getPitch()) {
                flag();
                event.setCancelled(true);
            }

            lastYaw = wrapped.getYaw();
            lastPitch = wrapped.getPitch();
        }
    }

}
