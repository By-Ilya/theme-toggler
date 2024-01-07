package io.github.byilya.themeswitch.themesettings;

import com.intellij.ide.actions.QuickChangeLookAndFeel;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.laf.UIThemeLookAndFeelInfo;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("UnstableApiUsage")
public class ThemesManager {
    private final LafManager ideaLafManager = LafManager.getInstance();

    protected static boolean isEqualThemes(UIThemeLookAndFeelInfo aTheme, UIThemeLookAndFeelInfo bTheme) {
        return aTheme.getId().equals(bTheme.getId());
    }

    protected static  boolean isEqualThemes(String aThemeId, UIThemeLookAndFeelInfo bTheme) {
        return aThemeId.equals(bTheme.getId());
    }

    protected Sequence<UIThemeLookAndFeelInfo> getInstalledThemes() {
        return this.ideaLafManager.getInstalledThemes();
    }

    protected UIThemeLookAndFeelInfo getDefaultLightTheme() {
        return this.ideaLafManager.getDefaultLightLaf();
    }

    protected UIThemeLookAndFeelInfo getDefaultDarkTheme() {
        return this.ideaLafManager.getDefaultDarkLaf();
    }

    protected boolean isIdeaUnderDarcula() {
        return this.ideaLafManager.getCurrentUIThemeLookAndFeel().isDark();
    }

    protected UIThemeLookAndFeelInfo findThemeById(String themeId) {
        AtomicReference<@Nullable UIThemeLookAndFeelInfo> foundThemeAtom = new AtomicReference<>(null);

        Sequence<UIThemeLookAndFeelInfo> allThemes = this.getInstalledThemes();
        allThemes.iterator().forEachRemaining((UIThemeLookAndFeelInfo currentTheme) -> {
            if (isEqualThemes(themeId, currentTheme)) {
                foundThemeAtom.set(currentTheme);
            }
        });

        return foundThemeAtom.get();
    }

    protected void changeThemeAndUpdateUi(UIThemeLookAndFeelInfo newTheme) {
        QuickChangeLookAndFeel.switchLafAndUpdateUI(this.ideaLafManager, newTheme, false);
    }
}
