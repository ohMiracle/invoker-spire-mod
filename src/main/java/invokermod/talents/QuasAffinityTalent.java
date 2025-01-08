package invokermod.talents;

import invokermod.powers.QuasPower;

import static invokermod.InvokerMod.makeID;

public class QuasAffinityTalent extends AbstractTalent {
    private static final String ID = makeID("QuasAffinity");
    private static final int MAX_LEVEL = 3;
    private static final float[] HEAL_MULTIPLIER = {1.2f, 1.4f, 1.6f};

    public QuasAffinityTalent(TalentTree tree) {
        super(ID, TalentType.QUAS, tree, MAX_LEVEL);
    }

    @Override
    protected boolean meetsRequirements() {
        return true; // 基础天赋，无前置要求
    }

    @Override
    protected void onLevelUp() {
        // 更新QuasPower的治疗倍率
        QuasPower.setHealMultiplier(HEAL_MULTIPLIER[level - 1]);
    }

    public float getHealMultiplier() {
        return level > 0 ? HEAL_MULTIPLIER[level - 1] : 1.0f;
    }
}
