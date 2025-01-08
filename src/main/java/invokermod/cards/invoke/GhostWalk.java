package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class GhostWalk extends AbstractInvokeCard {
    public static final String ID = makeID("GhostWalk");
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int DEXTERITY = 2;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int UPGRADE_PLUS_DEX = 1;

    public GhostWalk() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/skill/ghost_walk.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.SELF,
                ElementManager.ElementType.QUAS,
                ElementManager.ElementType.QUAS,
                ElementManager.ElementType.WEX);
        
        this.baseBlock = BLOCK;
        this.baseMagicNumber = DEXTERITY;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            // 获得格挡
            addToBot(new GainBlockAction(p, p, block));
            
            // 获得敏捷
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_DEX);
            initializeDescription();
        }
    }
}
