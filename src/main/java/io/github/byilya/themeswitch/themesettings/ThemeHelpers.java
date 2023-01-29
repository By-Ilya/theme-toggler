package io.github.byilya.themeswitch.themesettings;

import com.intellij.ide.ui.laf.IntelliJLookAndFeelInfo;
import com.intellij.ide.ui.laf.LafManagerImpl;
import com.intellij.ide.ui.laf.darcula.DarculaLookAndFeelInfo;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;

public class ThemeHelpers {
    protected static UIManager.LookAndFeelInfo getBaseLightTheme() {
        return new IntelliJLookAndFeelInfo();
    }

    protected static UIManager.LookAndFeelInfo getBaseDarkTheme() {
        return new DarculaLookAndFeelInfo();
    }

    protected static UIManager.LookAndFeelInfo[] getAllInstalledThemes() {
        return new LafManagerImpl().getInstalledLookAndFeels();
    }

    protected static String getThemeId(UIManager.LookAndFeelInfo theme) {
        return theme.toString();
    }

    protected static boolean isEqualThemes(UIManager.LookAndFeelInfo aTheme, UIManager.LookAndFeelInfo bTheme) {
        return getThemeId(aTheme).equals(getThemeId(bTheme));
    }

    protected static boolean isEqualThemes(UIManager.LookAndFeelInfo aTheme, String bThemeId) {
        return getThemeId(aTheme).equals(bThemeId);
    }

    protected static boolean isIdeaUnderDarcula() {
        return UIUtil.isUnderDarcula();
    }
}
