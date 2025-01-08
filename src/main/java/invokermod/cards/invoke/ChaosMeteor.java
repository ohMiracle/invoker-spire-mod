package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class ChaosMeteor extends AbstractInvokeCard {
    public static final String ID = makeID("ChaosMeteor");
    private static final int COST = 2;
    private static final int DAMAGE = 24;
    private static final int AOE_DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 8;
    private static final int UPGRADE_PLUS_AOE = 4;

    public ChaosMeteor() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/attack/chaos_meteor.png",
                COST, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.ENEMY,
                ElementManager.ElementType.EXORT,
                ElementManager.ElementType.EXORT,
                ElementManager.ElementType.WEX);
        
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = AOE_DAMAGE;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            // 对主目标造成大量伤害
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.FIRE));
            
            // 对所有敌人造成溅射伤害
            int[] multiDamage = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
            for (int i = 0; i < multiDamage.length; i++) {
                multiDamage[i] = this.magicNumber;
            }
            
            addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_AOE);
            initializeDescription();
        }
    }
}
