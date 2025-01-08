package invokermod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import invokermod.util.ElementManager;

public abstract class AbstractElementPower extends AbstractPower {
    protected final PowerStrings powerStrings;
    protected final ElementManager.ElementType elementType;

    public AbstractElementPower(String powerID, ElementManager.ElementType elementType, AbstractCreature owner, int amount) {
        this.ID = powerID;
        this.powerStrings = CardCrawlGame.languagePack.getPowerStrings(powerID);
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.amount = amount;
        this.elementType = elementType;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        
        // 加载图标
        String path = "invokermodResources/images/powers/" + elementType.name().toLowerCase() + ".png";
        this.region128 = new TextureAtlas.AtlasRegion(new TextureAtlas(path).findRegion("128"));
        this.region48 = new TextureAtlas.AtlasRegion(new TextureAtlas(path).findRegion("48"));
        
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public ElementManager.ElementType getElementType() {
        return elementType;
    }
}
