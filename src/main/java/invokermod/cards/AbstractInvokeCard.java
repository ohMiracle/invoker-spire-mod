package invokermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import invokermod.util.ElementManager;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractInvokeCard extends CustomCard {
    protected final CardStrings cardStrings;
    protected final List<ElementManager.ElementType> requiredElements;

    public AbstractInvokeCard(String id, String name, String img, int cost, String rawDescription,
                            CardType type, CardColor color, CardRarity rarity, CardTarget target,
                            ElementManager.ElementType... elements) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.requiredElements = Arrays.asList(elements);
        initializeDescription();
    }

    protected boolean hasRequiredElements() {
        ElementManager elementManager = ElementManager.getInstance();
        return elementManager.hasElements(requiredElements.toArray(new ElementManager.ElementType[0]));
    }

    protected int countElement(ElementManager.ElementType elementType) {
        return ElementManager.getInstance().countElement(elementType);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
