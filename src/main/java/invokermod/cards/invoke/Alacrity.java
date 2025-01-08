package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class Alacrity extends AbstractInvokeCard {
    public static final String ID = makeID("Alacrity");
    private static final int COST = 1;
    private static final int DRAW = 2;
    private static final int STATS = 1;
    private static final int UPGRADE_PLUS_DRAW = 1;
    private static final int UPGRADE_PLUS_STATS = 1;

    public Alacrity() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/skill/alacrity.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.SELF,
                ElementManager.ElementType.WEX,
                ElementManager.ElementType.WEX,
                ElementManager.ElementType.EXORT);
        
        this.baseMagicNumber = DRAW;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            // 抽牌
            addToBot(new DrawCardAction(p, magicNumber));
            
            // 获得力量和敏捷
            int statsAmount = upgraded ? STATS + UPGRADE_PLUS_STATS : STATS;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, statsAmount)));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, statsAmount)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DRAW);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
