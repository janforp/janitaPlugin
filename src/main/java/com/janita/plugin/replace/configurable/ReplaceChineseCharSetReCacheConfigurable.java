package com.janita.plugin.replace.configurable;

import com.intellij.openapi.options.SearchableConfigurable;
import com.janita.plugin.replace.constant.ReplaceChineseCharConstants;
import com.janita.plugin.replace.handler.ReplaceChineseCharTypedHandler;
import com.janita.plugin.replace.util.CacheUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 类说明：设置面板以及重新加载缓存
 *
 * @author zhucj
 * @since 2020/3/8 - 下午12:35
 */
@SuppressWarnings("all")
public class ReplaceChineseCharSetReCacheConfigurable implements SearchableConfigurable {

    private static final int MAX_CACHE_LENGTH = 45;

    /**
     * 主面板
     */
    private JPanel settingPanel;

    /**
     * 被替换输入框
     */
    private JTextField[] keyTextFields;

    /**
     * 替换值输入框
     */
    private JTextField[] valueTextFields;

    /**
     * 默认按钮
     */
    private JLabel btnDefault;

    @NotNull
    @Override
    public String getId() {
        System.out.println("com.janita.plugin.replace.configurable.ReplaceChineseCharSetReCacheConfigurable.getId");
        return "ReplaceChineseChar";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        System.out.println("com.janita.plugin.replace.configurable.ReplaceChineseCharSetReCacheConfigurable.getDisplayName");
        return this.getId();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        System.out.println("com.janita.plugin.replace.configurable.ReplaceChineseCharSetReCacheConfigurable.createComponent");
        if (settingPanel != null) {
            settingPanel.repaint();
            return settingPanel;
        }
        settingPanel = new JPanel();
        settingPanel.setLayout(null);
        //被替换输入框
        keyTextFields = new JTextField[MAX_CACHE_LENGTH];
        //替换值输入框
        valueTextFields = new JTextField[MAX_CACHE_LENGTH];
        //下标
        JLabel[] indexLabels = new JLabel[MAX_CACHE_LENGTH];
        //箭头
        JLabel[] arrowLabels = new JLabel[MAX_CACHE_LENGTH];
        for (int i = 0; i < MAX_CACHE_LENGTH; i++) {
            keyTextFields[i] = new JTextField();
            valueTextFields[i] = new JTextField();
            indexLabels[i] = new JLabel();
            arrowLabels[i] = new JLabel();
            keyTextFields[i].setBounds(35 + (i / 15) * 200, 32 * (i % 15), 60, 32);
            valueTextFields[i].setBounds(120 + (i / 15) * 200, 32 * (i % 15), 60, 32);
            indexLabels[i].setBounds(5 + (i / 15) * 200, 32 * (i % 15), 30, 32);
            arrowLabels[i].setBounds(95 + (i / 15) * 200, 32 * (i % 15), 25, 32);
            indexLabels[i].setText((i + 1) + ".");
            arrowLabels[i].setText("->");
            indexLabels[i].setHorizontalAlignment(JLabel.CENTER);
            arrowLabels[i].setHorizontalAlignment(JLabel.CENTER);
            keyTextFields[i].setHorizontalAlignment(JLabel.CENTER);
            valueTextFields[i].setHorizontalAlignment(JLabel.CENTER);
            settingPanel.add(keyTextFields[i]);
            settingPanel.add(valueTextFields[i]);
            settingPanel.add(indexLabels[i]);
            settingPanel.add(arrowLabels[i]);
        }
        btnDefault = new JLabel();
        btnDefault.setText("恢复默认");
        btnDefault.setForeground(Color.BLUE);
        btnDefault.setBounds(30, 32 * 15, 60, 32);
        btnDefault.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDefault.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int confirmDialogResponse = JOptionPane.showConfirmDialog(settingPanel, "确定恢复默认吗?", getId(), JOptionPane.YES_NO_OPTION);
                if (confirmDialogResponse == 0) {
                    CacheUtils.setCacheValue(ReplaceChineseCharConstants.DEFAULT_CACHE_VALUE);
                    ReplaceChineseCharTypedHandler.reloadReplaceCharMap();
                    reset();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //empty
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //empty
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnDefault.setText("<html><u>恢复默认</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDefault.setText("<html><u>恢复默认</u></html>");
            }
        });
        settingPanel.add(btnDefault);
        return settingPanel;
    }

    @Override
    public boolean isModified() {
        System.out.println("com.janita.plugin.replace.configurable.ReplaceChineseCharSetReCacheConfigurable.isModified");
        String oldValue = CacheUtils.getCacheValue().trim();
        String newValue = getConfigStringFromTextFields().trim();
        boolean modified = !StringUtils.equals(oldValue, newValue);
        if (modified) {
            System.out.println("用户进行了猛如虎的修改");
        }
        return modified;
    }

    /**
     * 获取设置中的值
     *
     * @return 设置中的值
     */
    private String getConfigStringFromTextFields() {
        System.out.println("com.janita.plugin.replace.configurable.ReplaceChineseCharSetReCacheConfigurable.getConfigStringFromTextFields");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_CACHE_LENGTH; i++) {
            JTextField jTextField1 = keyTextFields[i];
            String leftValue = jTextField1.getText().trim();
            JTextField jTextField2 = valueTextFields[i];
            String rightValue = jTextField2.getText().trim();
            builder.append(leftValue)
                    .append(ReplaceChineseCharConstants.SEP_CHAR)
                    .append(rightValue)
                    .append(ReplaceChineseCharConstants.SEP_CHAR);
        }
        return builder.toString().trim();
    }

    @Override
    public void apply() {
        System.out.println("com.janita.plugin.replace.configurable.ReplaceChineseCharSetReCacheConfigurable.apply");
        String updatedValue = getConfigStringFromTextFields().trim();
        CacheUtils.setCacheValue(updatedValue);
        //重新加载处理器的缓存
        ReplaceChineseCharTypedHandler.reloadReplaceCharMap();
    }

    @Override
    public void reset() {
        System.out.println("com.janita.plugin.replace.configurable.ReplaceChineseCharSetReCacheConfigurable.reset");
        String settingStr = CacheUtils.getCacheValue();
        String[] settingArr = settingStr.split(ReplaceChineseCharConstants.SEP_CHAR);
        //每次打开面板的时候，重新渲染输入框的值
        for (int i = 0; i < MAX_CACHE_LENGTH; i++) {
            JTextField jTextField1 = keyTextFields[i];
            String value1 = (2 * i) < settingArr.length ? settingArr[2 * i].trim() : "";
            jTextField1.setText(value1);

            JTextField jTextField2 = valueTextFields[i];
            String value2 = (2 * i + 1) < settingArr.length ? settingArr[2 * i + 1].trim() : "";
            jTextField2.setText(value2);
        }
    }
}
