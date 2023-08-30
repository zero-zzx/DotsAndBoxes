package com.zzx.apackage.view;

import java.util.Map;

import com.zzx.apackage.model.Player;

public interface PlayersStateView {
    public void setPlayerNow(Player player);
    public void setPlayerOccupyingBoxesCount(Map<Player,Integer> player_occupyingBoxesCount_map);
    public void playerTouched();
    public void setWinner(Player[] winner);
}
