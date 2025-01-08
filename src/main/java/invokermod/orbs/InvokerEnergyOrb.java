package invokermod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import invokermod.util.ElementManager;

public class InvokerEnergyOrb extends AbstractOrb {
    private float angle1;
    private float angle2;
    private float angle3;
    private float angle4;
    private float angleVelocity1;
    private float angleVelocity2;
    private float angleVelocity3;
    private float angleVelocity4;
    
    private static final float ORB_WAVY_DIST = 0.04f;
    private static final float PI_4 = 12.566371f;
    
    public InvokerEnergyOrb() {
        this.angle1 = MathUtils.random(360.0f);
        this.angle2 = MathUtils.random(360.0f);
        this.angle3 = MathUtils.random(360.0f);
        this.angle4 = MathUtils.random(360.0f);
        this.angleVelocity1 = MathUtils.random(30.0f, 70.0f);
        this.angleVelocity2 = MathUtils.random(30.0f, 70.0f);
        this.angleVelocity3 = MathUtils.random(30.0f, 70.0f);
        this.angleVelocity4 = MathUtils.random(30.0f, 70.0f);
    }

    @Override
    public void updateDescription() {

    }

    @Override
    public void updateAnimation() {
        this.angle1 += Gdx.graphics.getDeltaTime() * this.angleVelocity1;
        this.angle2 += Gdx.graphics.getDeltaTime() * this.angleVelocity2;
        this.angle3 += Gdx.graphics.getDeltaTime() * this.angleVelocity3;
        this.angle4 += Gdx.graphics.getDeltaTime() * this.angleVelocity4;
    }
    
    @Override
    public void render(SpriteBatch sb) {
        float centerX = AbstractDungeon.player.drawX + AbstractDungeon.player.hb_x;
        float centerY = AbstractDungeon.player.drawY + AbstractDungeon.player.hb_y + AbstractDungeon.player.hb_h / 2.0f;
        
        // Base orb
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.ENERGY_BLUE_LAYER2,
                centerX - 64.0f, centerY - 64.0f,
                64.0f, 64.0f,
                128.0f, 128.0f,
                Settings.scale, Settings.scale,
                this.angle1,
                0, 0,
                128, 128,
                false, false);
        
        // Element-specific effects
        ElementManager elementManager = ElementManager.getInstance();
        if (elementManager.getQuasCount() > 0) {
            renderElementalEffect(sb, centerX, centerY, this.angle2, Color.BLUE);
        }
        if (elementManager.getWexCount() > 0) {
            renderElementalEffect(sb, centerX, centerY, this.angle3, Color.PURPLE);
        }
        if (elementManager.getExortCount() > 0) {
            renderElementalEffect(sb, centerX, centerY, this.angle4, Color.ORANGE);
        }
    }
    
    private void renderElementalEffect(SpriteBatch sb, float x, float y, float angle, Color color) {
        sb.setColor(color);
        sb.draw(ImageMaster.ENERGY_BLUE_LAYER1,
                x - 64.0f, y - 64.0f,
                64.0f, 64.0f,
                128.0f, 128.0f,
                Settings.scale * 1.2f, Settings.scale * 1.2f,
                angle,
                0, 0,
                128, 128,
                false, false);
    }
    
    @Override
    public void playChannelSFX() {
        // Play channel sound if needed
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new InvokerEnergyOrb();
    }

    @Override
    public void onEvoke() {
        // This orb is not meant to be evoked
    }
}
