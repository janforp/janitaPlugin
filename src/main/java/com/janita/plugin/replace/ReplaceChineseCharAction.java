package com.janita.plugin.replace;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.janita.plugin.replace.handler.ReplaceChineseCharTypedHandler;
import org.jetbrains.annotations.NotNull;

/**
 * 加载编辑处理器即可
 *
 * @author zhucj
 * @since 202003
 */
public class ReplaceChineseCharAction extends AnAction {

    static {
        System.out.println("ReplaceChineseCharAction静态代码块");
        final EditorActionManager editorActionManager = EditorActionManager.getInstance();
        final TypedAction typedAction = editorActionManager.getTypedAction();
        typedAction.setupHandler(new ReplaceChineseCharTypedHandler(typedAction.getHandler()));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("com.janita.plugin.replace.ReplaceChineseCharAction.actionPerformed");
    }
}
