package invokermod.cards.invoke;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.cards.AbstractInvokeCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class IceWall extends AbstractInvokeCard {
    public static final String ID = makeID("IceWall");
    private static final int COST = 1;
    private static final int BLOCK = 15;
    private static final int UPGRADE_PLUS_BLOCK = 5;

    public IceWall() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/skill/ice_wall.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.SELF,
                ElementManager.ElementType.QUAS,
                ElementManager.ElementType.QUAS,
                ElementManager.ElementType.EXORT);
        
        this.baseBlock = BLOCK;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (hasRequiredElements()) {
            // 获得大量格挡
            addToBot(new GainBlockAction(p, p, block));
            
            // 如果玩家有力量，获得额外格挡
            if (p.hasPower("Strength")) {
                int strengthAmount = p.getPower("Strength").amount;
                if (strengthAmount > 0) {
                    addToBot(new GainBlockAction(p, p, strengthAmount * 2));
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
