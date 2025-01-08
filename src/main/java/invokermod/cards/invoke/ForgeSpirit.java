package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class ForgeSpirit extends AbstractInvokeCard {
    public static final String ID = makeID("ForgeSpirit");
    private static final int COST = 1;
    private static final int DAMAGE = 12;
    private static final int VULNERABLE = 2;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int UPGRADE_PLUS_VULNERABLE = 1;

    public ForgeSpirit() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/attack/forge_spirit.png",
                COST, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.ENEMY,
                ElementManager.ElementType.EXORT,
                ElementManager.ElementType.EXORT,
                ElementManager.ElementType.QUAS);
        
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = VULNERABLE;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            // 造成伤害
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.FIRE));
            
            // 施加易伤
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
            
            // 如果目标有格挡，造成额外伤害
            if (m.currentBlock > 0) {
                addToBot(new DamageAction(m, new DamageInfo(p, damage / 2, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE);
            initializeDescription();
        }
    }
}
