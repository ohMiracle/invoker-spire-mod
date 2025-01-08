package invokermod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import invokermod.talents.AbstractTalent;

public class TalentConnection {
    private final TalentButton source;
    private final TalentButton target;
    private final Color color;
    private final TextureRegion lineTexture;
    private final float thickness;
    private final float glowAmount;
    
    private static final float CONTROL_POINT_OFFSET = 100.0f * Settings.scale;
    private static final int SEGMENTS = 20;
    private final Vector2[] points;
    
    public TalentConnection(TalentButton source, TalentButton target, Color color, Texture lineTexture, float thickness, float glowAmount) {
        this.source = source;
        this.target = target;
        this.color = color;
        this.lineTexture = new TextureRegion(lineTexture);
        this.thickness = thickness * Settings.scale;
        this.glowAmount = glowAmount;
        this.points = new Vector2[SEGMENTS + 1];
        for (int i = 0; i <= SEGMENTS; i++) {
            this.points[i] = new Vector2();
        }
        updatePoints();
    }
    
    private void updatePoints() {
        float startX = source.getX() + source.getWidth() / 2.0f;
        float startY = source.getY() + source.getHeight() / 2.0f;
        float endX = target.getX() + target.getWidth() / 2.0f;
        float endY = target.getY() + target.getHeight() / 2.0f;
        
        float controlX1 = startX + CONTROL_POINT_OFFSET;
        float controlY1 = startY;
        float controlX2 = endX - CONTROL_POINT_OFFSET;
        float controlY2 = endY;
        
        for (int i = 0; i <= SEGMENTS; i++) {
            float t = i / (float) SEGMENTS;
            float x = cubicBezier(startX, controlX1, controlX2, endX, t);
            float y = cubicBezier(startY, controlY1, controlY2, endY, t);
            points[i].set(x, y);
        }
    }
    
    private float cubicBezier(float p0, float p1, float p2, float p3, float t) {
        float oneMinusT = 1 - t;
        return oneMinusT * oneMinusT * oneMinusT * p0 +
               3 * oneMinusT * oneMinusT * t * p1 +
               3 * oneMinusT * t * t * p2 +
               t * t * t * p3;
    }
    
    public void render(SpriteBatch sb) {
        updatePoints();
        
        Color renderColor = source.isUnlocked() && target.canUnlock() ? color : Color.DARK_GRAY;
        renderColor.a = 1.0f;
        
        // Draw glow effect
        if (glowAmount > 0) {
            Color glowColor = new Color(renderColor);
            glowColor.a = 0.5f * glowAmount;
            renderLine(sb, glowColor, thickness * 2.0f);
        }
        
        // Draw main line
        renderLine(sb, renderColor, thickness);
    }
    
    private void renderLine(SpriteBatch sb, Color color, float width) {
        sb.setColor(color);
        for (int i = 0; i < SEGMENTS; i++) {
            Vector2 current = points[i];
            Vector2 next = points[i + 1];
            
            float angle = (float) Math.toDegrees(Math.atan2(next.y - current.y, next.x - current.x));
            float length = current.dst(next);
            
            sb.draw(lineTexture,
                    current.x - width / 2.0f, current.y - width / 2.0f,
                    width / 2.0f, width / 2.0f,
                    length, width,
                    1.0f, 1.0f,
                    angle);
        }
    }
}
