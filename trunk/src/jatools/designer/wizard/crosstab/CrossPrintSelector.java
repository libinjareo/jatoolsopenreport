package jatools.designer.wizard.crosstab;

import jatools.designer.wizard.BuilderContext;
import jatools.swingx.TitledSeparator;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>Title: CrossPrintSelector </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Jatools</p>
 * @author Jiang Dehua
 * @version 1.0
 */
public class CrossPrintSelector extends JPanel{
  private JComboBox columnHeaderBox;
  private JComboBox rowHeaderBox;
  private JComboBox pagewrapBox;
  static final String[] values={"��ҳ�ɼ�","ÿҳ�ɼ�"};
  static final String[] css={"firstpage","everypage"};
  static final String[] pagewrap={"��ҳ","����ҳ"};
  static final String[] wrapCss={ "true","false"};

  public CrossPrintSelector() {
    super();
    initUI();
  }
  private void initUI(){
    columnHeaderBox=new JComboBox(values);
    rowHeaderBox=new JComboBox(values);
    pagewrapBox=new JComboBox(pagewrap);

    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
    TitledSeparator titleSeparator = new TitledSeparator("��������");
    titleSeparator.setMaximumSize(new Dimension(4000, 20));
    add(titleSeparator);
    JPanel panel=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    JLabel label=new JLabel("�б��⣺");
    panel.add(label);
    panel.add(columnHeaderBox);
    columnHeaderBox.setPreferredSize(new Dimension(120, 23));
    panel.setMaximumSize(new Dimension(4000, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
    add(panel);

    panel=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    label=new JLabel("�б��⣺");
    panel.add(label);
    panel.add(rowHeaderBox);
    rowHeaderBox.setPreferredSize(new Dimension(120, 23));
    panel.setMaximumSize(new Dimension(4000, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
    add(panel);


    TitledSeparator wrapSeparator = new TitledSeparator("��ҳ����");
    wrapSeparator.setMaximumSize(new Dimension(4000, 20));
    add(wrapSeparator);
    panel=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    label=new JLabel("�Զ���ҳ��");
    panel.add(label);
    panel.add(pagewrapBox);
    pagewrapBox.setPreferredSize(new Dimension(120, 23));
    panel.setMaximumSize(new Dimension(4000, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
    add(panel);

    add(Box.createVerticalGlue());
    setBorder(BorderFactory.createEmptyBorder(30, 50, 50, 50));
  }
  public void apply(BuilderContext context) {

    String top = "crosstab-top-header-visible:" +
        css[columnHeaderBox.getSelectedIndex()];
    String left ="crosstab-left-header-visible:" +
        css[rowHeaderBox.getSelectedIndex()];
    String pageWrap="crosstab-page-wrap:"+wrapCss[0];

    String cssString=top+";"+left+";"+pageWrap;

    context.setValue(CrossTabStyler.PRINTCSS, cssString);

  }

}
