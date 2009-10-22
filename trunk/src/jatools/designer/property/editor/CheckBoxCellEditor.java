package jatools.designer.property.editor;

import jatools.swingx.AbstractNode;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;



/**
 * boolean ֵtable cell �༭��
 * ��һ��JCheckBox ���ʵ��
 * @version $Revision: 1.2 $
 * @author $author$
 */
public class CheckBoxCellEditor extends AbstractCellEditor
    implements TableCellEditor, TableCellRenderer {
   
    JCheckBox checkBox = new JCheckBox();

    /**
     * Creates a new ZCheckBoxCellEditor object.
     */
    public CheckBoxCellEditor() {
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkBox.setIcon(checkBox.isSelected() ? AbstractNode.CHECKED_ICON : AbstractNode.NO_CHECK_ICON  );
                stopCellEditing();
            }
        });
        

        checkBox.setBackground(Color.white);
        checkBox.setFocusable(false);
    }

    /**
     * JTable Ҫ��һ��cell������
     * <p>TableCellRenderer ��Ψһ����</p>
     *
     * @param table ����һ��tableҪ�������
     * @param value ��Ԫ���е���ֵ
     * @param isSelected ��Ԫ���Ƿ�ѡ��
     * @param hasFocus  ��ǰ��Ԫ���Ƿ��н���
     * @param row ��Ԫ��������!
     * @param column ��Ԫ��������
     *
     * @return ���ر༭��������colorCombo��
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, 
                                                   int row, int column) {
        try {
            resetCheckBox(((Boolean) value).booleanValue());
        } catch (Exception ex) {
        	ex.printStackTrace() ;
        }

        return checkBox;
    }

    /**
     * ������ֵ�������������
     *
     * @param selected ��ǰֵ�Ƿ�Ϊ��
     */
    private void resetCheckBox(boolean selected) {
        checkBox.setSelected(selected);
    	checkBox.setIcon(checkBox.isSelected() ? AbstractNode.CHECKED_ICON : AbstractNode.NO_CHECK_ICON  );
//        if (selected) {
//            checkBox.setText(TRUE_TEXT);
//        } else {
//            checkBox.setText(FALSE_TEXT);
//        }
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
        try {
            resetCheckBox(((Boolean) value).booleanValue());
        } catch (Exception ex) {
        	ex.printStackTrace() ;
        }

        return checkBox;
    }

    /**
     * table ��֪�༭���༭����������ȡ��ֵ
     *
     * <p>TableCellEditor ����</p>
     * @return ��ֵ
     */
    public Object getCellEditorValue() {
        return new Boolean(checkBox.isSelected());
    }
}