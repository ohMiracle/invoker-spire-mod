package invokermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class ExortPower extends AbstractElementPower {
    public static final String POWER_ID = makeID("ExortPower");

    public ExortPower(AbstractCreature owner, int amount) {
        super(POWER_ID, ElementManager.ElementType.EXORT, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount)));
    }

    @Override
    public void onRemove() {
        addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -amount)));
    }
}
