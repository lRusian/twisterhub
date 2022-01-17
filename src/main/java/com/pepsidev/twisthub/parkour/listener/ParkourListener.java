package com.pepsidev.twisthub.parkour.listener;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ParkourListener implements Listener {

	@EventHandler
	public void onParkourActivationLand(PlayerInteractEvent event) {
		String activationMat = Hub.getInstance().getConfig().getString("Parkour.Start-Material");
		String checkPointMat = Hub.getInstance().getConfig().getString("Parkour.CheckPoint-Material");
		boolean enabled = Hub.getInstance().getConfig().getBoolean("Parkour.Enabled");
		if (event.getAction() == Action.PHYSICAL) {
			if (event.getClickedBlock() == null)
				return;
			if (event.getClickedBlock().getType() == Material.valueOf(checkPointMat)) {
				if (Hub.getInstance().getParkourManager().clickedPlate.contains(event.getPlayer()))
					return;
				Hub.getInstance().getParkourManager().saveCheckPoint(event.getPlayer());
				Hub.getInstance().getParkourManager().clickedPlate.add(event.getPlayer());
				new BukkitRunnable() {
					@Override
					public void run() {
						Hub.getInstance().getParkourManager().clickedPlate.remove(event.getPlayer());
					}
				}.runTaskLater(Hub.getInstance(), 20 * 6);
			}
			if (event.getClickedBlock().getType() == Material.valueOf(activationMat)) {
				if (!Hub.getInstance().getParkourManager().parkour.contains(event.getPlayer())) {
					double x = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.X");
					double y = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.Y");
					double z = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.Z");
					float pitch = Hub.getInstance().getConfig().getInt("Parkour.Start-Location.Pitch");
					float yaw = Hub.getInstance().getConfig().getInt("Parkour.Start-Location.Yaw");
					Location location = new Location(event.getPlayer().getWorld(), x, y, z);
					location.setPitch(pitch);
					location.setYaw(yaw);
					Hub.getInstance().getParkourManager().activateParkour(event.getPlayer(), location);
					Hub.getInstance().getParkourManager().clickedPlate.add(event.getPlayer());
					new BukkitRunnable() {
						@Override
						public void run() {
							Hub.getInstance().getParkourManager().clickedPlate.remove(event.getPlayer());
						}
					}.runTaskLater(Hub.getInstance(), 20 * 6);
				}
			}
		}
	}

	@EventHandler
	public void onInteractFinishSign(PlayerInteractEvent event) {

		Block sign = event.getClickedBlock();

		if (sign == null)
			return;

		if (sign.getState() instanceof Sign) {
			if (Hub.getInstance().getParkourManager().parkour.contains(event.getPlayer())) {
				String line1 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-1");
				String line2 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-2");
				String line3 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-3");
				String line4 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-4");
				Sign block = (Sign) sign.getState();
				if (block.getLine(0).equalsIgnoreCase(CC.translate(line1))) {
					if (block.getLine(1).equalsIgnoreCase(CC.translate(line2))) {
						if (block.getLine(2).equalsIgnoreCase(CC.translate(line3))) {
							if (block.getLine(3).equalsIgnoreCase(CC.translate(line4))) {

								Hub.getInstance().getParkourManager().finishParkour(event.getPlayer());

							}
						}
					}
				}

			}
		}

	}

	@EventHandler
	public void onChange(SignChangeEvent event) {

		if (event.getLine(0).equalsIgnoreCase("[finish]")) {

			String line1 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-1");
			String line2 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-2");
			String line3 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-3");
			String line4 = Hub.getInstance().getConfig().getString("Parkour.Finish-Sign.Line-4");
			event.setLine(0, CC.translate(line1));
			event.setLine(1, CC.translate(line2));
			event.setLine(2, CC.translate(line3));
			event.setLine(3, CC.translate(line4));

		}

	}

}
