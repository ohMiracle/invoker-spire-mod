package invokermod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import invokermod.talents.AbstractTalent;

import static invokermod.InvokerMod.makeID;

public class AghanimsScepter extends AbstractTalentRelic {
    public static final String ID = makeID("AghanimsScepter");
    
    public AghanimsScepter() {
        super(ID, RelicTier.RARE, LandingSound.HEAVY);
    }
    
    @Override
    public void onEquip() {
        TalentTree.getInstance().addBonusPoints(2);
    }
    
    @Override
    public void onUnequip() {
        TalentTree.getInstance().removeBonusPoints(2);
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
