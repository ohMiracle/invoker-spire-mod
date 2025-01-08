package invokermod.util;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EnergyManager {
    private static EnergyManager instance;
    private static final int BASE_ENERGY = 3;
    private int bonusEnergy;
    
    private EnergyManager() {
        this.bonusEnergy = 0;
    }
    
    public static EnergyManager getInstance() {
        if (instance == null) {
            instance = new EnergyManager();
        }
        return instance;
    }
    
    public void startTurn() {
        // 根据元素组合提供额外能量
        ElementManager elementManager = ElementManager.getInstance();
        int quasCount = elementManager.countElement(ElementManager.ElementType.QUAS);
        int wexCount = elementManager.countElement(ElementManager.ElementType.WEX);
        int exortCount = elementManager.countElement(ElementManager.ElementType.EXORT);
        
        // 计算额外能量
        bonusEnergy = calculateBonusEnergy(quasCount, wexCount, exortCount);
        
        // 添加额外能量
        if (bonusEnergy > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(bonusEnergy));
        }
    }
    
    private int calculateBonusEnergy(int quas, int wex, int exort) {
        int bonus = 0;
        
        // 三相同元素：+2能量
        if (quas == 3 || wex == 3 || exort == 3) {
            bonus += 2;
        }
        // 两相同元素：+1能量
        else if (quas == 2 || wex == 2 || exort == 2) {
            bonus += 1;
        }
        
        return bonus;
    }
    
    public int getCurrentEnergy() {
        return EnergyPanel.totalCount;
    }
    
    public int getBonusEnergy() {
        return bonusEnergy;
    }
    
    public void reset() {
        bonusEnergy = 0;
    }
}
