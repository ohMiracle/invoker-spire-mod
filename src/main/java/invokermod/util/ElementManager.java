package invokermod.util;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import invokermod.powers.ExortPower;
import invokermod.powers.QuasPower;
import invokermod.powers.WexPower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementManager {
    private static ElementManager instance;
    private static final int MAX_ELEMENTS = 3;
    private final List<ElementType> elements;

    public int getQuasCount() {
        return countElement(ElementType.QUAS);
    }

    public int getWexCount() {
        return countElement(ElementType.WEX);
    }
    public int getExortCount() {
        return countElement(ElementType.EXORT);
    }

    public enum ElementType {
        QUAS,
        WEX,
        EXORT
    }

    private ElementManager() {
        elements = new ArrayList<>();
    }

    public static ElementManager getInstance() {
        if (instance == null) {
            instance = new ElementManager();
        }
        return instance;
    }

    public void addElement(ElementType element) {
        if (elements.size() >= MAX_ELEMENTS) {
            removeElement(0);
        }
        elements.add(element);
        updatePowers();
    }

    public void removeElement(int index) {
        if (index >= 0 && index < elements.size()) {
            elements.remove(index);
            updatePowers();
        }
    }

    public void clearElements() {
        elements.clear();
        updatePowers();
    }

    public List<ElementType> getElements() {
        return new ArrayList<>(elements);
    }

    public boolean hasElements(ElementType... requiredElements) {
        List<ElementType> tempElements = new ArrayList<>(elements);
        for (ElementType required : requiredElements) {
            boolean found = false;
            for (int i = 0; i < tempElements.size(); i++) {
                if (tempElements.get(i) == required) {
                    tempElements.remove(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public int countElement(ElementType elementType) {
        int count = 0;
        for (ElementType element : elements) {
            if (element == elementType) {
                count++;
            }
        }
        return count;
    }

    private void updatePowers() {
        if (AbstractDungeon.player == null) return;

        // 移除所有元素Power
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof QuasPower || power instanceof WexPower || power instanceof ExortPower) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(
                    AbstractDungeon.player, AbstractDungeon.player, power));
            }
        }

        // 添加新的Power
        int quasCount = countElement(ElementType.QUAS);
        int wexCount = countElement(ElementType.WEX);
        int exortCount = countElement(ElementType.EXORT);

        if (quasCount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                new QuasPower(AbstractDungeon.player, quasCount)));
        }
        if (wexCount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                new WexPower(AbstractDungeon.player, wexCount)));
        }
        if (exortCount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                new ExortPower(AbstractDungeon.player, exortCount)));
        }
    }
}
