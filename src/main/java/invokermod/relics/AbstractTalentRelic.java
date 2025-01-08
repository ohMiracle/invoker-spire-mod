package invokermod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import invokermod.talents.AbstractTalent;
import invokermod.talents.TalentTree;

public abstract class AbstractTalentRelic extends CustomRelic {
    protected AbstractTalent.TalentType talentType;
    
    public AbstractTalentRelic(String id, RelicTier tier, LandingSound sfx) {
        super(id, ImageMaster.loadImage("images/relics/frozenCore.png"),
                ImageMaster.loadImage("images/relics/outline/frozenCore.png"),
                tier, sfx);
        this.talentType = null;
    }
    
    @Override
    public void onEquip() {
        TalentTree.getInstance().addBonusPoints(1);
        if (talentType != null) {
            TalentTree.getInstance().addTypeBonus(talentType);
        }
    }
    
    @Override
    public void onUnequip() {
        TalentTree.getInstance().removeBonusPoints(1);
        if (talentType != null) {
            TalentTree.getInstance().removeTypeBonus(talentType);
        }
    }
    
    @Override
    public AbstractRelic makeCopy() {
        try {
            return getClass().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
