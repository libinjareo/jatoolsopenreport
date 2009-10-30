/*
 *   Author: John.
 *
 *   ���ݽܴ����   All Copyrights Reserved.
 */

/*
 * Created on 2003-12-28
 *
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
package jatools.designer.wizard.table;

import jatools.ReportDocument;
import jatools.component.Component;
import jatools.component.Label;
import jatools.component.Page;
import jatools.component.PagePanel;
import jatools.component.Panel;
import jatools.component.Text;
import jatools.component.table.Cell;
import jatools.component.table.CellStore;
import jatools.component.table.RowPanel;
import jatools.component.table.Table;
import jatools.core.view.Border;
import jatools.data.reader.DatasetReader;
import jatools.designer.App;
import jatools.designer.wizard.BuilderContext;
import jatools.designer.wizard.CustomSummary;
import jatools.designer.wizard.ReportBuilder;
import jatools.designer.wizard.ReportStyler;
import jatools.designer.wizard.util.CustomGroup;
import jatools.dom.Group;
import jatools.dom.src.DatasetNodeSource;
import jatools.dom.src.GroupNodeSource;
import jatools.dom.src.RootNodeSource;
import jatools.dom.src.RowNodeSource;
import jatools.util.Util;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Icon;



/**
 * @author   java9
 */
public class TableStyler implements ReportStyler {
    static final String STYLER_NAME = App.messages.getString("res.205");
    static final String ICON_URL = "/jatools/icons/styletable.gif";

    /*
     * (non-Javadoc)
     *
     * @see com.jatools.designer.builder2.ZReportStyler#getBuilder()
     */

    //    ReportBuilder builder;

    //2007-12-28
    TableReportBuilder builder;

    /**
     * ȡ�÷�����ۼ����е�����
     * @param old int
     * @param groupFields CustomGroup[]
     * @param summaries CustomSummary[]
     * @return int
     */
    HashMap rowNumberMap = new HashMap();

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return getName();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return STYLER_NAME;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Icon getIcon() {
        return Util.getIcon(ICON_URL);
    }

    /**
     *
     *
     *  * ����ָ���Ľ���������������һ������
     * @param report
     *            ����
     * @param context
     *            ����������
     *
     * @return ���ɵı���
     */
    public void format(ReportDocument doc, BuilderContext context) {
        // ����ҳ��
        Page page = new Page();
        page.setName("panel");

        // ����ҳü
        PagePanel header = new PagePanel();
        header.setHeight(80);
        header.setName("header");
        page.setHeader(header);

        // ����ҳ����
        PagePanel body = new PagePanel();
        body.setName("body");

        // ����һ�����,��ҳ������
        //    Table t = getTable();
        Table t = getTable2(context);
        t.setX(20);
        t.setY(20);

        body.add(t);
        page.setBody(body);

        // ����ҳ��
        PagePanel footer = new PagePanel();
        footer.setHeight(80);
        footer.setName("footer");

        page.setFooter(footer);

        // ����ҳ����,��reportdocument������
        doc.setPage(page);

        // ������ؽڵ�Դ
        //doc.setNodeSource(getRootNodeSource());
        doc.setNodeSource(generateRootNodeSource(context));
    }

    /**
     * ȡ����ʾ��,���������ʾ��+ͳ���������У���಻������
     * @param disPlayItems String[]
     * @param summaries CustomSummary[]
     * @return ArrayList
     */
    ArrayList getDisPlayFields(String[] disPlayItems, HashMap summariesMap) {
        ArrayList list = new ArrayList();

        for (int i = 0; i < disPlayItems.length; i++) {
            list.add(disPlayItems[i]);
        }

        Collection set = summariesMap.values();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            Object o = it.next();
            ArrayList summariesVector = (ArrayList) o;

            if (summariesVector != null) {
                for (int i = 0; i < summariesVector.size(); i++) {
                    //calcField������ص���
                    CustomSummary cs = (CustomSummary) summariesVector.get(i);
                    String calcField = cs.getCalcField();

                    if (!list.contains(calcField)) {
                        list.add(calcField);
                    }
                }
            }
        }

