package invokermod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import invokermod.talents.AbstractTalent;
import invokermod.talents.TalentTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalentTreeScreen implements ScrollBarListener {
    private static final float HEADER_HEIGHT = 100.0f * Settings.scale;
    private static final float TALENT_SPACING_X = 200.0f * Settings.scale;
    private static final float TALENT_SPACING_Y = 120.0f * Settings.scale;
    private static final float ELEMENT_SPACING = 300.0f * Settings.scale;
    private static final float SCROLL_BAR_THRESHOLD = 500.0f * Settings.scale;

    private static final Color QUAS_COLOR = new Color(0.2f, 0.6f, 1.0f, 1.0f);
    private static final Color WEX_COLOR = new Color(0.8f, 0.2f, 1.0f, 1.0f);
    private static final Color EXORT_COLOR = new Color(1.0f, 0.4f, 0.2f, 1.0f);
    private static final Color INVOKE_COLOR = new Color(1.0f, 0.8f, 0.2f, 1.0f);

    private final List<TalentButton> talentButtons;
    private final List<TalentConnection> talentConnections;
    private final Map<AbstractTalent.TalentType, List<TalentButton>> talentButtonsByType;
    private float scrollY;
    private float targetY;
    private ScrollBar scrollBar;
    private boolean grabbedScreen;
    private float grabStartY;
    private float currentDiffY;

    public TalentTreeScreen() {
        this.talentButtons = new ArrayList<>();
        this.talentConnections = new ArrayList<>();
        this.talentButtonsByType = new HashMap<>();
        this.scrollY = 0.0f;
        this.targetY = 0.0f;
        this.scrollBar = new ScrollBar(this);
        this.grabbedScreen = false;

        initializeTalentButtons();
        initializeTalentConnections();
    }

    private void initializeTalentButtons() {
        float startY = Settings.HEIGHT - HEADER_HEIGHT - TALENT_SPACING_Y;

        // 初始化元素天赋
        initializeElementTalents(AbstractTalent.TalentType.QUAS, Settings.WIDTH * 0.25f, startY);
        initializeElementTalents(AbstractTalent.TalentType.WEX, Settings.WIDTH * 0.5f, startY);
        initializeElementTalents(AbstractTalent.TalentType.EXORT, Settings.WIDTH * 0.75f, startY);

        // 初始化通用天赋
        initializeInvokeTalents(Settings.WIDTH * 0.5f, startY - ELEMENT_SPACING);
    }

    private void initializeElementTalents(AbstractTalent.TalentType type, float centerX, float startY) {
        List<AbstractTalent> talents = TalentTree.getInstance().getTalentsByType(type);
        List<TalentButton> buttons = new ArrayList<>();

        float currentY = startY;
        for (AbstractTalent talent : talents) {
            TalentButton button = new TalentButton(talent, centerX, currentY);
            talentButtons.add(button);
            buttons.add(button);
            currentY -= TALENT_SPACING_Y;
        }

        talentButtonsByType.put(type, buttons);
    }

    private void initializeInvokeTalents(float centerX, float startY) {
        List<AbstractTalent> talents = TalentTree.getInstance().getTalentsByType(AbstractTalent.TalentType.INVOKE);
        List<TalentButton> buttons = new ArrayList<>();

        float currentX = centerX - (talents.size() - 1) * TALENT_SPACING_X / 2.0f;
        for (AbstractTalent talent : talents) {
            TalentButton button = new TalentButton(talent, currentX, startY);
            talentButtons.add(button);
            buttons.add(button);
            currentX += TALENT_SPACING_X;
        }

        talentButtonsByType.put(AbstractTalent.TalentType.INVOKE, buttons);
    }

    private void initializeTalentConnections() {
        // 连接同类型天赋
        connectTalentsByType(AbstractTalent.TalentType.QUAS);
        connectTalentsByType(AbstractTalent.TalentType.WEX);
        connectTalentsByType(AbstractTalent.TalentType.EXORT);

        // 连接元素天赋到通用天赋
        List<TalentButton> invokeButtons = talentButtonsByType.get(AbstractTalent.TalentType.INVOKE);
        if (invokeButtons != null && !invokeButtons.isEmpty()) {
            TalentButton invokeMastery = invokeButtons.get(0);

            // 连接每个元素的最后一个天赋到Invoke Mastery
            for (AbstractTalent.TalentType type : new AbstractTalent.TalentType[]{
                    AbstractTalent.TalentType.QUAS,
                    AbstractTalent.TalentType.WEX,
                    AbstractTalent.TalentType.EXORT
            }) {
                List<TalentButton> elementButtons = talentButtonsByType.get(type);
                if (elementButtons != null && !elementButtons.isEmpty()) {
                    talentConnections.add(new TalentConnection(
                            elementButtons.get(elementButtons.size() - 1),
                            invokeMastery
                    ));
                }
            }

            // 连接Invoke Mastery到其他通用天赋
            for (int i = 1; i < invokeButtons.size(); i++) {
                talentConnections.add(new TalentConnection(invokeMastery, invokeButtons.get(i)));
            }
        }
    }

    private void connectTalentsByType(AbstractTalent.TalentType type) {
        List<TalentButton> buttons = talentButtonsByType.get(type);
        if (buttons != null && buttons.size() > 1) {
            for (int i = 0; i < buttons.size() - 1; i++) {
                talentConnections.add(new TalentConnection(buttons.get(i), buttons.get(i + 1)));
            }
        }
    }

    private Color getTypeColor(AbstractTalent.TalentType type) {
        switch (type) {
            case QUAS:
                return QUAS_COLOR;
            case WEX:
                return WEX_COLOR;
            case EXORT:
                return EXORT_COLOR;
            case INVOKE:
                return INVOKE_COLOR;
            default:
                return Color.WHITE;
        }
    }

    public void render(SpriteBatch sb) {
        // 渲染半透明背景
        sb.setColor(new Color(0.0f, 0.0f, 0.0f, 0.8f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f,
                Settings.WIDTH, Settings.HEIGHT);

        // 渲染标题
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont,
                "Talent Tree", Settings.WIDTH / 2.0f,
                Settings.HEIGHT - HEADER_HEIGHT / 2.0f, Color.WHITE);

        // 渲染元素类型标题
        renderTypeTitle(sb, "Quas", Settings.WIDTH * 0.25f, Settings.HEIGHT - HEADER_HEIGHT * 0.3f, QUAS_COLOR);
        renderTypeTitle(sb, "Wex", Settings.WIDTH * 0.5f, Settings.HEIGHT - HEADER_HEIGHT * 0.3f, WEX_COLOR);
        renderTypeTitle(sb, "Exort", Settings.WIDTH * 0.75f, Settings.HEIGHT - HEADER_HEIGHT * 0.3f, EXORT_COLOR);

        // 渲染可用点数
        String pointsText = "Available Points: " + TalentTree.getInstance().getAvailablePoints();
        FontHelper.renderFontRightAligned(sb, FontHelper.tipHeaderFont,
                pointsText, Settings.WIDTH - 50.0f * Settings.scale,
                Settings.HEIGHT - 50.0f * Settings.scale, Color.GREEN);

        // 渲染连线
        for (TalentConnection connection : talentConnections) {
            connection.render(sb, scrollY);
        }

        // 渲染天赋按钮
        for (TalentButton button : talentButtons) {
            button.render(sb, scrollY);
        }

        // 渲染滚动条
        if (getTotalHeight() > SCROLL_BAR_THRESHOLD) {
            this.scrollBar.render(sb);
        }
    }

    private void renderTypeTitle(SpriteBatch sb, String text, float x, float y, Color color) {
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont,
                text, x, y + scrollY, color);
    }

    public void update() {
        updateScrolling();

        for (TalentButton button : talentButtons) {
            button.update();
        }
    }

    private void updateScrolling() {
        if (!this.grabbedScreen) {
            if (InputHelper.scrolledDown) {
                this.targetY += Settings.SCROLL_SPEED;
            } else if (InputHelper.scrolledUp) {
                this.targetY -= Settings.SCROLL_SPEED;
            }
        }

        if (InputHelper.justClickedLeft) {
            this.grabbedScreen = true;
            this.grabStartY = InputHelper.mY;
            this.currentDiffY = 0.0f;
        }

        if (this.grabbedScreen) {
            this.currentDiffY = InputHelper.mY - this.grabStartY;
        }

        if (InputHelper.justReleasedClickLeft) {
            this.grabbedScreen = false;
            this.targetY += this.currentDiffY;
        }

        this.scrollY = this.targetY;
        if (this.scrollY < 0.0f) {
            this.scrollY = 0.0f;
            this.targetY = 0.0f;
        }

        float scrollMax = Math.max(0.0f, getTotalHeight() - SCROLL_BAR_THRESHOLD);
        if (this.scrollY > scrollMax) {
            this.scrollY = scrollMax;
            this.targetY = scrollMax;
        }

        if (getTotalHeight() > SCROLL_BAR_THRESHOLD) {
            this.scrollBar.update();
        }
    }

    private float getTotalHeight() {
        return HEADER_HEIGHT + talentButtons.size() * TALENT_SPACING_Y;
    }

    @Override
    public void scrolledUsingBar(float newPercent) {
        float newPosition = MathUtils.lerp(0.0f,
                Math.max(0.0f, getTotalHeight() - SCROLL_BAR_THRESHOLD),
                newPercent);
        this.scrollY = newPosition;
        this.targetY = newPosition;
    }

    public void dispose() {
        for (TalentConnection connection : talentConnections) {
            connection.dispose();
        }
    }
}
