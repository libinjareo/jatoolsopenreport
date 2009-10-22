package jatools.designer.property.editor;

import jatools.swingx.Chooser;
import jatools.swingx.MoreButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;



/**
 * @author   java9
 */
public class CellEditorWithCustomEditor extends AbstractCellEditor
    implements TableCellEditor {
    TableCellEditor inplaceEditor; //Ƕ�뵥Ԫ���еı༭��
    Chooser chooser; //��Ӧ������ѡ����
    Object value; // ��ǰ�༭����ֵ
    JPanel editorContainer; // Ƕ�뵥Ԫ���е�pane
    JButton chooserButton;
    JComponent owner;

    /**
    * Creates a new ZCellEditorWithCustomEditor object.
    * @param inplaceEditor_ Ƕ�뵥Ԫ���еı༭��
    * @param chooser_ ��� [...] ��Ӧ������ѡ����
    *
    * <p> ע�⣬���캯��������ʵ�ʵı༭���������JTextField,JComboBox ������뵽editorContainer��</p>
    * <p> �༭��������  getTableCellEditorComponent �м���</p>
    * @see #getTableCellEditorComponent
    */
    public CellEditorWithCustomEditor(JComponent owner, Chooser chooser) {
       this(owner,null, chooser);
    }

    /**
    * Creates a new ZCellEditorWithCustomEditor object.
    *
    * @param inplaceEditor DOCUMENT ME!
    * @param chooser DOCUMENT ME!
    */
    public CellEditorWithCustomEditor(JComponent owner,
                                       TableCellEditor inplaceEditor,
                                       Chooser chooser) {
        this.owner = owner;
        this.inplaceEditor = inplaceEditor;
        this.chooser = chooser;


        //����ui/////////////////////////////////////////////////
        editorContainer = new JPanel(new BorderLayout()); // BorderLayout ����������Ը߶ȣ�ȡ��ȣ��ϱ���֮
                                                          //      �м������


        // BorderLayout ����������Ը߶ȣ�ȡ��ȣ��ϱ���֮
        //      �м������
        chooserButton = new MoreButton(); // ������ť //
       // chooserButton.setMargin(new Insets(0, 0, 0, 0));

        editorContainer.add(chooserButton, "East"); //
        editorContainer.setBackground(Color.white);

        chooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               

                // ����ѡ����
                if (CellEditorWithCustomEditor.this.chooser.showChooser(CellEditorWithCustomEditor.this.owner,value)) {
                    // ����޸������ԣ��͸���JTable
                    value = CellEditorWithCustomEditor.this.chooser.getValue();
                    stopCellEditing();
                } else {
                    // ��������޸ģ�Ҳ����JTable
                    cancelCellEditing();
                }
            }
        });


        // ˫��ʱ������chooser
        editorContainer.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    chooserButton.doClick();
                }
            }
        });

        if (this.inplaceEditor != null) {
            //��Ƕ��ʽ�༭���еõ���Ϣ///////////////////////////////////////////////
            this.inplaceEditor.addCellEditorListener(new CellEditorListener() {
                public void editingStopped(ChangeEvent e) {
                    value = CellEditorWithCustomEditor.this.inplaceEditor.getCellEditorValue();


                    // ���Ƕ��ı༭���༭��ɣ�������ң��Ա��ҿ���ת��JTable
                    fireEditingStopped();
                }

                public void editingCanceled(ChangeEvent e) {
                    // ���Ƕ��ı༭��ȡ���˱༭Ҳ�������
                    fireEditingCanceled();
                }
            });
        }
    }

    /**
    * JTable Ҫ��һ��cell�༭��
    *
    * ��Ƕ��༭����ȡ�ñ༭����������װ��༭�������������������ظ�table,���Ե�Ԫ����ʵ��Ƕ�����һ������
    *
    * <p>TableCellEditor ����</p>
    *
    * @param table ����һ��tableҪ��༭��
    * @param value ��Ԫ���е���ֵ
    * @param isSelected ��Ԫ���Ƿ�ѡ��
    * @param row ��Ԫ��������!
    * @param column ��Ԫ��������
    *
    * @return ���ر༭��������editorContainer��
    */
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        Component ie = null;

        if (inplaceEditor == null) {
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            ie = renderer.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        } else {
            ie = inplaceEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
        }

        editorContainer.add(ie, "Center"); //
        this.value = value;

        return editorContainer;
    }
    
	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
	 */
	public boolean isCellEditable(EventObject e) {
		
		if(inplaceEditor != null)
		{
			inplaceEditor.isCellEditable( e);
		}
		// TODO Auto-generated method stub
		return super.isCellEditable(e);
	}
    /**
    * table ��֪�༭���༭����������ȡ��ֵ
    *
    * <p>TableCellEditor ����</p>
    * @return ��ֵ
    */
    public Object getCellEditorValue() {
        return value;
    }
}