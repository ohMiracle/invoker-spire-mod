package invokermod.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import invokermod.InvokerMod;
import invokermod.orbs.InvokerEnergyOrb;

import java.util.ArrayList;

import static invokermod.InvokerMod.makeID;

public class TheInvoker extends CustomPlayer {
    public static final String ID = makeID("TheInvoker");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    private final InvokerEnergyOrb energyOrb;
    
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_INVOKER;
        @SpireEnum(name = "INVOKER_BLUE")
        public static AbstractCard.CardColor INVOKER_BLUE;
        @SpireEnum(name = "INVOKER_BLUE")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public TheInvoker() {
        super(characterStrings.NAMES[0], Enums.THE_INVOKER,
                new InvokerEnergyOrb(),
                "images/characters/ironclad/idle/idle.png",
                "images/characters/ironclad/shoulder.png");

        this.energyOrb = (InvokerEnergyOrb) this.orb;

        initializeClass(null,
                "images/characters/ironclad/shoulder2.png",
                "images/characters/ironclad/corpse.png",
                loadout(),
                20.0F, -10.0F, 220.0F, 290.0F,
                new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(
                "images/characters/ironclad/idle/skeleton.atlas",
                "images/characters/ironclad/idle/skeleton.json",
                1.0f);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(makeID("Strike"));
        retVal.add(makeID("Strike"));
        retVal.add(makeID("Strike"));
        retVal.add(makeID("Defend"));
        retVal.add(makeID("Defend"));
        retVal.add(makeID("Defend"));
        retVal.add(makeID("Quas"));
        retVal.add(makeID("Wex"));
        retVal.add(makeID("Exort"));
        retVal.add(makeID("Invoke"));
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(makeID("QuasOrbRelic"));
        UnlockTracker.markRelicAsSeen(makeID("QuasOrbRelic"));
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0],
                characterStrings.TEXT[0],
                STARTING_HP,
                MAX_HP,
                ORB_SLOTS,
                STARTING_GOLD,
                CARD_DRAW,
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.INVOKER_BLUE;
    }

    @Override
    public Color getCardRenderColor() {
        return InvokerMod.INVOKER_BLUE;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public Color getCardTrailColor() {
        return InvokerMod.INVOKER_BLUE;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_MAGIC_FAST_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_MAGIC_FAST_1";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheInvoker();
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return InvokerMod.INVOKER_BLUE;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.LIGHTNING,
                AbstractGameAction.AttackEffect.POISON,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    @Override
    public String getVampireText() {
        return characterStrings.TEXT[2];
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        this.energyOrb.render(sb);
    }
}
