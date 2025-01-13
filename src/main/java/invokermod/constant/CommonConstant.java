package invokermod.constant;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CharacterStrings;

/**
 * @Description: 常量类
 * @Author: zhengchang
 * @Date: 2025/1/10
 */
public interface CommonConstant {


    String MOD_ID = "invokermod";
    String INVOKER = "INVOKER";
    int ENERGY_PER_TURN = 3;
    // 精英主义
    String ELITISM_RELIC = "ElitismRelic";

    CharacterStrings INVOKER_CHARACTER_STRING = CardCrawlGame.languagePack.getCharacterString(MOD_ID + ":" + INVOKER);

}
