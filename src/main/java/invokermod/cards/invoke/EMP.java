package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class EMP extends AbstractInvokeCard {
    public static final String ID = makeID("EMP");
    private static final int COST = 1;
    private static final int ENERGY_PER_ENEMY = 1;
    private static final int UPGRADE_ENERGY = 1;

    public EMP() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/skill/emp.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.ALL_ENEMY,
                ElementManager.ElementType.WEX,
                ElementManager.ElementType.WEX,
                ElementManager.ElementType.WEX);
        
        this.baseMagicNumber = ENERGY_PER_ENEMY;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            int aliveEnemies = 0;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.isDying) {
                    aliveEnemies++;
                }
            }
            
            if (aliveEnemies > 0) {
                addToBot(new GainEnergyAction(aliveEnemies * magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_ENERGY);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
