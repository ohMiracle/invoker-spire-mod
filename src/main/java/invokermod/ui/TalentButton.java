package invokermod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import invokermod.talents.AbstractTalent;

public class TalentButton {
    private static final float BUTTON_W = 128.0f;
    private static final float BUTTON_H = 128.0f;
    private static final float TITLE_OFFSET_Y = 50.0f * Settings.scale;
    private static final float LEVEL_OFFSET_Y = -40.0f * Settings.scale;
    
    private final AbstractTalent talent;
    private final Hitbox hitbox;
    private final float x;
    private final float y;
    private boolean isHovered;
    private boolean isPressed;
    
    private static final Color HOVER_GLOW_COLOR = new Color(1.0f, 1.0f, 1.0f, 0.25f);
    private static final Color LOCKED_COLOR = new Color(0.5f, 0.5f, 0.5f, 1.0f);
    private static final Color UNLOCKED_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    
    public TalentButton(AbstractTalent talent, float x, float y) {
        this.talent = talent;
        this.x = x;
        this.y = y;
        this.hitbox = new Hitbox(x, y, BUTTON_W * Settings.scale, BUTTON_H * Settings.scale);
        this.isHovered = false;
        this.isPressed = false;
    }
    
    public void update() {
        hitbox.update();
        
        isHovered = hitbox.hovered;
        boolean wasPressed = isPressed;
        isPressed = hitbox.clicked;
        
        if (wasPressed && !isPressed) {
            onClick();
        }
    }
    
    private void onClick() {
        if (canUnlock()) {
            talent.levelUp();
        }
    }
    
    public void render(SpriteBatch sb) {
        // Draw background
        Color bgColor = isUnlocked() ? UNLOCKED_COLOR : LOCKED_COLOR;
        sb.setColor(bgColor);
        sb.draw(ImageMaster.CARD_SKILL_BG_SILHOUETTE,
                x, y,
                BUTTON_W / 2.0f, BUTTON_H / 2.0f,
                BUTTON_W, BUTTON_H,
                Settings.scale, Settings.scale,
                0.0f,
                0, 0,
                (int)BUTTON_W, (int)BUTTON_H,
                false, false);
        
        // Draw icon
        if (talent.getIcon() != null) {
            sb.setColor(Color.WHITE);
            sb.draw(talent.getIcon(),
                    x + 32.0f * Settings.scale, y + 32.0f * Settings.scale,
                    64.0f * Settings.scale, 64.0f * Settings.scale);
        }
        
        // Draw hover glow
        if (isHovered && canUnlock()) {
            sb.setColor(HOVER_GLOW_COLOR);
            sb.draw(ImageMaster.CARD_SKILL_BG_SILHOUETTE,
                    x - 16.0f * Settings.scale, y - 16.0f * Settings.scale,
                    (BUTTON_W + 32.0f) / 2.0f, (BUTTON_H + 32.0f) / 2.0f,
                    BUTTON_W + 32.0f, BUTTON_H + 32.0f,
                    Settings.scale, Settings.scale,
                    0.0f,
                    0, 0,
                    (int)BUTTON_W, (int)BUTTON_H,
                    false, false);
        }
        
        // Draw title
        FontHelper.renderFontCentered(sb,
                FontHelper.cardTitleFont,
                talent.getName(),
                x + BUTTON_W / 2.0f * Settings.scale,
                y + BUTTON_H * Settings.scale + TITLE_OFFSET_Y,
                isUnlocked() ? Color.WHITE : Color.GRAY);
        
        // Draw level
        FontHelper.renderFontCentered(sb,
                FontHelper.cardTitleFont,
                talent.getLevel() + "/" + talent.getMaxLevel(),
                x + BUTTON_W / 2.0f * Settings.scale,
                y + LEVEL_OFFSET_Y,
                isUnlocked() ? Color.WHITE : Color.GRAY);
        
        hitbox.render(sb);
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getWidth() {
        return BUTTON_W * Settings.scale;
    }
    
    public float getHeight() {
        return BUTTON_H * Settings.scale;
    }
    
    public AbstractTalent getTalent() {
        return talent;
    }
    
    public boolean isUnlocked() {
        return talent.getLevel() > 0;
    }
    
    public boolean canUnlock() {
        return talent.canLevel();
    }
}
