package invokermod.talents;

import invokermod.powers.WexPower;

import static invokermod.InvokerMod.makeID;

public class WexAffinityTalent extends AbstractTalent {
    private static final String ID = makeID("WexAffinity");
    private static final int MAX_LEVEL = 3;
    private static final int[] EXTRA_CARDS = {1, 1, 2};

    public WexAffinityTalent(TalentTree tree) {
        super(ID, TalentType.WEX, tree, MAX_LEVEL);
    }

    @Override
    protected boolean meetsRequirements() {
        return true; // 基础天赋，无前置要求
    }

    @Override
    protected void onLevelUp() {
        // 更新WexPower的额外抽牌数
        WexPower.setExtraCards(EXTRA_CARDS[level - 1]);
    }

    public int getExtraCards() {
        return level > 0 ? EXTRA_CARDS[level - 1] : 0;
    }
}
