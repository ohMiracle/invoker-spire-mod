package invokermod.cards.basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import invokermod.cards.AbstractElementCard;
import invokermod.util.ElementManager;

import static invokermod.InvokerMod.makeID;

public class Exort extends AbstractElementCard {
    public static final String ID = makeID("Exort");
    private static final int COST = 0;
    private static final int STRENGTH = 2;

    public Exort() {
        super(ID, cardStrings.NAME, "invokermodResources/images/cards/skill/exort.png",
                COST, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS,
                CardRarity.BASIC, CardTarget.SELF, ElementManager.ElementType.EXORT);
        this.exhaust = true;
        this.baseMagicNumber = STRENGTH;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    protected void onElementAdded() {
        AbstractPlayer p = AbstractDungeon.player;
        // 每个火球会使你增加2点力量
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
    }
}
