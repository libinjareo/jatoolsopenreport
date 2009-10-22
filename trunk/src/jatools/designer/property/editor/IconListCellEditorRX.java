package jatools.designer.property.editor;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


/**
 * @author   java9
 */
public class IconListCellEditorRX extends AbstractCellEditor
    implements TableCellEditor, TableCellRenderer {
    public static String PROPERTY_VALUE = "value"; //
    JComboBox comboBox;
    IconListRenderer listRenderer;

    /**
 * Creates a new ZColorCBBAsCellEditor object.
 */
    public IconListCellEditorRX(Object[] objects, Icon icon) {
        comboBox = new JComboBox(objects);
        listRenderer = new IconListRenderer(icon);
        comboBox.setRenderer(listRenderer);

        ZBasicComboBoxUI ui = new ZBasicComboBoxUI();
        comboBox.setUI(ui);

        JList list = ui.getPopupList();
        list.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                stopCellEditing();
            }
        });
    }

    /**
 * JTable Ҫ��һ��cell�༭��
 * <p>TableCellEditor ����</p>
 *
 * @param table ����һ��tableҪ��༭��
 * @param value ��Ԫ���е���ֵ
 * @param isSelected ��Ԫ���Ƿ�ѡ��
 * @param row ��Ԫ��������!
 * @param column ��Ԫ��������
 *
 * @return ���ر༭��������colorCombo��
 */
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        comboBox.setSelectedItem(value);

        return comboBox;
    }

    /**
 * DOCUMENT ME!
 *
 * @param table DOCUMENT ME!
 * @param value DOCUMENT ME!
 * @param isSelected DOCUMENT ME!
 * @param hasFocus DOCUMENT ME!
 * @param row DOCUMENT ME!
 * @param column DOCUMENT ME!
 *
 * @return DOCUMENT ME!
 */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, 
                                                   int row, int column) {
        return listRenderer.getListCellRendererComponent(null, value, 0, false, false);
    }

    /**
 * table ��֪�༭���༭����������ȡ��ֵ
 *
 * <p>TableCellEditor ����</p>
 * @return ��ֵ
 */
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

    class ZBasicComboBoxUI extends BasicComboBoxUI {
        public JList getPopupList() {
            return listBox;
        }
    }
}