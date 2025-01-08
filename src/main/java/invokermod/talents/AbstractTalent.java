package invokermod.talents;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;

public abstract class AbstractTalent {
    protected final String id;
    protected final UIStrings uiStrings;
    protected final String name;
    protected final String[] descriptions;
    protected final TalentType type;
    protected final TalentTree tree;
    protected int level;
    protected final int maxLevel;

    public enum TalentType {
        QUAS,    // Ice element talents
        WEX,     // Storm element talents
        EXORT,   // Fire element talents
        INVOKE   // General invoke talents
    }

    public AbstractTalent(String id, TalentType type, TalentTree tree, int maxLevel) {
        this.id = id;
        this.uiStrings = CardCrawlGame.languagePack.getUIString(id);
        this.name = uiStrings.TEXT[0];
        this.descriptions = new String[maxLevel];
        System.arraycopy(uiStrings.TEXT, 1, this.descriptions, 0, maxLevel);
        this.type = type;
        this.tree = tree;
        this.level = 0;
        this.maxLevel = maxLevel;
    }

    public boolean canLevel() {
        return level < maxLevel && tree.getAvailablePoints() > 0 && meetsRequirements();
    }

    protected abstract boolean meetsRequirements();

    public void levelUp() {
        if (canLevel()) {
            level++;
            tree.spendPoint();
            onLevelUp();
        }
    }

    protected abstract void onLevelUp();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return descriptions[Math.min(level, maxLevel - 1)];
    }

    public Texture getIcon() {
        // 临时使用游戏中已有的图标
        switch (type) {
            case QUAS:
                return ImageMaster.FROST_ORB_RIGHT;
            case WEX:
                return ImageMaster.LIGHTNING_ORB_RIGHT;
            case EXORT:
                return ImageMaster.DARK_ORB_RIGHT;
            case INVOKE:
                return ImageMaster.PLASMA_ORB_RIGHT;
            default:
                return ImageMaster.FROST_ORB_RIGHT;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public TalentType getType() {
        return type;
    }

    public void reset() {
        level = 0;
    }
}
