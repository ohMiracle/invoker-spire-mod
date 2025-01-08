package invokermod.talents;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import invokermod.characters.TheInvoker;

import java.util.*;

public class TalentTree {
    private static TalentTree instance;
    private final Map<String, AbstractTalent> talents;
    private final Map<AbstractTalent.TalentType, List<AbstractTalent>> talentsByType;
    private final Map<AbstractTalent.TalentType, Float> typeBonuses;
    private int availablePoints;
    private int totalPoints;
    private int bonusPoints;
    private static final int POINTS_PER_LEVEL = 1;
    private static final int MAX_LEVEL = 20;

    private TalentTree() {
        talents = new HashMap<>();
        talentsByType = new HashMap<>();
        typeBonuses = new HashMap<>();
        availablePoints = 0;
        totalPoints = 0;
        bonusPoints = 0;
        initializeTalents();
    }

    public static TalentTree getInstance() {
        if (instance == null) {
            instance = new TalentTree();
        }
        return instance;
    }

    private void initializeTalents() {
        // Initialize Ice element talents
        talents.put("QuasAffinity", new QuasAffinityTalent(this));
        talents.put("IceMastery", new IceMasteryTalent(this));
        talents.put("FrostArmor", new FrostArmorTalent(this));

        // Initialize Storm element talents
        talents.put("WexAffinity", new WexAffinityTalent(this));
        talents.put("LightningMastery", new LightningMasteryTalent(this));
        talents.put("SpeedAura", new SpeedAuraTalent(this));

        // Initialize Fire element talents
        talents.put("ExortAffinity", new ExortAffinityTalent(this));
        talents.put("FireMastery", new FireMasteryTalent(this));
        talents.put("BurningAura", new BurningAuraTalent(this));

        // Initialize general invoke talents
        talents.put("InvokeMastery", new InvokeMasteryTalent(this));
        talents.put("ElementalHarmony", new ElementalHarmonyTalent(this));
        talents.put("SpellAmplification", new SpellAmplificationTalent(this));

        for (AbstractTalent talent : talents.values()) {
            AbstractTalent.TalentType type = talent.getType();
            talentsByType.computeIfAbsent(type, k -> new ArrayList<>()).add(talent);
        }
    }

    public void onLevelUp() {
        if (AbstractDungeon.player instanceof TheInvoker) {
            int level = AbstractDungeon.player.level;
            if (level <= MAX_LEVEL) {
                availablePoints += POINTS_PER_LEVEL;
                totalPoints += POINTS_PER_LEVEL;
            }
        }
    }

    public void spendPoint() {
        if (availablePoints > 0) {
            availablePoints--;
        }
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public AbstractTalent getTalent(String id) {
        return talents.get(id);
    }

    public List<AbstractTalent> getAllTalents() {
        return new ArrayList<>(talents.values());
    }

    public List<AbstractTalent> getTalentsByType(AbstractTalent.TalentType type) {
        return talentsByType.getOrDefault(type, Collections.emptyList());
    }

    public void reset() {
        availablePoints = 0;
        totalPoints = 0;
        bonusPoints = 0;
        for (AbstractTalent talent : talents.values()) {
            talent.reset();
        }
    }

    public void addBonusPoints(int points) {
        this.bonusPoints += points;
        this.availablePoints += points;
    }

    public void removeBonusPoints(int points) {
        this.bonusPoints = Math.max(0, this.bonusPoints - points);
        this.availablePoints = Math.max(0, this.availablePoints - points);
    }

    public void addTypeBonus(AbstractTalent.TalentType type) {
        typeBonuses.put(type, typeBonuses.getOrDefault(type, 1.0f) + 0.25f);
    }

    public void removeTypeBonus(AbstractTalent.TalentType type) {
        typeBonuses.put(type, Math.max(1.0f, typeBonuses.getOrDefault(type, 1.0f) - 0.25f));
    }

    public float getTypeBonus(AbstractTalent.TalentType type) {
        return typeBonuses.getOrDefault(type, 1.0f);
    }

    public void increaseAllMaxLevels(int amount) {
        for (List<AbstractTalent> talents : talentsByType.values()) {
            for (AbstractTalent talent : talents) {
                talent.increaseMaxLevel(amount);
            }
        }
    }

    public void decreaseAllMaxLevels(int amount) {
        for (List<AbstractTalent> talents : talentsByType.values()) {
            for (AbstractTalent talent : talents) {
                talent.decreaseMaxLevel(amount);
            }
        }
    }
}
