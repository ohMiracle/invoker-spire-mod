package invokermod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import invokermod.talents.AbstractTalent;

import static invokermod.InvokerMod.makeID;

public class WexOrbRelic extends AbstractTalentRelic {
    public static final String ID = makeID("WexOrb");
    
    public WexOrbRelic() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.talentType = AbstractTalent.TalentType.WEX;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
