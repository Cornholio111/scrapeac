package me.cornholio.scrapeac.checks.world.scaffold;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import me.cornholio.scrapeac.checks.Category;
import me.cornholio.scrapeac.checks.Check;
import me.cornholio.scrapeac.checks.CheckData;
import org.bukkit.entity.Player;

@CheckData(name="Scaffold", type="A", category=Category.WORLD, description="placeBlock.scaffold")
public class ScaffoldA extends Check {

    public ScaffoldA(Player player) {
        super(player);
    }

    @Override
    public void onPacketEvent(PacketReceiveEvent event) {

        if(event.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {

            final WrapperPlayClientPlayerBlockPlacement wrapped = new WrapperPlayClientPlayerBlockPlacement(event);
            final Vector3f cursorPos = wrapped.getCursorPosition();

            // hitvec is not where placed.
            if(cursorPos.getX() < 0 || cursorPos.getX() > 1
                    || cursorPos.getY() < 0 || cursorPos.getY() > 1
                    || cursorPos.getZ() < 0 || cursorPos.getZ() > 1) {
                flag();
            }

        }

    }

}
