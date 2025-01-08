package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class ColdSnap extends AbstractInvokeCard {
    public static final String ID = makeID("ColdSnap");
    private static final int COST = 1;
    private static final int WEAK = 2;
    private static final int UPGRADE_PLUS_WEAK = 1;

    public ColdSnap() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/skill/cold_snap.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.ALL_ENEMY,
                ElementManager.ElementType.QUAS,
                ElementManager.ElementType.QUAS,
                ElementManager.ElementType.QUAS);
        
        this.baseMagicNumber = WEAK;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.isDying) {
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false)));
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_WEAK);
            initializeDescription();
        }
    }
}
