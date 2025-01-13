package invokermod.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.InvokerMod;
import invokermod.constant.ResourcePath;
import invokermod.orbs.ExortOrb;
import invokermod.util.ResourcePathUtil;

public class Exort extends CustomCard {
    public static final String ID = InvokerMod.makeID("Exort");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public Exort() {
        super(ID, NAME, ResourcePathUtil.getPath(ResourcePath.IMAGE_CARDS_INVOKER,"exort.png"), COST, DESCRIPTION, 
            CardType.SKILL, AbstractCard.CardColor.BLUE, CardRarity.BASIC, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChannelAction(new ExortOrb()));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = com.badlogic.gdx.graphics.Color.RED;
    }
}
