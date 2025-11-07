package edu.uc.it2045c.battleship;

public class GameStats {
    private int missStreak=0, strikeCount=0, totalMiss=0, totalHit=0;

    public void reset(){ missStreak=0; strikeCount=0; totalMiss=0; totalHit=0; }
    public void onHit(){ totalHit++; missStreak=0; }
    public void onMiss(){
        totalMiss++; missStreak++;
        if (missStreak >= 5){ strikeCount++; missStreak = 0; }
    }
    public int getMissStreak(){ return missStreak; }
    public int getStrikeCount(){ return strikeCount; }
    public int getTotalMiss(){ return totalMiss; }
    public int getTotalHit(){ return totalHit; }
}