package invokermod;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.*;
import invokermod.cards.basic.Strike;
import invokermod.cards.basic.Defend;
import invokermod.cards.basic.Quas;
import invokermod.cards.basic.Wex;
import invokermod.cards.basic.Exort;
import invokermod.characters.TheInvoker;
import invokermod.constant.CommonConstant;
import invokermod.constant.ResourcePath;
import invokermod.enums.CharacterEnums;
import invokermod.relics.*;
import invokermod.util.ResourcePathUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.megacrit.cardcrawl.core.Settings.language;

@SpireInitializer
public class InvokerMod implements
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(InvokerMod.class.getName());
    private static String modID;
    private static CharacterStrings characterStrings;
    
    public static CharacterStrings getCharacterStrings() {
        if (characterStrings == null) {
            characterStrings = CardCrawlGame.languagePack.getCharacterString("invokermod:TheInvoker");
        }
        return characterStrings;
    }

    public InvokerMod() {
    
        BaseMod.subscribe(this);
        modID = CommonConstant.MOD_ID;

        BaseMod.addColor(CharacterEnums.INVOKER_COLOR,
                Color.CYAN.cpy(),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_512, "bg_attack_512.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_512, "bg_skill_512.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_512, "bg_power_512.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_ORB, "cost_orb.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_1024, "bg_attack.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_1024, "bg_skill.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_1024, "bg_power.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_ORB, "card_orb.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER_CARDSTYLE_ORB, "small_orb.png"));
    
    }

    public static void initialize() {
        new InvokerMod();
    }

    @Override
    public void receivePostInitialize() {
        // 初始化设置和资源
        logger.info("Initializing Invoker Mod settings and resources");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheInvoker(),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER,"button.png"),
                ResourcePathUtil.getPath(ResourcePath.IMAGE_CHAR_INVOKER,"Character_Portrait.png"),
                CharacterEnums.INVOKER);
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics for Invoker");
        BaseMod.addRelicToCustomPool(new Elitism(), CharacterEnums.INVOKER_COLOR);
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding cards for Invoker");
        
        // Register basic cards manually to ensure they're available
        BaseMod.addCard(new Quas());
        BaseMod.addCard(new Wex());
        BaseMod.addCard(new Exort());
        // BaseMod.addCard(new Strike());
        // BaseMod.addCard(new Defend());
        
        // Auto-register other cards
        // new AutoAdd(getModID())
        //     .packageFilter("invokermod.cards")
        //     .setDefaultSeen(true)
        //     .cards();
    }

    @Override
    public void receiveEditStrings() {
        String lang;
        switch (language) {
            case ZHS:
                lang = "zhs";
                break;
            case ZHT:
                lang = "zht";
                break;
            default:
            case ENG:
                lang = "eng";
                break;
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, ResourcePathUtil.getPath(ResourcePath.LOCALIZATION + "/" + lang, "cards.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, ResourcePathUtil.getPath(ResourcePath.LOCALIZATION + "/" + lang, "characters.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, ResourcePathUtil.getPath(ResourcePath.LOCALIZATION + "/" + lang, "relics.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class, ResourcePathUtil.getPath(ResourcePath.LOCALIZATION + "/" + lang, "orbs.json"));
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    public static String getModID() {
        return modID;
    }

}
