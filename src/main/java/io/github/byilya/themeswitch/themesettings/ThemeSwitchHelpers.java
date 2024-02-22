package io.github.byilya.themeswitch.themesettings;

import com.intellij.ide.ui.laf.UIThemeLookAndFeelInfo;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("UnstableApiUsage")
public class ThemeSwitchHelpers {
    private final ThemesManager themesManager = new ThemesManager();

    @Nullable
    private Integer findThemeIndexById(String themeId) {
        AtomicReference<Integer> iAtom = new AtomicReference<>(0);
        @Nullable AtomicReference<Integer> foundThemeIndexAtom = new AtomicReference<>(null);

        Sequence<UIThemeLookAndFeelInfo> allThemes = this.themesManager.getInstalledThemes();
        allThemes.iterator().forEachRemaining((UIThemeLookAndFeelInfo currentTheme) -> {
            if (ThemesManager.isEqualThemes(themeId, currentTheme)) {
                foundThemeIndexAtom.set(iAtom.get());
            }

            iAtom.set(iAtom.get() + 1);
        });

        return foundThemeIndexAtom.get();
    }

    private UIThemeLookAndFeelInfo getDefaultThemeAndUpdateSettingsState(boolean withChangingState) {
        ThemeSettingsState settingsState = ThemeSettingsState.getInstance();
        UIThemeLookAndFeelInfo defaultTheme;

        if (withChangingState) {
            if (settingsState.isUnderDarculaState) {
                defaultTheme = this.themesManager.getDefaultLightTheme();
                settingsState.lightThemeId = defaultTheme.getId();
            } else {
                defaultTheme = this.themesManager.getDefaultDarkTheme();
                settingsState.darkThemeId = defaultTheme.getId();
            }
        } else {
            if (settingsState.isUnderDarculaState) {
                defaultTheme = this.themesManager.getDefaultDarkTheme();
                settingsState.darkThemeId = defaultTheme.getId();
            } else {
                defaultTheme = this.themesManager.getDefaultLightTheme();
                settingsState.lightThemeId = defaultTheme.getId();
            }
        }

        return defaultTheme;
    }

    private UIThemeLookAndFeelInfo getNextThemeToInstall(boolean withChangingState) {
        ThemeSettingsState settingsState = ThemeSettingsState.getInstance();

        String nextThemeId = settingsState.getNextThemeIdToInstall(withChangingState);
        @Nullable UIThemeLookAndFeelInfo themeToInstall = this.themesManager.findThemeById(nextThemeId);

        return themeToInstall == null ? this.getDefaultThemeAndUpdateSettingsState(withChangingState) : themeToInstall;
    }

    private int getFallbackThemeIndex(boolean isDark) {
        UIThemeLookAndFeelInfo fallbackTheme = isDark
                ? this.themesManager.getDefaultDarkTheme()
                : this.themesManager.getDefaultLightTheme();
        @Nullable Integer fallbackThemeIndex = this.findThemeIndexById(fallbackTheme.getId());

        return fallbackThemeIndex == null ? 0 : fallbackThemeIndex;
    }

    protected int getInstalledThemeIndexById(String themeId, boolean isDark) {
        @Nullable Integer installedThemeIndex = this.findThemeIndexById(themeId);

        return installedThemeIndex == null ? this.getFallbackThemeIndex(isDark) : installedThemeIndex;
    }

    @Nullable
    protected UIThemeLookAndFeelInfo findThemeByIndex(int themeIndex) {
        AtomicReference<Integer> iAtom = new AtomicReference<>(0);
        @Nullable AtomicReference<UIThemeLookAndFeelInfo> foundThemeAtom = new AtomicReference<>(null);

        Sequence<UIThemeLookAndFeelInfo> allThemes = this.themesManager.getInstalledThemes();
        allThemes.iterator().forEachRemaining((UIThemeLookAndFeelInfo currentTheme) -> {
            if (iAtom.get().equals(themeIndex)) {
                foundThemeAtom.set(currentTheme);
            }

            iAtom.set(iAtom.get() + 1);
        });

        return foundThemeAtom.get();
    }

    public void changeTheme(boolean withChangingState) {
        ThemeSettingsState settingsState = ThemeSettingsState.getInstance();

        try {
            UIThemeLookAndFeelInfo nextTheme = this.getNextThemeToInstall(withChangingState);
            this.themesManager.changeThemeAndUpdateUi(nextTheme);
        } catch (AssertionError err) {
            UIThemeLookAndFeelInfo defaultTheme = this.getDefaultThemeAndUpdateSettingsState(withChangingState);
            this.themesManager.changeThemeAndUpdateUi(defaultTheme);
        } finally {
            settingsState.switchIsIdeaUnderDarcula(withChangingState);
        }
    }
}
