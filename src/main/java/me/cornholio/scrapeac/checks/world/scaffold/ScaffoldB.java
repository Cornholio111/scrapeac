package me.cornholio.scrapeac.checks.world.scaffold;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.BlockFace;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import me.cornholio.scrapeac.checks.Category;
import me.cornholio.scrapeac.checks.Check;
import me.cornholio.scrapeac.checks.CheckData;
import org.bukkit.entity.Player;

@CheckData(name="Scaffold", type="B", category= Category.WORLD, description="placeBlock.scaffold")
public class ScaffoldB extends Check {

    public ScaffoldB(Player player) {
        super(player);
    }

    private int buffer;
    private float lastX, lastY, lastZ;
    private long lastPlace;

    @Override
    public void onPacketEvent(PacketReceiveEvent event) {

        if(event.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {

            final WrapperPlayClientPlayerBlockPlacement wrapped = new WrapperPlayClientPlayerBlockPlacement(event);
            final Vector3f cursorPos = wrapped.getCursorPosition();

            // Repetitive hitvec
            if(cursorPos.getX() == lastX
                    && cursorPos.getY() == lastY
                    && cursorPos.getZ() == lastZ
                    && wrapped.getFace() != BlockFace.UP // Jump up
                    && wrapped.getFace() != BlockFace.DOWN // Below and place
                    && System.currentTimeMillis() - lastPlace <= 400) { // Prevent them from place block, break, place again same angle. only scaffold

                if(++buffer >= 3) {
                    flag();
                    if(++buffer >= 6) {
                        event.setCancelled(true);
                    }
                }

            } else buffer -= buffer > 0 ? 1 : 0;

            lastX = cursorPos.getX();
            lastY = cursorPos.getY();
            lastZ = cursorPos.getZ();
            lastPlace = System.currentTimeMillis();

        }

    }

}
