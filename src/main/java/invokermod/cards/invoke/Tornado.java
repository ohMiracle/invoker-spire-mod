package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class Tornado extends AbstractInvokeCard {
    public static final String ID = makeID("Tornado");
    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int HITS = 3;
    private static final int WEAK = 2;
    private static final int UPGRADE_PLUS_DMG = 2;

    public Tornado() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/attack/tornado.png",
                COST, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.ALL_ENEMY,
                ElementManager.ElementType.WEX,
                ElementManager.ElementType.WEX,
                ElementManager.ElementType.QUAS);
        
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = WEAK;
        this.magicNumber = this.baseMagicNumber;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            // 造成多段伤害
            for (int i = 0; i < HITS; i++) {
                addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
            
            // 施加虚弱
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
            upgradeDamage(UPGRADE_PLUS_DMG);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
