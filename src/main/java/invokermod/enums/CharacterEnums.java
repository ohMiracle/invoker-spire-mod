package invokermod.enums;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class CharacterEnums {
    @SpireEnum
    public static AbstractPlayer.PlayerClass INVOKER;
    @SpireEnum
    public static AbstractCard.CardColor INVOKER_COLOR;
    @SpireEnum(name = "INVOKER_COLOR")
    public static CardLibrary.LibraryType INVOKER_LIBRARY;
}