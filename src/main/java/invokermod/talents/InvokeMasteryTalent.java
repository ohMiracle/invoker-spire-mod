package invokermod.talents;

import static invokermod.InvokerMod.makeID;

public class InvokeMasteryTalent extends AbstractTalent {
    private static final String ID = makeID("InvokeMastery");
    private static final int MAX_LEVEL = 3;
    private static final float[] SPELL_DAMAGE_MULTIPLIER = {1.2f, 1.4f, 1.6f};

    public InvokeMasteryTalent(TalentTree tree) {
        super(ID, TalentType.INVOKE, tree, MAX_LEVEL);
    }

    @Override
    protected boolean meetsRequirements() {
        return tree.getTalent("QuasAffinity").getLevel() > 0 &&
               tree.getTalent("WexAffinity").getLevel() > 0 &&
               tree.getTalent("ExortAffinity").getLevel() > 0;
    }

    @Override
    protected void onLevelUp() {
        // 更新技能伤害倍率
    }

    public float getSpellDamageMultiplier() {
        return level > 0 ? SPELL_DAMAGE_MULTIPLIER[level - 1] : 1.0f;
    }
}
