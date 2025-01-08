package invokermod.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.InvokerMod;
import invokermod.characters.TheInvoker;

import static invokermod.InvokerMod.makeID;

public class Defend extends CustomCard {
    public static final String ID = makeID("Defend");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public Defend() {
        super(ID, cardStrings.NAME, "images/cards/skill/defend.png",
                COST, cardStrings.DESCRIPTION,
                CardType.SKILL, TheInvoker.Enums.INVOKER_BLUE,
                CardRarity.BASIC, CardTarget.SELF);
        
        this.baseBlock = BLOCK;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
