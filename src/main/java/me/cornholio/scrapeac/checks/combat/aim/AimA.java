package me.cornholio.scrapeac.checks.combat.aim;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import me.cornholio.scrapeac.checks.Category;
import me.cornholio.scrapeac.checks.Check;
import me.cornholio.scrapeac.checks.CheckData;
import me.cornholio.scrapeac.util.MathUtil;
import me.cornholio.scrapeac.util.PacketUtil;
import org.bukkit.entity.Player;

@CheckData(name="Aim", type="A", category=Category.COMBAT, description="aim.gcd")
public class AimA extends Check {

    public AimA(final Player player) {
        super(player);
    }

    private float lastPitch = -999, lastDeltaPitch = -999;
    private double lastSensitivity = -999;
    private int invalid = 0;

    @Override
    public void onPacketEvent(final PacketReceiveEvent event) {

        if(PacketUtil.isRotating(event)) {

            final WrapperPlayClientPlayerFlying wrapper = new WrapperPlayClientPlayerFlying(event);

            if(lastPitch != -999) {

                final float deltaPitch = Math.abs(wrapper.getLocation().getPitch() - lastPitch);

                if(lastDeltaPitch != -999) {

                    // code usage start: https://www.spigotmc.org/threads/determining-a-players-sensitivity.468373/

                    final float gcd = (float) MathUtil.getGcd(deltaPitch, lastDeltaPitch);
                    final double sensitivityModifier = Math.cbrt(0.8333 * gcd);
                    final double sensitivityStepTwo = (1.666 * sensitivityModifier) - 0.3333;
                    final double finalSensitivity = sensitivityStepTwo * 200;

                    // code usage end

                    if(lastSensitivity != -999) {

                        if(finalSensitivity < 0) {

                            if(++invalid > 4) {

                                flag();

                                invalid = 2;

                            }

                        } else {
                            invalid = 0;
                        }

                    }

                    lastSensitivity = finalSensitivity;

                }

                lastDeltaPitch = deltaPitch;

            }

            lastPitch = wrapper.getLocation().getPitch();

        }

    }

    public int gcd(int a, int b) { return b==0 ? a : gcd(b, a%b); }

}
