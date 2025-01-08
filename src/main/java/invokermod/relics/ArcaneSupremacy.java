package invokermod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import invokermod.talents.AbstractTalent;
import invokermod.talents.TalentTree;

import static invokermod.InvokerMod.makeID;

public class ArcaneSupremacy extends AbstractTalentRelic {
    public static final String ID = makeID("ArcaneSupremacy");
    
    public ArcaneSupremacy() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
        this.talentType = AbstractTalent.TalentType.INVOKE;
    }
    
    @Override
    public void onEquip() {
        super.onEquip();
        // Increase maximum level of all talents
        TalentTree.getInstance().increaseAllMaxLevels(1);
    }
    
    @Override
    public void onUnequip() {
        super.onUnequip();
        // Decrease maximum level of all talents
        TalentTree.getInstance().decreaseAllMaxLevels(1);
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
