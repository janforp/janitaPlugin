package com.janita.plugin.autgencode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.janita.plugin.autgencode.dialog.AutoCodeDialog;
import org.jetbrains.annotations.NotNull;

/**
 * 自动代码生成
 *
 * @author zhucj
 * @since 202003
 */
public class AutoCodeAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AutoCodeDialog dialog = new AutoCodeDialog();
        dialog.setVisible(true);
    }
}
