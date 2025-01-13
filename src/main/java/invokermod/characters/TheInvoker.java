package invokermod.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import invokermod.cards.basic.Defend;
import invokermod.cards.basic.Exort;
import invokermod.cards.basic.Quas;
import invokermod.cards.basic.Strike;
import invokermod.cards.basic.Wex;
import invokermod.constant.CommonConstant;
import invokermod.constant.ResourcePath;
import invokermod.enums.CharacterEnums;
import invokermod.relics.Elitism;
import invokermod.util.ResourcePathUtil;

import java.util.ArrayList;

public class TheInvoker extends CustomPlayer {
    



    public TheInvoker() {
        super(CommonConstant.INVOKER_CHARACTER_STRING.NAMES[0],
                CharacterEnums.INVOKER,
                null, // 不使用动画
                null, // 不使用动画
                null, // 不使用动画 
                null, // 不使用动画
                null); // 不使用动画

        initializeClass(
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER, "invoker.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER, "shoulder.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER, "shoulder2.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER, "corpse_character.png"),
                getLoadout(),
                20.0F, -10.0F, 220.0F, 390.0F,
                new EnergyManager(CommonConstant.ENERGY_PER_TURN));
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Exort.ID);
        retVal.add(Wex.ID);
        retVal.add(Quas.ID);
        // retVal.add(Defend.ID);
        // retVal.add(Defend.ID);
        // retVal.add(Defend.ID);
        // retVal.add(Defend.ID);
        // retVal.add(Strike.ID);
        // retVal.add(Strike.ID);
        // retVal.add(Strike.ID);
        // retVal.add(Strike.ID);

        // retVal.add("Wex");
        // retVal.add("Exort");
        // retVal.add("Invoke");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Elitism.ID);
        UnlockTracker.markRelicAsSeen(Elitism.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                CommonConstant.INVOKER_CHARACTER_STRING.NAMES[0],
                CommonConstant.INVOKER_CHARACTER_STRING.TEXT[0],
                75,
                75,
                3,
                99,
                5,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "The Invoker";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CharacterEnums.INVOKER_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.CYAN;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        // Return a basic card for events
        return null; // Implement later
    }

    @Override
    public Color getCardTrailColor() {
        return Color.CYAN;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_MAGIC_FAST_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return CommonConstant.INVOKER_CHARACTER_STRING.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheInvoker();
    }

    @Override
    public String getSpireHeartText() {
        return CommonConstant.INVOKER_CHARACTER_STRING.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.CYAN;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.LIGHTNING,
                AbstractGameAction.AttackEffect.SLASH_HEAVY
        };
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }
}
