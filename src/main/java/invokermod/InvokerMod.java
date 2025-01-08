package invokermod;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import invokermod.cards.basic.*;
import invokermod.characters.TheInvoker;
import invokermod.relics.*;
import invokermod.ui.TalentTreeScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class InvokerMod implements
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber,
        PostUpdateSubscriber {

    public static final Logger logger = LogManager.getLogger(InvokerMod.class.getName());
    private static String modID;
    private TalentTreeScreen talentTreeScreen;
    private boolean showingTalentTree;

    public InvokerMod() {
        BaseMod.subscribe(this);
        modID = "invokermod";
        
        logger.info("Creating the color " + TheInvoker.Enums.INVOKER_COLOR.toString());
        BaseMod.addColor(TheInvoker.Enums.INVOKER_COLOR,
                Color.GOLD.cpy(),
                "invokermodResources/images/512/bg_attack_default_gray.png",
                "invokermodResources/images/512/bg_skill_default_gray.png",
                "invokermodResources/images/512/bg_power_default_gray.png",
                "invokermodResources/images/512/card_default_gray_orb.png",
                "invokermodResources/images/1024/bg_attack_default_gray.png",
                "invokermodResources/images/1024/bg_skill_default_gray.png",
                "invokermodResources/images/1024/bg_power_default_gray.png",
                "invokermodResources/images/1024/card_default_gray_orb.png",
                "invokermodResources/images/512/card_small_orb.png");
    }

    public static void initialize() {
        new InvokerMod();
    }

    @Override
    public void receivePostInitialize() {
        this.talentTreeScreen = new TalentTreeScreen();
        this.showingTalentTree = false;
        
        // 添加打开天赋树的按钮
        BaseMod.addTopPanelItem(new TopPanelItem("Talents", "Open Talent Tree",
                "invokermodResources/images/ui/talent_button.png") {
            @Override
            protected void onClick() {
                if (AbstractDungeon.player instanceof TheInvoker) {
                    showingTalentTree = !showingTalentTree;
                }
            }
        });
    }

    @Override
    public void receivePostUpdate() {
        if (showingTalentTree && talentTreeScreen != null) {
            talentTreeScreen.update();
            
            // 按ESC关闭天赋树
            if (InputHelper.pressedEscape) {
                InputHelper.pressedEscape = false;
                showingTalentTree = false;
            }
        }
    }

    @Override
    public void receiveRender(SpriteBatch sb) {
        if (showingTalentTree && talentTreeScreen != null) {
            talentTreeScreen.render(sb);
        }
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding cards for Invoker");
        
        // Add basic cards
        BaseMod.addCard(new Strike_Invoker());
        BaseMod.addCard(new Defend_Invoker());
        BaseMod.addCard(new Invoke());
        
        // Add element cards
        BaseMod.addCard(new Quas());
        BaseMod.addCard(new Wex());
        BaseMod.addCard(new Exort());
        
        // Add triple element invoke spell cards
        BaseMod.addCard(new ColdSnap());
        BaseMod.addCard(new EMP());
        BaseMod.addCard(new SunStrike());
        
        // Add double element invoke spell cards
        BaseMod.addCard(new GhostWalk());
        BaseMod.addCard(new IceWall());
        BaseMod.addCard(new Alacrity());
        BaseMod.addCard(new Tornado());
        BaseMod.addCard(new ForgeSpirit());
        BaseMod.addCard(new ChaosMeteor());
    }
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Adding Invoker character");
        
        BaseMod.addCharacter(new TheInvoker("The Invoker"),
                THE_INVOKER_BUTTON,
                THE_INVOKER_PORTRAIT,
                TheInvoker.class);
    }
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics for Invoker");
        
        // 注册基础遗物
        BaseMod.addRelicToCustomPool(new QuasOrbRelic(), TheInvoker.Enums.INVOKER_COLOR);
        BaseMod.addRelicToCustomPool(new WexOrbRelic(), TheInvoker.Enums.INVOKER_COLOR);
        BaseMod.addRelicToCustomPool(new ExortOrbRelic(), TheInvoker.Enums.INVOKER_COLOR);
        BaseMod.addRelicToCustomPool(new AghanimsScepter(), TheInvoker.Enums.INVOKER_COLOR);
        BaseMod.addRelicToCustomPool(new ArcaneSupremacy(), TheInvoker.Enums.INVOKER_COLOR);
    }
    
    @Override
    public void receiveEditStrings() {
        String lang = "eng";
        
        // Fall back to English if the language is not supported
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }
        
        // Load localization files
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "invokermodResources/localization/" + lang + "/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                "invokermodResources/localization/" + lang + "/character.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "invokermodResources/localization/" + lang + "/powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "invokermodResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(KeywordStrings.class,
                "invokermodResources/localization/" + lang + "/keywords.json");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    private static String getModID() {
        return modID;
    }
}
