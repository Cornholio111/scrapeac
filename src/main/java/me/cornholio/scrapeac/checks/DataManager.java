package me.cornholio.scrapeac.checks;

import me.cornholio.scrapeac.checks.world.badpackets.BadPacketsA;
import me.cornholio.scrapeac.checks.world.badpackets.BadPacketsB;
import me.cornholio.scrapeac.checks.world.scaffold.ScaffoldA;
import me.cornholio.scrapeac.checks.world.scaffold.ScaffoldB;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataManager {

    private final HashMap<Player, List<Check>> checkMap = new HashMap<>();
    private final HashMap<Player, Long> time = new HashMap<>();
    private final HashMap<Player, Boolean> alerts = new HashMap<>();

    public void addChecks(final Player player) {
        List<Check> checks = new ArrayList<>();

        checks.add(new ScaffoldA(player));

        checks.add(new ScaffoldB(player));


        checks.add(new BadPacketsA(player));

        checks.add(new BadPacketsB(player));

        checkMap.put(player, checks);

        time.put(player, System.currentTimeMillis());

    }

    public List<Check> getChecks(final Player player) {
        if(!checkMap.containsKey(player)) {
            addChecks(player);
        }
        return checkMap.get(player);
    }

    public int getTime(Player player) {
        return (int)(System.currentTimeMillis() - time.get(player)) / 50;
    }

    public boolean getAlerts(Player player) {
        if(!alerts.containsKey(player)) {
            alerts.put(player, false);
        }
        return alerts.get(player);
    }

    public void setAlerts(Player player, boolean value) {
        alerts.put(player, value);
    }

}
