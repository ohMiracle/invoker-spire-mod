package invokermod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class WexPower extends AbstractElementPower {
    public static final String POWER_ID = makeID("WexPower");

    public WexPower(AbstractCreature owner, int amount) {
        super(POWER_ID, ElementManager.ElementType.WEX, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractCreature target) {
        flash();
        addToBot(new DrawCardAction(owner, amount));
    }
}
