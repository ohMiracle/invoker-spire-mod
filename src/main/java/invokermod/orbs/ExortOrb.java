package invokermod.orbs;

import basemod.abstracts.CustomOrb;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import invokermod.InvokerMod;
import invokermod.constant.ResourcePath;
import invokermod.util.ResourcePathUtil;

public class ExortOrb extends CustomOrb {
    public static final String ORB_ID = InvokerMod.makeID("ExortOrb");
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    private float vfxTimer = 0.5F;
    private static final float VFX_INTERVAL_TIME = 0.25F;
    private static final int STRENGTH_AMOUNT = 2;

    public ExortOrb() {
        super(ORB_ID, 
            orbStrings.NAME,
            STRENGTH_AMOUNT,  // Passive Amount - 提供力量加成
            0,               // Evoke Amount - 激发时不提供额外效果
            "火球被动",    
            "火球被动消失",  
            ResourcePathUtil.getPath(ResourcePath.IMAGE_ORBS, "exort.png")); // Passive Image
        this.updateDescription();
        
        // 当球被创建时，给予力量加成
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, this.passiveAmount), this.passiveAmount));
    }

    @Override
    public void updateDescription() {
        this.description = orbStrings.DESCRIPTION[0] + this.passiveAmount + orbStrings.DESCRIPTION[1];
    }

    @Override
    public void onEvoke() {
        // 当球被移除时，移除这个球提供的力量加成
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, -this.passiveAmount), -this.passiveAmount));
    }

    @Override
    public void applyFocus() {
        // 不受Focus影响
    }

    @Override
    public void onStartOfTurn() {
        // 不在回合开始时触发
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += MathUtils.PI / 45.0F;
        this.vfxTimer -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new DarkOrbActivateEffect(this.cX, this.cY));
            this.vfxTimer = VFX_INTERVAL_TIME;
        }
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.1F);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ExortOrb();
    }
}
