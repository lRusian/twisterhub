package com.pepsidev.twisthub.queues;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import me.joeleoli.portal.shared.queue.Queue;
import me.signatured.ezqueueshared.QueueInfo;
import me.signatured.ezqueuespigot.EzQueueAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class QueueManager {
	
	public Queues getQueueSupport() {
		switch (ConfigFile.getConfig().getString("system.queue")) {
			case "EZQUEUE":
				return Queues.EZQUEUE;
			case "PORTAL":
				return Queues.PORTAL;
			default:
				return Queues.NONE;
		}
	}
	
	public boolean inQueue(Player player) {
		switch (this.getQueueSupport()) {
			case EZQUEUE:
				return QueueInfo.getQueueInfo(EzQueueAPI.getQueue(player)) != null;
			case PORTAL:
				return Queue.getByPlayer(player.getUniqueId()) != null;
			default:
				return false;
		}
	}
	
	public void sendPlayer(Player player, String server) {
		switch (this.getQueueSupport()) {
			case EZQUEUE:
				EzQueueAPI.addToQueue(player, server);
				break;
			case PORTAL:
				Bukkit.getServer().dispatchCommand(player, "joinqueue " + server);
				break;
			default:
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF(server);
				player.getPlayer().sendPluginMessage(Hub.getInstance(), "BungeeCord", out.toByteArray());
				break;
		}
	}
	
	public String getQueueIn(Player player) {
		switch (this.getQueueSupport()) {
			case EZQUEUE:
				return EzQueueAPI.getQueue(player);
			case PORTAL:
				return Queue.getByPlayer(player.getUniqueId()).getName();
			default:
				return "NoInQueue";
		}
	}
	
	public int getPosition(Player player) {
		switch (this.getQueueSupport()) {
			case EZQUEUE:
				return EzQueueAPI.getPosition(player);
			case PORTAL:
				return Queue.getByPlayer(player.getUniqueId()).getPosition(player.getUniqueId());
			default:
				return 0;
		}
	}
	
	public int getInQueue(String queue) {
		switch (this.getQueueSupport()) {
			case EZQUEUE:
				return QueueInfo.getQueueInfo(queue).getPlayersInQueue().size();
			case PORTAL:
				return Queue.getByName(queue).getPlayers().size();
			default:
				return 0;
		}
	}
}
