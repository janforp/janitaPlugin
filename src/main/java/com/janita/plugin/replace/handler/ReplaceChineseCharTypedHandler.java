package com.janita.plugin.replace.handler;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import com.janita.plugin.replace.constant.ReplaceChineseCharConstants;
import com.janita.plugin.replace.util.CacheUtils;
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

    /**
     * 处理器
     */
    private TypedActionHandler typedActionHandler;

    /**
     * 最终输入
     */
    private char lastChar = ' ';

    public ReplaceChineseCharTypedHandler(TypedActionHandler typedActionHandler) {
        System.out.println("com.janita.plugin.replace.handler.ReplaceChineseCharTypedHandler.ReplaceChineseCharTypedHandler构造器");
        this.typedActionHandler = typedActionHandler;
    }

    static {
        System.out.println("ReplaceChineseCharTypedHandler静态代码块");
        reloadReplaceCharMap();
    }

    public static void reloadReplaceCharMap() {
        System.out.println("com.janita.plugin.replace.handler.ReplaceChineseCharTypedHandler.reloadReplaceCharMap");
        cachedReplaceCharMap.clear();
        String cachedValue = CacheUtils.getCacheValue();
        String[] cacheValueArr = cachedValue.split(ReplaceChineseCharConstants.SEP_CHAR);
        final int doubleV = 2;
        for (int i = 0; i < cacheValueArr.length / doubleV; i++) {
            String key = cacheValueArr[doubleV * i].trim();
            String value = cacheValueArr[doubleV * i + 1].trim();
            cachedReplaceCharMap.put(key, value);
        }
    }

    @Override
    public void execute(@NotNull Editor editor, char originInputChar, @NotNull DataContext dataContext) {
        System.out.println("com.janita.plugin.replace.handler.ReplaceChineseCharTypedHandler.execute");
        Document document = editor.getDocument();
        Project project = editor.getProject();
        CaretModel caretModel = editor.getCaretModel();
        Caret primaryCaret = caretModel.getPrimaryCaret();
        int offset = primaryCaret.getOffset();
        String inputStr = String.valueOf(originInputChar);
        String replacedValue = cachedReplaceCharMap.get(inputStr);
        if (lastChar == ReplaceChineseCharConstants.BIAS_LINE && replacedValue != null) {
            Runnable runnable = () -> {
                document.deleteString(offset - 1, offset);
                document.insertString(offset - 1, inputStr);
                primaryCaret.moveToOffset(offset);
            };
            WriteCommandAction.runWriteCommandAction(project, runnable);
            this.lastChar = originInputChar;
            return;
        }
        if (replacedValue != null) {
            char finalShowChar = replacedValue.charAt(0);
            this.typedActionHandler.execute(editor, finalShowChar, dataContext);
            this.lastChar = originInputChar;
            System.out.println("字符：" + originInputChar + " 被替换为：" + finalShowChar);
            return;
        }
        this.typedActionHandler.execute(editor, originInputChar, dataContext);
        this.lastChar = originInputChar;
        System.out.println("本次输入不需要替换");
    }
}
