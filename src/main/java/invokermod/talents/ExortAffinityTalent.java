package invokermod.talents;

import invokermod.powers.ExortPower;

import static invokermod.InvokerMod.makeID;

public class ExortAffinityTalent extends AbstractTalent {
    private static final String ID = makeID("ExortAffinity");
    private static final int MAX_LEVEL = 3;
    private static final float[] STRENGTH_MULTIPLIER = {1.5f, 2.0f, 2.5f};

    public ExortAffinityTalent(TalentTree tree) {
        super(ID, TalentType.EXORT, tree, MAX_LEVEL);
    }

    @Override
    protected boolean meetsRequirements() {
        return true; // 基础天赋，无前置要求
    }

    @Override
    protected void onLevelUp() {
        // 更新ExortPower的力量倍率
        ExortPower.setStrengthMultiplier(STRENGTH_MULTIPLIER[level - 1]);
    }

    public float getStrengthMultiplier() {
        return level > 0 ? STRENGTH_MULTIPLIER[level - 1] : 1.0f;
    }
}
