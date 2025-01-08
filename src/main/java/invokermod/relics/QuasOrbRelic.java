package invokermod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import invokermod.talents.AbstractTalent;

import static invokermod.InvokerMod.makeID;

public class QuasOrbRelic extends AbstractTalentRelic {
    public static final String ID = makeID("QuasOrb");
    
    public QuasOrbRelic() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.talentType = AbstractTalent.TalentType.QUAS;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
