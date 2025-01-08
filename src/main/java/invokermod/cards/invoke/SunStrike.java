package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class SunStrike extends AbstractInvokeCard {
    public static final String ID = makeID("SunStrike");
    private static final int COST = 1;
    private static final int DAMAGE = 20;
    private static final int UPGRADE_PLUS_DMG = 5;

    public SunStrike() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/attack/sun_strike.png",
                COST, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.ENEMY,
                ElementManager.ElementType.EXORT,
                ElementManager.ElementType.EXORT,
                ElementManager.ElementType.EXORT);
        
        this.baseDamage = DAMAGE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
