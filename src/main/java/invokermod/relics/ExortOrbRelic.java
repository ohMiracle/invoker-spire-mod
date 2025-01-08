package invokermod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import invokermod.talents.AbstractTalent;

import static invokermod.InvokerMod.makeID;

public class ExortOrbRelic extends AbstractTalentRelic {
    public static final String ID = makeID("ExortOrb");
    
    public ExortOrbRelic() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.talentType = AbstractTalent.TalentType.EXORT;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
