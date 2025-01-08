package invokermod.cards.basic;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import invokermod.cards.AbstractElementCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class Quas extends AbstractElementCard {
    public static final String ID = makeID("Quas");
    private static final int COST = 0;
    private static final int HEAL_AMOUNT = 1;

    public Quas() {
        super(ID, cardStrings.NAME, "images/cards/skill/defend_blue.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.BASIC, CardTarget.SELF, ElementManager.ElementType.QUAS);
        this.exhaust = true;
        this.baseMagicNumber = HEAL_AMOUNT;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    protected void onElementAdded() {
        AbstractPlayer p = AbstractDungeon.player;
        // 每个冰球会在你造成伤害时回复1点生命值
        addToBot(new HealAction(p, p, magicNumber));
    }
}
