package invokermod.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.cards.invoke.*;
import invokermod.util.ElementManager;

import java.util.List;

import static invokermod.InvokerMod.makeID;

public class Invoke extends CustomCard {
    public static final String ID = makeID("Invoke");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final int COST = 1;

    public Invoke() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/skill/invoke.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.BASIC, CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ElementManager elementManager = ElementManager.getInstance();
        List<ElementManager.ElementType> elements = elementManager.getElements();

        if (elements.size() >= 3) {
            AbstractCard spellCard = null;

            // Check for triple combinations
            if (countElement(ElementManager.ElementType.QUAS) == 3) {
                spellCard = new ColdSnap();
            } else if (countElement(ElementManager.ElementType.WEX) == 3) {
                spellCard = new EMP();
            } else if (countElement(ElementManager.ElementType.EXORT) == 3) {
                spellCard = new SunStrike();
            }
            // Check for double combinations
            else if (countElement(ElementManager.ElementType.QUAS) >= 2 && countElement(ElementManager.ElementType.WEX) >= 1) {
                spellCard = new GhostWalk();
            } else if (countElement(ElementManager.ElementType.QUAS) >= 2 && countElement(ElementManager.ElementType.EXORT) >= 1) {
                spellCard = new IceWall();
            } else if (countElement(ElementManager.ElementType.WEX) >= 2 && countElement(ElementManager.ElementType.QUAS) >= 1) {
                spellCard = new Tornado();
            } else if (countElement(ElementManager.ElementType.WEX) >= 2 && countElement(ElementManager.ElementType.EXORT) >= 1) {
                spellCard = new Alacrity();
            } else if (countElement(ElementManager.ElementType.EXORT) >= 2 && countElement(ElementManager.ElementType.QUAS) >= 1) {
                spellCard = new ForgeSpirit();
            } else if (countElement(ElementManager.ElementType.EXORT) >= 2 && countElement(ElementManager.ElementType.WEX) >= 1) {
                spellCard = new ChaosMeteor();
            }

            if (spellCard != null) {
                addToBot(new MakeTempCardInHandAction(spellCard, 1));
                elementManager.clearElements();
            }
        }
    }

    private int countElement(ElementManager.ElementType elementType) {
        return ElementManager.getInstance().countElement(elementType);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
