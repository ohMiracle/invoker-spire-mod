

package invokermod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import invokermod.InvokerMod;
import invokermod.constant.CommonConstant;
import invokermod.constant.ResourcePath;
import invokermod.util.ResourcePathUtil;

public class Elitism extends CustomRelic {

    public static final String ID = InvokerMod.makeID(CommonConstant.ELITISM_RELIC);

    public Elitism() {
        super(ID, 
        getImgTexture(ID), 
        RelicTier.STARTER, LandingSound.MAGICAL);
    }


    public AbstractRelic makeCopy() {
        return new Elitism();
    }

    private static Texture getImgTexture(String id) {
        return ImageMaster.loadImage(ResourcePathUtil.getPath(ResourcePath.IMAGE_RELICS_OUTLINE, "elitism.png"));
    }
}
