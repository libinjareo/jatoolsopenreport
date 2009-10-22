package jatools.designer.property.editor.printstyle;

import jatools.designer.Main;
import jatools.engine.css.PrintStyle;
import jatools.swingx.Chooser;
import jatools.swingx.CommandPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;



/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.5 $
  */
public class PrintStylePropertyEditor extends JDialog implements Chooser {
    String result;
    boolean done;
    private CrossTabRulePanel crossTabPanel = null;
    
    private VisibleRulePanel visiblePanel = null;
    private RepeatRulePanel repeatPanel = null;
    private PageRulePanel pagePanel = null;
    private LayoutRulePanel layoutPanel = null;
    private TextRulePanel textPanel = null;

    /**
     * Creates a new BackgroundImageEditor object.
     */
    public PrintStylePropertyEditor() {
        super(Main.getInstance(), "��ӡ��ʽ����", true);

        this.getContentPane().add(this.getTabbedPanel(), BorderLayout.CENTER);

        ActionListener oklistener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    done = true;
                    result = getPrintStyle();

                    hide();
                }
            };

        ActionListener cancellistener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    done = false;
                    hide();
                }
            };

        CommandPanel commandPanel = CommandPanel.createPanel(false);
        commandPanel.addComponent("ȷ��", oklistener);
        commandPanel.addComponent("ȡ��", cancellistener);

        commandPanel.addComponent("Ϊ��",
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    done = true;
                    hide();
                }
            });

        this.getContentPane().add(commandPanel, BorderLayout.SOUTH);
        setSize(new Dimension(450, 350));
    }

    /**
     * Creates a new PrintPanel object.
     */
    JTabbedPane getTabbedPanel() {
        JTabbedPane tab = new JTabbedPane();

        textPanel = new TextRulePanel();
        crossTabPanel = new CrossTabRulePanel();
        
        
        visiblePanel = new VisibleRulePanel();
        repeatPanel = new RepeatRulePanel();
        pagePanel = new PageRulePanel();
        layoutPanel = new LayoutRulePanel();
        tab.addTab("�ı�", textPanel);
        tab.addTab("�����", crossTabPanel);
   
        
        tab.addTab("�ɼ���", visiblePanel);
        tab.addTab("ѭ��", repeatPanel);
        tab.addTab("��ҳ", pagePanel);
        tab.addTab("��ӡ�󲼾�", layoutPanel);

        return tab;
    }

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */

    /**
     * DOCUMENT ME!
     *
     * \"[^\"]*\"
     *
     * @param owner DOCUMENT ME!
     * @param value DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean showChooser(JComponent owner, Object value) {
        this.done = false;
        this.result = null;
        this.setPrintStyle((String) value);
        this.setLocationRelativeTo(owner);
        show();

        return this.done;
    }

    private void setPrintStyle(String src) {
        PrintStyle printStyle = new PrintStyle(src);
        this.textPanel.setRule(printStyle.getTextRule());
        this.visiblePanel.setRule(printStyle.getVisibleRule());
        this.repeatPanel.setRule(printStyle.getRepeatRule());
        this.pagePanel.setRule(printStyle.getPageRule());
        this.layoutPanel.setRule(printStyle.getLayoutRule());
        this.crossTabPanel.setRule(printStyle.getCrossTabRule());
        
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getValue() {
        return this.result;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getPrintStyle() {
        StringBuffer result = new StringBuffer();

        boolean done = false;
        Object rule = this.textPanel.getRule();

        if (rule != null) {
            result.append(rule.toString());

            done = true;
        }

        rule = this.visiblePanel.getRule();

        if (rule != null) {
            result.append(rule.toString());

            done = true;
        }

        rule = this.repeatPanel.getRule();

        if (rule != null) {
            result.append(rule.toString());

            done = true;
        }

        rule = this.pagePanel.getRule();

        if (rule != null) {
            result.append(rule.toString());

            done = true;
        }

        rule = this.layoutPanel.getRule();

        if (rule != null) {
            result.append(rule.toString());

            done = true;
        }

        rule = this.crossTabPanel.getRule();

        if (rule != null) {
            result.append(rule.toString());

            done = true;
        }
        
       

        return done ? result.toString() : null;
    }
}
