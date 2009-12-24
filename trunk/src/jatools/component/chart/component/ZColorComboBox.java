package jatools.component.chart.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ListCellRenderer;





/**
 * ��Ͽ�ʽ����ɫѡ����
 * �Դ��Զ���ĵ���list������,��ѡ����ֻ��һ�ѡ�����ʼ��ѡ��
 * @version $Revision: 1.1 $
 * @author $author$
 */
public class ZColorComboBox extends JComboBox implements ListCellRenderer/*�Զ���ĵ���list������*/
{
    public static final int NULL_BOX_VISIBLE = 1;
    public static final int OTHER_BOX_VISIBLE = 2;
    public static final String PROPERTY_POPUP_CLOSED = "popup_closed"; //
    private static final Dimension HEAD_ICON_SIZE = new Dimension(20, 10);

    // ��ɫ�壬Ҳ�ǵ���ʽ�б�Ļ�����
    private ZColorPallette listRenderer;

    // ��һ����Ŀ����ʼ��ֻ��һ����ɫ���󣬶���ʼ��ѡ����һ���������ı�ΨһԪ��
    // 1.����setColor ��ʼ��ɫʱ���ı�˼��ϵ�ΨһԪ��
    // 2.������ʽ�б�ر�ʱ�� �����б��м���ɫ����ȡ��
    private Color color = Color.black;

    // ����Ͽ��ģ�ͣ���colors��Ϊ��ѡ��Ŀ
    private DefaultComboBoxModel model = new DefaultComboBoxModel();
    
    private FillStyleInterface style;

    /**
    * Creates a new ZColorPicker object.
    */
    public ZColorComboBox() {
        this(OTHER_BOX_VISIBLE | NULL_BOX_VISIBLE);
    }

    
    /**
     * Creates a new ZColorComboBox object.
     *
     * @param textVisible DOCUMENT ME!
     */
    public ZColorComboBox(int textVisible) {
        // ���Ψһ��Ŀ����Ĭ��Ϊ��ɫ��һ���ͨ��setColor�����޸�
        model.addElement(color);

        setModel(model);


        //������ɫ���б������
        listRenderer = new ZColorPallette(textVisible);


        // ������Ͽ򵽴˵��ҵ��б������
        setRenderer(this);

        // ����ui,��ui���Զ��Ƶ���ʽ�б�Ĵ�С����ʱȡ������ʽ�б��ʵ��
        ZFixedSizeComboBoxUI ui = new ZFixedSizeComboBoxUI();
        setUI(ui);


        // ȡ���б��Ա㽫����¼�������ɫ�壬������ɫ����Գ���rollover���
        listenToList(ui.getPopupList());


        // ָ���һ��
        setSelectedIndex(0);


        // ���ɱ༭
        setEditable(false);

        JComponent jc = (JComponent) this.getEditor().getEditorComponent();
        jc.setBorder(BorderFactory.createLineBorder(Color.red)); // . .setBackground(Color.red)  ;
    }

    /**
    * DOCUMENT ME!
    *
    * @param color DOCUMENT ME!
    */
    public void setColor(Color color) {
        this.color = color;
        //setSelectedItem(this.color);
        repaint();
    }

    public void setFillStyle(FillStyleInterface style){
    	this.style = style;
    	repaint();
    }
    public FillStyleInterface getFillStyle(){
    	return style;
    }
    /**
    * DOCUMENT ME!
    *
    * @return DOCUMENT ME!
    */
    public Color getColor() {
        return color;
    }

    /**
    * DOCUMENT ME!
    *
    * @param list DOCUMENT ME!
    * @param value DOCUMENT ME!
    * @param index DOCUMENT ME!
    * @param isSelected DOCUMENT ME!
    * @param cellHasFocus DOCUMENT ME!
    *
    * @return DOCUMENT ME!
    */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, 
                                                  boolean cellHasFocus) {
        listRenderer.setColor(color);

        if(index == -1){
           	return style.createLabel(getSize());
         }

        // ���index == -1,��ָʾ����������Ƕ����Ͽ��У������Ǵ��ڵ���״̬
        listRenderer.setAtHeader(index == -1);

        return listRenderer;
    }

    /**
    * ��������ʽ�б�
    *
    * ȡ���б��Ա㽫����¼�������ɫ�壬������ɫ����Գ���rollover���
    *
    * @param list ����ʽ�б�
    */
    private void listenToList(JList list) {
        list.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                listRenderer.mousePressed(e);
                color = listRenderer.getColor();
                setSelectedIndex(0);
            }

            public void mouseReleased(MouseEvent e) {
                // ֪ͨ�б��ر���
                firePropertyChange(PROPERTY_POPUP_CLOSED, false, true);
            }

            public void mouseExited(MouseEvent e) {
                listRenderer.mouseExited(e);
            }
        });
        list.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                listRenderer.mouseMoved(e);
            }
        });
    }

    /**
     * DOCUMENT ME!
     *
     * @param argv DOCUMENT ME!
     */
    public static void main(String[] argv) {
        ZColorComboBox content = new ZColorComboBox();
        SingleColor s = new SingleColor(Color.RED);
        content.setFillStyle(s);
        JDialog d = new JDialog((Frame) null, "Just For Test !"); //
        d.setModal(true);
        d.getContentPane().add(content, BorderLayout.CENTER);
        d.pack();
        d.show();
    }
}