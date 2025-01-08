package invokermod.cards.basic;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import invokermod.cards.AbstractElementCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class Wex extends AbstractElementCard {
    public static final String ID = makeID("Wex");
    private static final int COST = 0;
    private static final int DRAW = 1;

    public Wex() {
        super(ID, cardStrings.NAME, "images/cards/skill/defend_purple.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.BASIC, CardTarget.SELF, ElementManager.ElementType.WEX);
        this.exhaust = true;
        this.baseMagicNumber = DRAW;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    protected void onElementAdded() {
        // 每个雷球会让你打出一张牌后立即抽一张牌
        addToBot(new DrawCardAction(AbstractDungeon.player, magicNumber));
    }
}
