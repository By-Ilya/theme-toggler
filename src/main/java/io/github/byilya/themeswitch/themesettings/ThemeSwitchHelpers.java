package io.github.byilya.themeswitch.themesettings;

import com.intellij.ide.actions.QuickChangeLookAndFeel;
import com.intellij.ide.ui.LafManager;

import javax.swing.*;

public class ThemeSwitchHelpers {
    private static UIManager.LookAndFeelInfo getFallbackTheme(boolean withChangingState) {
        ThemeSettingsState settingsState = ThemeSettingsState.getInstance();

        UIManager.LookAndFeelInfo fallbackTheme;
        if (withChangingState) {
            if (settingsState.isUnderDarculaState) {
                fallbackTheme = ThemeHelpers.getBaseLightTheme();
                settingsState.lightThemeId = ThemeHelpers.getThemeId(fallbackTheme);
            } else {
                fallbackTheme = ThemeHelpers.getBaseDarkTheme();
                settingsState.darkThemeId = ThemeHelpers.getThemeId(fallbackTheme);
            }
        } else {
            if (settingsState.isUnderDarculaState) {
                fallbackTheme = ThemeHelpers.getBaseDarkTheme();
                settingsState.darkThemeId = ThemeHelpers.getThemeId(fallbackTheme);
            } else {
                fallbackTheme = ThemeHelpers.getBaseLightTheme();
                settingsState.lightThemeId = ThemeHelpers.getThemeId(fallbackTheme);
            }
        }

        return fallbackTheme;
    }

    private static UIManager.LookAndFeelInfo getThemeToInstall(boolean withChangingState) {
        ThemeSettingsState settingsState = ThemeSettingsState.getInstance();

        String newThemeId;
        if (withChangingState) {
            newThemeId = settingsState.isUnderDarculaState
                    ? settingsState.lightThemeId
                    : settingsState.darkThemeId;
        } else {
            newThemeId = settingsState.isUnderDarculaState
                    ? settingsState.darkThemeId
                    : settingsState.lightThemeId;
        }

        UIManager.LookAndFeelInfo newTheme = null;
        UIManager.LookAndFeelInfo[] allThemes = ThemeHelpers.getAllInstalledThemes();
        for (UIManager.LookAndFeelInfo theme : allThemes) {
            if (ThemeHelpers.isEqualThemes(theme, newThemeId)) {
                newTheme = theme;
            }
        }

        return newTheme == null ? getFallbackTheme(withChangingState) : newTheme;
    }

    protected static int getFallbackThemeIndex(UIManager.LookAndFeelInfo[] allThemes, boolean isDark) {
        UIManager.LookAndFeelInfo fallbackTheme = isDark
                ? ThemeHelpers.getBaseDarkTheme()
                : ThemeHelpers.getBaseLightTheme();

        for (int i = 0; i < allThemes.length; i++) {
            if (ThemeHelpers.isEqualThemes(allThemes[i], fallbackTheme)) {
                return i;
            }
        }

        return 0;
    }

    public static void changeTheme(boolean withChangingState) {
        ThemeSettingsState settingsState = ThemeSettingsState.getInstance();
        try {
            QuickChangeLookAndFeel.switchLafAndUpdateUI(
                    LafManager.getInstance(),
                    getThemeToInstall(withChangingState),
                    false
            );
        } catch (AssertionError err) {
            QuickChangeLookAndFeel.switchLafAndUpdateUI(
                    LafManager.getInstance(),
                    getFallbackTheme(withChangingState),
                    false
            );
        } finally {
            if (withChangingState) {
                settingsState.isUnderDarculaState = !settingsState.isUnderDarculaState;
            }
        }
    }
}
