package io.github.byilya.themeswitch;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;

import io.github.byilya.themeswitch.themesettings.ThemeSwitchHelpers;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import java.awt.event.MouseEvent;

public class ChangeThemeWidgetPresentation implements StatusBarWidget.IconPresentation {
    private final ThemeSwitchHelpers themeSwitchHelpers = new ThemeSwitchHelpers();

    @Override
    public @Nullable Icon getIcon() {
        return AllIcons.Actions.IntentionBulbGrey;
    }

    @Override
    public @Nullable @NlsContexts.Tooltip String getTooltipText() {
        return "Change theme";
    }

    @Override
    public @Nullable Consumer<MouseEvent> getClickConsumer() {
        return mouseEvent -> this.themeSwitchHelpers.changeTheme(true);
    }
}
