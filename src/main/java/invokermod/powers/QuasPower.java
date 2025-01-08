package invokermod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class QuasPower extends AbstractElementPower {
    public static final String POWER_ID = makeID("QuasPower");

    public QuasPower(AbstractCreature owner, int amount) {
        super(POWER_ID, ElementManager.ElementType.QUAS, owner, amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            flash();
            addToBot(new HealAction(owner, owner, amount));
        }
    }
}