        return list;
    }

    int getColumnNumber(CustomGroup[] groupFields, String[] disPlayItems, HashMap summariesMap) {
        int columnNumber = groupFields.length;
        columnNumber = columnNumber + getDisPlayFields(disPlayItems, summariesMap).size();

        return columnNumber;
    }

    /**
     * ArrayList<CustomSummary>
     * @param v ArrayList
     * @return int
     */
    int getSameCalcFieldLargestNumber(ArrayList v) {
        Map temp = new HashMap();

        if (v.size() > 0) {
            for (int q = 0; q < v.size(); q++) {
                CustomSummary cs = (CustomSummary) v.get(q);
                String calc = cs.getCalcField();

                if (!temp.containsKey(calc)) {
                    temp.put(calc, new Integer(1));
                } else {
                    temp.put(calc, new Integer(((Integer) temp.get(calc)).intValue() + 1));
                }
            }
        }

        Collection c = temp.values();
        Iterator it = c.iterator();
        int number = 1; //����һ��

        while (it.hasNext()) {
            Integer in = (Integer) it.next();

            if (in.intValue() > number) {
                number = in.intValue();
            }
        }

        return number;
    }

    int getRowNumber(CustomGroup[] groupFields, HashMap summariesMap) {
        //ͷ��
        int rowNumber = 1;
        //row��
        rowNumber = rowNumber + 1;

        //group����
        for (int i = groupFields.length - 1; i >= 0; i--) {
            CustomGroup cg = (CustomGroup) groupFields[i];
            String key = cg.getGroupBy();
            boolean b = summariesMap.containsKey(key);

            if (b) {
                ArrayList v = (ArrayList) summariesMap.get(key);
                int num = getSameCalcFieldLargestNumber(v);
                rowNumber = rowNumber + num; //������
            } else {
                rowNumber = rowNumber + 1;
            }

            //���ǰ�������ͷ,group��ײ�,rowPanel����������Խ��
            rowNumberMap.put(key, new Integer(rowNumber));
        }

        //�ϼ�����
        boolean bb = summariesMap.containsKey(null);

        if (bb) {
            ArrayList vv = (ArrayList) summariesMap.get(null);
            int num = getSameCalcFieldLargestNumber(vv);
            rowNumber = rowNumber + num;
        } else {
            rowNumber = rowNumber + 1;
        }

        rowNumberMap.put(null, new Integer(rowNumber));

        return rowNumber;
    }

    /**
     * 2007-12-28
     * @return Table
     */
    Table getTable2(BuilderContext context) {
        DatasetReader reader = (DatasetReader) context.getValue(BuilderContext.READER);
        String[] disPlayItems = (String[]) context.getValue(BuilderContext.DISPLAY_ITEMS);
        Map aliasLooker = (Map) context.getValue(BuilderContext.ALIAS_LOOKER);
        CustomGroup[] groupFields = (CustomGroup[]) context.getValue(BuilderContext.GROUP_ITEMS);
        HashMap summariesMap = (HashMap) context.getValue(BuilderContext.SUMMARY_ITEMS);

        int[] rows = new int[getRowNumber(groupFields, summariesMap)];
        int[] columns = new int[getColumnNumber(groupFields, disPlayItems, summariesMap)];
        Arrays.fill(rows, 20);
        Arrays.fill(columns, 80);

        Table table = new Table(rows, columns);
        table.setNodePath(reader.getName());

        //��һ�б�ͷ
        Label label = null;

        for (int i = 0; i < groupFields.length; i++) {
            //      label=new Label(groupFields[i].getGroupBy());
            //      table.add(label,0,i);

            //��ʾ�ɱ�ǩ
            String name = groupFields[i].getGroupBy();

            if (aliasLooker.containsKey(name)) {
                String temp = aliasLooker.get(groupFields[i].getGroupBy()).toString().trim();
                name = temp.equals("") ? name : temp;
            }

            label = new Label(name);
            table.add(label, 0, i);
        }

        ArrayList disPlayField = getDisPlayFields(disPlayItems, summariesMap);

        for (int i = 0; i < disPlayField.size(); i++) {
            //      label=new Label(disPlayField.get(i).toString());
            //      table.add(label,0,groupFields.length+i);

            //��ʾ�ɱ�ǩ
            String name = disPlayField.get(i).toString();

            if (aliasLooker.containsKey(name)) {
                String temp = aliasLooker.get(name).toString().trim();
                name = temp.equals("") ? name : temp;
            }

            label = new Label(name);
            table.add(label, 0, groupFields.length + i);
        }

        //group����row��
        ArrayList rowsList = new ArrayList();

        for (int i = 0; i < groupFields.length; i++) {
            RowPanel rowPanel = new RowPanel();
            String groupby = groupFields[i].getGroupBy();

            if (i == 0) {
                rowPanel.setNodePath(groupby);
                table.add(rowPanel);
            } else {
                rowPanel.setNodePath(groupby);
            }

            int rowSpan = ((Integer) rowNumberMap.get(groupby)).intValue() - 1;
            rowPanel.setCell(new Cell(1, 0, disPlayField.size() + groupFields.length, rowSpan));

            //rowPanel ����text��ͳ�ƺ�����
            int groupNumber = groupFields.length;
            int disPlayNumber = disPlayField.size();
            boolean b = summariesMap.containsKey(groupby);
            ArrayList v = new ArrayList();

            if (b) {
                v = (ArrayList) summariesMap.get(groupby);
            }

            if (!b || (v.size() < 1)) {
                //С��
                if (i < (groupFields.length - 1)) {
                    Label xiaoji = new Label(App.messages.getString("res.206"));
                    rowPanel.add(xiaoji, rowSpan, i + 1, groupFields.length - i - 1, 1);
                }

                //text
                for (int j = 0; j < disPlayNumber; j++) {
                    Text text = new Text();
                    rowPanel.add(text, rowSpan, groupNumber + j);
                }
            } else {
                //��þ�����ͬcalcfield������
                //С��
                int largest = getSameCalcFieldLargestNumber(v);

                if (i < (groupFields.length - 1)) {
                    Label xiaoji = new Label(App.messages.getString("res.206"));
                    rowPanel.add(xiaoji, rowSpan - (largest - 1), i + 1,
                        groupFields.length - i - 1, largest);
                }

                ArrayList copyVector = new ArrayList();

                for (int vv = 0; vv < v.size(); vv++) {
                    copyVector.add(v.get(vv));
                }

                for (int j = 0; j < largest; j++) {
                    for (int k = 0; k < disPlayNumber; k++) {
                        Text text = new Text();
                        String field = disPlayField.get(k).toString();

                        for (int w = 0; w < copyVector.size(); w++) {
                            CustomSummary cs = (CustomSummary) copyVector.get(w);

                            if (cs.getCalcField().equals(field)) {
                                //cs.getCalcType()ע��
                                String clac = cs.getCalcType()
                                                .substring(cs.getCalcType().indexOf("(") + 1,
                                        cs.getCalcType().indexOf(")"));
                                text.setVariable("=$." + field + "." + clac + "()");
                                //�����ظ�
                                copyVector.remove(cs);

                                break;
                            }
                        }

                        rowPanel.add(text, rowSpan - (largest - 1) + j, groupNumber + k);
                    }
                }
            }

            rowsList.add(rowPanel);
        }

        //��ϸ��
        RowPanel lastRow = new RowPanel();
        lastRow.setNodePath("Row");

        Text text = null;
        lastRow.setCell(new Cell(1, 0, groupFields.length + disPlayField.size(), 1));

        for (int i = 0; i < groupFields.length; i++) {
            String groupby = groupFields[i].getGroupBy();
            int rowSpan = ((Integer) rowNumberMap.get(groupby)).intValue() - 1;
            text = new Text();
            text.setVariable("=$" + groupby);
            text.setPrintStyle("united-level:" + (i + 1));
            lastRow.add(text, 1, i, 1, rowSpan);
        }

        for (int i = 0; i < disPlayField.size(); i++) {
            String field = disPlayField.get(i).toString();
            text = new Text();
            text.setVariable("=$." + field);
            lastRow.add(text, 1, groupFields.length + i, 1, 1);
        }

        //���û�з���
        if (groupFields.length == 0) {
            table.add(lastRow);
        }

        rowsList.add(lastRow);

        RowPanel firstPanel = (RowPanel) rowsList.get(0);
        rowsList.remove(firstPanel);
        configPanel(rowsList, firstPanel);

        //�ϼ�
        boolean bb = summariesMap.containsKey(null);

        if (bb) {
            ArrayList vv = (ArrayList) summariesMap.get(null);
            int largest = getSameCalcFieldLargestNumber(vv);
            int rowSpan = ((Integer) rowNumberMap.get(null)).intValue() - 1;

            table.add(label = new Label(App.messages.getString("res.207")), rowSpan - (largest - 1), 0, groupFields.length,
                largest);

            int disPlayNumber = disPlayField.size();

            ArrayList copyVector = new ArrayList();

            for (int cc = 0; cc < vv.size(); cc++) {
                copyVector.add(vv.get(cc));
            }

            for (int i = 0; i < largest; i++) {
                for (int k = 0; k < disPlayNumber; k++) {
                    text = new Text();

                    String field = disPlayField.get(k).toString();

                    for (int w = 0; w < copyVector.size(); w++) {
                        CustomSummary cs = (CustomSummary) copyVector.get(w);

                        if (cs.getCalcField().equals(field)) {
                            String clac = cs.getCalcType()
                                            .substring(cs.getCalcType().indexOf("(") + 1,
                                    cs.getCalcType().indexOf(")"));
                            text.setVariable("=$." + field + "." + clac + "()");

                            //�����ظ�
                            copyVector.remove(cs);

                            break;
                        }
                    }

                    table.add(text, rowSpan - (largest - 1) + i, groupFields.length + k);
                }
            }
        } else {
        }

        // table���������,���ϱ߿�
        setBorder(table);

        return table;
    }

    /**
     * Ӧ�õ����������
     * @param fieldName String
     * @param CustomSummarys ArrayList
     * @return ArrayList
     */
    ArrayList getCalcAsSameFieldName(String fieldName, ArrayList CustomSummarys) {
        ArrayList v = new ArrayList();

        for (int i = 0; i < CustomSummarys.size(); i++) {
            CustomSummary cs = (CustomSummary) CustomSummarys.get(i);

            if (cs.getCalcField().equals(fieldName)) {
                v.add(cs);
            }
        }

        return v;
    }

    /**
      * panel ֮�����
      * @param panels ArrayList
      * @param first Panel
      */
    void configPanel(ArrayList panels, Panel first) {
        Panel panel = null;

        for (int i = 0; i < panels.size(); i++) {
            panel = (Panel) panels.get(i);
            first.add(panel);
            panels.remove(panel);

            break;
        }

        if (panel != null) {
            configPanel(panels, panel);
        }
    }

    /**
     * groupNodeSource֮��Ĳ�ι�ϵ
     * @param list ArrayList
     * @param root GroupNodeSource
     */
    void configGroupSource(ArrayList list, GroupNodeSource root) {
        GroupNodeSource groupSource = null;

        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            groupSource = (GroupNodeSource) o;
            root.add(groupSource);
            list.remove(o);

            break;
        }

        if (groupSource != null) {
            configGroupSource(list, groupSource);
        }
    }

    /**
     * �������ݼ�
     * @param context BuilderContext
     * @return RootNodeSource
     */
    RootNodeSource generateRootNodeSource(BuilderContext context) {
        DatasetReader reader = (DatasetReader) context.getValue(BuilderContext.READER);
        RootNodeSource root = new RootNodeSource();

        DatasetNodeSource dns = new DatasetNodeSource(reader.getName(), reader);
        root.add(dns);

        CustomGroup[] groupFields = (CustomGroup[]) context.getValue(BuilderContext.GROUP_ITEMS);
        ArrayList groupNodeSourceList = new ArrayList();
        ArrayList groupNodeSourceListCopy = new ArrayList();

        for (int i = 0; i < groupFields.length; i++) {
            Group group = new Group(groupFields[i].getGroupBy(),
                    groupFields[i].getOrder() );
            GroupNodeSource groupSource = new GroupNodeSource(group);
            groupNodeSourceList.add(groupSource);
            groupNodeSourceListCopy.add(groupSource);
        }

        if (groupNodeSourceListCopy.size() > 0) {
            GroupNodeSource rootGroup = (GroupNodeSource) groupNodeSourceListCopy.get(0);
            dns.add(rootGroup);
            groupNodeSourceListCopy.remove(rootGroup);
            configGroupSource(groupNodeSourceListCopy, rootGroup);

            RowNodeSource closed = new RowNodeSource();
            ((GroupNodeSource) groupNodeSourceList.get(groupNodeSourceList.size() - 1)).add(closed);
        }

        //��û�з���ʱ��Ҳ��Ҫһ��RowNodeSource
        if (groupNodeSourceList.size() == 0) {
            RowNodeSource closed = new RowNodeSource();
            dns.add(closed);
        }

        return root;
    }



    private void setBorder(Table table) {
        CellStore cellstore = table.getCellstore();

        for (int r = 0; r < table.getRowCount(); r++) {
            for (int c = 0; c < table.getColumnCount(); c++) {
                Component child = cellstore.getComponent(r, c);

                if (child != null) {
                    child.setBorder(new Border());
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     * @return   DOCUMENT ME!
     * @uml.property   name="builder"
     */
    public ReportBuilder getBuilder() {
        if (builder == null) {
            //            builder = new _TableBuilder();
            builder = new TableReportBuilder();
            ((TableReportBuilder) builder).setStyler(this);
        }

        return builder;
    }



    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getDescription() {
        return "����һ���򵥱��,����Է�����ܵı��."; // //
    }

    class _TableBuilder implements ReportBuilder {
        /*
         * (non-Javadoc)
         *
         * @see com.jatools.designer.builder2.ZReportBuilder#build(com.jatools.designer.builder2.ZBuilderContext)
         */
        public ReportDocument build(Frame owner, BuilderContext context) {
            ReportDocument doc = new ReportDocument();

            format(doc, context);

            return doc;
        }
    }
}
