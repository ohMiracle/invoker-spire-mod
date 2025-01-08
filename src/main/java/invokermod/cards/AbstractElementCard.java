package invokermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import invokermod.util.ElementManager;

public abstract class AbstractElementCard extends CustomCard {
    protected final ElementManager.ElementType elementType;
    protected final CardStrings cardStrings;

    public AbstractElementCard(String id, String name, String img, int cost, String rawDescription,
                             CardType type, CardColor color, CardRarity rarity, CardTarget target,
                             ElementManager.ElementType elementType) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.elementType = elementType;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ElementManager.getInstance().addElement(elementType);
        onElementAdded();
    }

    protected abstract void onElementAdded();

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
