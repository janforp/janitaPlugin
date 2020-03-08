package com.janita.plugin.replace.handler;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import com.janita.plugin.replace.constant.ReplaceChineseCharConstants;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 类说明：替换自负的处理器，核心的替换逻辑在此
 *
 * @author zhucj
 * @since 2020/3/8 - 上午11:59
 */
public class ReplaceChineseCharTypedHandler implements TypedActionHandler {

    /**
     * 输入key,替换成value
     */
    private static Map<String, String> cachedReplaceCharMap = new HashMap<>();

    private TypedActionHandler typedActionHandler;

    private char lastChar = ' ';

    public ReplaceChineseCharTypedHandler(TypedActionHandler typedActionHandler) {
        this.typedActionHandler = typedActionHandler;
    }

    public static void reloadReplaceCharMap() {
        cachedReplaceCharMap.clear();
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        String cachedValue = propertiesComponent.getValue(ReplaceChineseCharConstants.CACHE_KEY, ReplaceChineseCharConstants.DEFAULT_CACHE_VALUE);
        String[] cacheValueArr = cachedValue.split(ReplaceChineseCharConstants.SEP_CHAR);
        for (int i = 0; i < cacheValueArr.length / 2; i++) {
            String key = cacheValueArr[2 * i].trim();
            String value = cacheValueArr[2 * i + 1].trim();
            cachedReplaceCharMap.put(key, value);
        }
    }

    @Override
    public void execute(@NotNull Editor editor, char inputChar, @NotNull DataContext dataContext) {
        Document document = editor.getDocument();
        Project project = editor.getProject();
        CaretModel caretModel = editor.getCaretModel();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        int offset = primaryCaret.getOffset();
        String inputStr = String.valueOf(inputChar);
        String replacedValue = cachedReplaceCharMap.get(inputStr);
        if (lastChar == ReplaceChineseCharConstants.BIAS_LINE && replacedValue != null) {
            Runnable runnable = () -> {
                document.deleteString(offset - 1, offset);
                document.insertString(offset - 1, inputStr);
                primaryCaret.moveToOffset(offset);
            };
            WriteCommandAction.runWriteCommandAction(project, runnable);
            this.lastChar = inputChar;
            return;
        }
        if (replacedValue != null) {
            this.typedActionHandler.execute(editor, replacedValue.charAt(0), dataContext);
            this.lastChar = inputChar;
            return;
        }
        this.typedActionHandler.execute(editor, inputChar, dataContext);
        this.lastChar = inputChar;
    }
}
