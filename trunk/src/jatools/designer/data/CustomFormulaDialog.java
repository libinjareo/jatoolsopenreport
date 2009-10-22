package jatools.designer.data;

import jatools.data.Formula;
import jatools.designer.Main;
import jatools.designer.VariableTreeModel;
import jatools.engine.AmbiguousNameNodePattern;
import jatools.engine.PrintConstants;
import jatools.swingx.CommandPanel;
import jatools.swingx.MessageBox;
import jatools.swingx.SimpleTreeNode;
import jatools.swingx.scripteditor.ScriptEditor;
import jatools.util.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;




/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class CustomFormulaDialog extends JDialog implements ActionListener {
    static final String[] FUNCTIONS = new String[] {
            "����ֵ�ͺ���", "double abs(double)", "double asin(double)", "double acos(double)",
            "double atan(double)", "double atan2(double,double)", "double ceil(double)",
            "double cos(double)", "double exp(double)", "double floor(double)",
            
            "double log(double)", "double max(double,double)", "double min(double,double)",
            "double pow(double,double)", "double random()", "double rint(double)",
            "double sin(double)", "double sqrt(double)", "double tan(double)",
            "double toDegrees(double)", "double toRadians(double)", "float abs(float)",
            "float max(float,float)", "float min(float,float)", "int abs(int)", "int max(int,int)",
            "int min(int,int)", "int round(float)", "long abs(long)", "long max(long,long)",
            "long min(long,long)", "long round(double)",
            
            "�ַ�������", "int length(String)", "char charAt(String, int)",
            
            "boolean startsWith(String, String)", "boolean endsWith(String, String)",
            
            "int indexOf(String, String)",
            
            "int lastIndexOf(String, String)",
            
            "String substring(String, int)", "String substring(String, int,int)",
            
            "String replaceAll(String, String , String )",
            
            "String[] split(String, String)",
            
            "String toLowerCase(String)",
            
            "String toUpperCase(String )",
            
            "���ں���",
            
            "int getYear(Date )", "int getMonth(Date )", "int getDate(Date )", "int getDay(Date )",
            "int getHours(Date )", "int getMinutes(Date )", "int getSeconds(Date )",
            "boolean before(Date,Date )", "boolean after(Date,Date )",
            
            "����ת������",
            
            "String toRmbString(double)", "String toHZYear(int)", "String toHZMonth(int)",
            "String toHZDay(int)",
            
            "String format(float,String)", "String format(double,String)",
            "String format(Date,String)", "�������ͺ���", "String p(String )",
            "String clobString(String )"
        };
    static final HashMap tips = new HashMap(FUNCTIONS.length);
    IconTree functionTree;
    VariableTree varTree;
    JButton check;
    JButton ok;
    JButton cancel;
    ScriptEditor textArea;
    private Formula formula;
    protected boolean nullPermitted;
    JLabel tipLabel;
   

    /**
     * Creates a new CustomFormulaDialog object.
     *
     * @param owner DOCUMENT ME!
     * @param astemp DOCUMENT ME!
     */
    public CustomFormulaDialog(Frame owner, boolean astemp) {
        this(owner, true, false, astemp);
    }

    /**
     * Creates a new CustomFormulaDialog object.
     *
     * @param owner DOCUMENT ME!
     * @param showVariable DOCUMENT ME!
     * @param nullPermitted DOCUMENT ME!
     * @param astemp DOCUMENT ME!
     */
    public CustomFormulaDialog(Frame owner, boolean showVariable, boolean nullPermitted,
        boolean astemp) {
        super(owner, "��ʽ����", true);

        varTree = new VariableTree();

        TreeModel variableModel = null;

        if ((Main.getInstance() != null) && (Main.getInstance().getActiveEditor() != null)) {
            variableModel = Main.getInstance().getActiveEditor().getVariableModel();
        } else {
            variableModel = VariableTreeModel.getDefaults();
        }

        varTree.setModel(variableModel);

        varTree.setShowPopupMenu(false);

        varTree.setDoubleClickAction(this);

        functionTree = createFunctionTree();

        JComponent first = null;

        tips.put("double abs(double)", "��ȡ���������ľ���ֵ,abs(-12.345)��ֵΪ12.345");
        tips.put("double asin(double)", "��ȡ���������ķ�����ֵ ,asin(1)��ֵ��1.57");
        tips.put("double acos(double)", "��ȡ���������ķ�����ֵ,acos(0)��ֵ��1.57");
        tips.put("double atan(double)", "��ȡ���������ķ�����ֵ,atan(1)��ֵ��0.78");
        tips.put("double atan2(double,double)", "��ȡ���������ķ�����ֵ,acos(0)��ֵ��1.57");
        tips.put("double ceil(double)", "��ȡ��С�ڲ����������������,ceil(-12.3)��ֵ��-12.0.");
        tips.put("double cos(double)", "��ȡ��������������ֵ,cos(0)��ֵ��1.0");
        tips.put("double exp(double)", "��ȡ��Ȼ����e��d���ݷ�,exp(1)��ֵ��2.71");
        tips.put("double floor(double)", "��ȡ�����ڲ����������������,floor(12.34)��ֵ��12.0");
        tips.put("double log(double)", "��ȡ����Ϊe����Ϊ�ò�������������ֵ,log(1.23)��ֵ��0.207");
        tips.put("double max(double,double)", "��ȡ�������������нϴ��һ��,max(1.23,1.24)��ֵ��1.24");
        tips.put("double min(double,double)", "��ȡ�������������н�С��һ��,min(1.23,1.24)��ֵ��1.23");
        tips.put("double pow(double,double)", "��ȡ��������1�Ĳ�������2�η�֮���ֵ ,pow(2.5,3)��ֵ��15.625");
        tips.put("double random()", "��ȡ0.0��1.0֮��һ�������,random()��ֵ��0.12");
        tips.put("double rint(double)", "��ȡ����������������֮���ֵ,rint(12.34)��ֵ��12.0");
        tips.put("double sin(double)", "��ȡ��������������ֵ,sin(0)��ֵ��0.0");
        tips.put("double sqrt(double)", "��ȡ����������ƽ��֮���ֵ,sqrt(6.25)��ֵ��2.");
        tips.put("double tan(double)", "��ȡ��������������ֵ,tan(1.5)��ֵ��14.10");
        tips.put("double toDegrees(double)", "��ȡ����Ϊe����Ϊ�ò�������������ֵ,log(1.23)��ֵ��0.207");
        tips.put("double toRadians(double)", "��ȡ��ò��������������Ӧ�ĽǶ�,toDegrees(1.57)��ֵ��89.95");
        tips.put("float abs(float)", "��ȡ�����Ͳ��������ľ���ֵ,abs(-12.345f)��ֵ��12.345");
        tips.put("float max(float,float)", "��ȡ���������Ͳ��������бȽϴ��һ��,max(1.23f,1.24f)��ֵ��1 ");
        tips.put("float min(float,float)", "��ȡ���������Ͳ��������бȽ�С��һ��, min(1.23f,1.24f)��ֵ��1.23.");
        tips.put("int abs(int)", "��ȡ���������Ͳ��������бȽ�С��һ��,min(1.23f,1.24f)��ֵ��1.23.");
        tips.put("int max(int,int)", "��ȡ�������Ͳ��������бȽϴ��һ��,max(23,24)��ֵ��24  .");
        tips.put("int min(int,int)", "��ȡ�������Ͳ��������бȽ�С��һ��,min(23,24).");
        tips.put("int round(float)", "��ȡ���ش��ڸ����ĸ����Ͳ�����������С���ͱ���,round(-12.34f)��ֵ��.");
        tips.put("long abs(long)", "��ȡ�����Ͳ��������ľ���ֵ,abs(-12345678.9)��ֵ��1.23456789E7");
        tips.put("long max(long,long)", "��ȡ���������Ͳ��������бȽϴ��һ��,max(1234,1235)��ֵ��1235");
        tips.put("long min(long,long)", "��ȡ�������������бȽ�С��һ��,min(1.23f,1.24f)��ֵ��1.23");
        tips.put("long round(double)", "��ȡ���ش���˫���Ȳ�����������С���ͱ���,round(-12.34)��ֵ��-12.");

        tips.put("int length(String)", "��ȡ�ַ������������ĳ���,length(\"abc\")��ֵΪ3,length(\"��Һ�\")��ֵΪ6");
        tips.put("char charAt(String, int)",
            "��ȡ�ַ�������������λ��i+1λ�õ��ַ�,charAt(\"abcdefg\",3)��ֵΪd,charAt(\"abcdefg\",0)��ֵΪa.");
        tips.put("boolean startsWith(String, String)",
            "��ȡ��������1�Ƿ��Բ�������2Ϊ��ͷ���ݵĲ���ֵ,startsWith(\"afc\",\"b\")��ֵΪfalse,startsWith(\"afc\",\"a\")��ֵΪtrue");
        tips.put("boolean endsWith(String, String)",
            "��ȡ��������1�Ƿ��Բ�������2Ϊ��β���ݵĲ���ֵ,endsWith(\"afcccb\",\"asb\")��ֵΪfalse,endsWith(\"acccb\",\"ccb\")��ֵΪtrue");
        tips.put("int indexOf(String, String)",
            "��ȡ�ַ���2���ַ���1�г��ֵĵ�һ��λ��,indexOf(\"abcdefg\",\"de\")��ֵΪ3.");
        tips.put("int lastIndexOf(String, String)",
            "��ȡ�ַ���2���ַ���1�г��ֵ����һ��λ��,lastIndexOf(\"abcdabcdefg\",\"ab\")��ֵΪ4");
        tips.put("String substring(String, int)",
            "��ȡ�ַ����дӵ�i+1λ��ʼ���ַ���,substring(\"abcdefg\",2)��ֵΪ\"cdefg\".");
        tips.put("String substring(String, int,int)",
            "��ȡ�ַ������������ĳ���,length(\"abc\")��ֵΪ3,length(\"��Һ�\")��ֵΪ6");
        tips.put("String replaceAll(String, String , String )",
            "��ȡ���ַ���3�����ַ���1֮�е������ַ���2���õ������ַ���,\nreplaceAll(\"abcdeabcdeabcde\",\"bc\",\"xy\")��ֵΪ\"axydeaxydeaxyde\".");
        tips.put("String[] split(String, String)",
            "���ַ���1�����ַ���2�ĵط���ʼ���зָ�õ�һ���ַ�������,String str =\"xd::abc::cde\",String [] r= str.split(\"::\"),r����{\"xd\",\"abc\",\"cde\"}");
        tips.put("String toLowerCase(String)",
            "�ø�����λ�ù���,�Ѹ��ַ����е������ַ�ת��ΪСд,toLowerCase(\"aBcDeFg\",Locale.PRC);��ֵΪ\"abcdefg\".");
        tips.put("String toUpperCase(String )", "��ȡ���ַ���תΪ��д����ַ���,toUpperCase(\"acdEfg\")��ֵΪACDEFG.");

        tips.put("int getYear(Date )", "��ȡ����d�е���,������dֵΪ1970-12-31 00:00:00.0��getYear(d)��ֵΪ1970.");
        tips.put("int getMonth(Date )", "��ȡ����d�е���,d��ֵΪ1970-12-31 00:00:00.0,getMonth(d)��ֵΪ12 .");
        tips.put("int getDate(Date )", "��ȡ����d�е���,d��ֵΪ1970-12-31 00:00:00.0��getDate(d)��ֵΪ31.");
        tips.put("int getDay(Date )", "��ȡ����dΪһ�����ڵĵڼ���,d��ֵΪ1970-12-31 00:00:00.0��getDay(d)��ֵΪ4 .");
        tips.put("int getHours(Date )", "��ȡ����d�е�Сʱ,d��ֵΪ1970-12-31 00:00:00.0��getHours(d)��ֵΪ0.");
        tips.put("int getMinutes(Date )", "��ȡ����d�еķ���,d��ֵΪ1970-12-31 00:00:00.0��getMinutes(d)��ֵΪ0 .");
        tips.put("int getSeconds(Date )", "��ȡ����d�е���,d��ֵΪ1970-12-31 00:00:00.0��getSeconds(d)��ֵΪ0.");
        tips.put("boolean before(Date,Date )",
            "��ȡ����d�Ƿ������p��Ĳ���ֵ,d��ֵΪ1970-12-31 00:00:00.0��p��ֵΪ1981-10-10 00:00:00.0 before(d,p)��ֵΪtrue.");
        tips.put("int getYear(Date )", "��ȡ����d�е���,������dֵΪ1970-12-31 00:00:00.0��getYear(d)��ֵΪ1970.");
        tips.put("boolean after(Date,Date )",
            "��ȡ����d�Ƿ������p��Ĳ���ֵ,d��ֵΪ1970-12-31 00:00:00.0��p��ֵΪ1981-10-10 00:00:00.0after(d,p)��ֵΪfalse .");

        tips.put("String toRmbString(double)", "��ȡ˫���Ȳ�������d������Ҵ�д,toRmbString(12.34)��ֵΪ�� Ҽʰ��Ԫ�������� .");
        tips.put("String toHZYear(int)", "��ȡ��ݵĺ��ִ�д,toHZYear(2005)��ֵΪ�������� .");
        tips.put("String toHZMonth(int)", "��ȡ���ִ�д���·�,toHZMonth(12)��ֵΪҼʰ�� .");
        tips.put("String toHZDay(int)", "��ȡ���ִ�д������,toHZDay(12)��ֵΪҼʰ��.");
        tips.put("String format(float,String)",
            "��ȡ���ַ�����������s��ʽ��ʾ�ĵ����Ȳ�������f��ֵ,format(123456.789f,\"###,##0.000\")��ֵΪ123,456.789 .");
        tips.put("String format(double,String)",
            "��ȡ���ַ�����������s��ʽ��ʾ��˫���ȱ���d��ֵ,format(123.456,\"###,##0.00\")��ֵΪ123.46.");
        tips.put("String format(Date,String)",
            "��ȡ���ַ�����������s��ʽ��ʾ�����ڱ���d��ֵ,d��ֵΪ1970-12-31 00:00:00.0��format(d,\"yyyy-MM-dd\")��ֵΪ1970-12-31.");

        tips.put("String p(String )", "��ȡ��������ļ�jatools.properties�е�����ֵ,p(\"userid\")��ֵΪ875704628");
        tips.put("String clobString(String )",
            "ȡ�����ݼ��е�ĳһ����Ϊ�ò����������ֶε�ֵ,String memo = clobString(\"mydata.my_memo\"),��ʾ�������ݼ��е�my_memo�ֶ��У�ȡ��һ���ַ���ֵ��$my_memo�ֶ�����Ϊ BLOB");

        JPanel scrollPanel = new JPanel(new BorderLayout());
        tipLabel = new JLabel("������Ӧ��������ʾ�ú������÷����й�ʾ��", JLabel.LEFT);
        tipLabel.setHorizontalAlignment(JLabel.LEFT);
        tipLabel.setVerticalAlignment(JLabel.TOP);
        tipLabel.setPreferredSize(new Dimension(20, 50));

        if (varTree != null) {
            JTabbedPane tab = new JTabbedPane();
            tab.addTab("����", new JScrollPane(varTree));

            jatools.designer.variable.XmlSourceTree tree = new jatools.designer.variable.XmlSourceTree();
            tree.setToggleClickCount(10000);
            tree.setEnablePopup(false);

            if (((Main.getInstance() != null) && (Main.getInstance().getActiveEditor() != null)) &&
                    (Main.getInstance().getActiveEditor().getReport() != null)) {
                tree.initTreeData(Main.getInstance().getActiveEditor().getReport().getNodeSource());
            }

            tree.addPropertyChangeListener(new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals("select.value")) {
                            String selectedText = evt.getNewValue().toString();
                            textArea.replaceSelection(selectedText);
                            textArea.requestFocus();
                        }
                    }
                });
            tab.addTab("���ݼ�����", new JScrollPane(tree));

            scrollPanel.add(new JScrollPane(functionTree), "Center");
            scrollPanel.add(tipLabel, "South");
            tab.add("����", scrollPanel);

            first = tab;
        } else {
            first = new JScrollPane(functionTree);
        }

        JPanel formulaTextPanel = new JPanel(new BorderLayout());

        JLabel formulaLabel = new JLabel("��ʽ:", JLabel.LEFT);
        formulaTextPanel.add(formulaLabel, BorderLayout.NORTH);
        textArea = new ScriptEditor(astemp);

        JScrollPane bottom = new JScrollPane(textArea);
        formulaTextPanel.add(bottom);

        JSplitPane spthird = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
        spthird.setDividerLocation(193);
        spthird.setTopComponent(first);
        spthird.setBottomComponent(formulaTextPanel);

        getContentPane().add(spthird);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel leftButtonPanel = new JPanel(new FlowLayout());

        CommandPanel rightButtonPanel = CommandPanel.createPanel(false);

        check = new JButton(" ��֤ ");
        ok = new JButton("ȷ��");
        cancel = new JButton("ȡ��");

        JButton empty = new JButton("Ϊ��");

        ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    done();
                }
            });

        cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    hide();
                }
            });

        empty.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    empty();
                }
            });

        rightButtonPanel.addComponent(ok);
        rightButtonPanel.addComponent(cancel);

        if (nullPermitted) {
            rightButtonPanel.addComponent(empty);
        }

        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        this.nullPermitted = nullPermitted;

        pack();

        Dimension size = showVariable ? new Dimension(630, 480) : new Dimension(420, 430);
        setSize(size);
        this.setLocationRelativeTo(owner);
    }

    protected void empty() {
        textArea.setText(null);
        done();
    }

    private IconTree createFunctionTree() {
        Icon icon = Util.getIcon("/jatools/icons/function.gif");

        SimpleTreeNode rootNode = new SimpleTreeNode("����", icon);

        SimpleTreeNode parentNode = rootNode;

        for (int i = 0; i < FUNCTIONS.length; i++) {
            if (FUNCTIONS[i].indexOf("(") == -1) {
                SimpleTreeNode n = new SimpleTreeNode(FUNCTIONS[i], icon);
                rootNode.add(n);
                parentNode = n;
            } else {
                parentNode.add(new SimpleTreeNode(FUNCTIONS[i], icon, 1));
            }
        }

        IconTree tree = new IconTree();
        tree.setRoot(rootNode);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
                String selectedNode = null;
                String value = null;

                public void valueChanged(TreeSelectionEvent e) {
                    TreePath path = e.getNewLeadSelectionPath();
                    SimpleTreeNode simpleNode = (SimpleTreeNode) path.getLastPathComponent();

                    if (simpleNode != null) {
                        selectedNode = simpleNode.getUserObject().toString();

                        if (tips.containsKey(selectedNode)) {
                            value = (String) tips.get(selectedNode);
                            tipLabel.setText("<html><p>" + value + "</p></html>");
                        } else {
                            tipLabel.setText(selectedNode);
                        }
                    }
                }
            });
        tree.setDoubleClickAction(this);

        return tree;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        String text = null;

        if ((e.getSource() == functionTree) && (functionTree.getSelectedType() == 1)) {
            text = (String) functionTree.getSelectedObject();

            if (text != null) {
                int index = text.indexOf(" ");
                int left = text.indexOf("(");

                int count = 0;
                int start = 0;

                for (int i = 0; i < text.length(); i++) {
                    int comma = text.indexOf(",", start);

                    if (comma >= 0) {
                        start = comma + 1;
                        count++;
                    }
                }

                String textShort;
                String str = "";

                if (index >= 0) {
                    textShort = text.substring(index + 1, left);

                    if (count > 0) {
                        for (int i = 0; i < count; i++) {
                            str = str + ",  ";
                        }

                        text = textShort + "(" + str + ")";
                    } else {
                        text = textShort + "()";
                    }
                } else {
                    textArea.requestFocus();
                }

                textArea.replaceSelection(text + " ");
                textArea.requestFocus();

                return;
            }
        } else if ((e.getSource() == varTree) && varTree.isSettable()) {
            text = (String) varTree.getVariable();
        } else if (e.getSource() instanceof JToggleButton) {
            text = ((JToggleButton) e.getSource()).getText() + " ";
        }

        if (text != null) {
            textArea.replaceSelection(text);
            textArea.requestFocus();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Formula start() {
        return start((String) null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param formula DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Formula start(Formula formula) {
        if (formula != null) {
            textArea.setText(formula.getText());
        }

        this.formula = null;
        show();

        return this.formula;
    }

    /**
     * DOCUMENT ME!
     *
     * @param text DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Formula start(String text) {
        textArea.setText(text);

        this.formula = null;
        show();

        return this.formula;
    }

    /**
     * DOCUMENT ME!
     *
     * @param value DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean showChooser(Object value) {
        textArea.setText((String) value);
        this.formula = null;
        show();

        return this.formula != null;
    }

    private void done() {
        String txt = textArea.getText();

        boolean isNull = (txt == null) || txt.trim().equals("");

        if (isNull && !nullPermitted) {
            MessageBox.error(CustomFormulaDialog.this, "��ʽ���ʽ����Ϊ��.");
            textArea.requestFocus();

            return;
        } else if ((txt.indexOf(PrintConstants.TOTAL_PAGE_NUMBER) != -1) &&
                (!txt.trim().equals(PrintConstants.TOTAL_PAGE_NUMBER))) {
            if (AmbiguousNameNodePattern.matches(txt, PrintConstants.TOTAL_PAGE_NUMBER)) {
                MessageBox.error(CustomFormulaDialog.this,
                    "��ҳ������:" + PrintConstants.TOTAL_PAGE_NUMBER + ",���ܲ�������.");

                textArea.requestFocus();

                return;
            }
        }

        formula = new Formula(isNull ? null : txt);
        hide();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getValue() {
        return this.formula.getText();
    }

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
    }
}
