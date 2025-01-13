package invokermod.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import invokermod.InvokerMod;

public class QuasOrb extends AbstractOrb {
    public static final String ORB_ID = InvokerMod.makeID("QuasOrb");
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;
    private static final float ORB_BORDER_SCALE = 1.2f;
    
    private float vfxTimer = 0.0f;
    private static final float VFX_INTERVAL_TIME = 0.25f;

    public QuasOrb() {
        this.ID = ORB_ID;
        this.name = orbString.NAME;
        this.baseEvokeAmount = 5;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        
        this.updateDescription();
        this.channelAnimTimer = 0.5f;
        
        this.angle = 0.0f;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.passiveAmount + DESCRIPTIONS[1] + this.evokeAmount + DESCRIPTIONS[2];
    }

    @Override
    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(
            new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.evokeAmount));
    }

    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToTop(
            new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.passiveAmount));
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += MathUtils.PI * Settings.scale / 45.0f;
        
        this.vfxTimer -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = VFX_INTERVAL_TIME;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(0.2f, 0.6f, 1.0f, this.c.a / 2.0f));
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, 
                this.scale + MathUtils.sin(this.angle / MathUtils.PI / 4.0f) * 0.04f * Settings.scale, 
                this.scale, this.angle, 0, 0, 96, 96, false, false);
        
        sb.setColor(new Color(0.6f, 0.8f, 1.0f, this.c.a / 2.0f));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0f, this.cY - 48.0f + this.bobEffect.y, 48.0f, 48.0f, 96.0f, 96.0f, 
                this.scale * ORB_BORDER_SCALE, this.scale * ORB_BORDER_SCALE, this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new QuasOrb();
    }
}