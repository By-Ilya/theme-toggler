package io.github.byilya.themeswitch;

import com.intellij.icons.AllIcons;
import com.intellij.ide.actions.QuickChangeLookAndFeel;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.laf.IntelliJLookAndFeelInfo;
import com.intellij.ide.ui.laf.darcula.DarculaLookAndFeelInfo;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class ChangeThemeWidgetPresentation implements StatusBarWidget.IconPresentation {
    private static final UIManager.LookAndFeelInfo LIGHT_THEME = new IntelliJLookAndFeelInfo();
    private static final UIManager.LookAndFeelInfo DARK_THEME = new DarculaLookAndFeelInfo();

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
        return mouseEvent -> {
            UIManager.LookAndFeelInfo newTheme = UIUtil.isUnderDarcula() ? LIGHT_THEME : DARK_THEME;
            QuickChangeLookAndFeel.switchLafAndUpdateUI(LafManager.getInstance(), newTheme, true);
        };
    }
}
