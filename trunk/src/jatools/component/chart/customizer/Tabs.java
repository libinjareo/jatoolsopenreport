/*
 * Copyright 1997-2003 Visual Engineering, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Visual Engineering, Inc.
 * Use is subject to license terms.
 */
package jatools.component.chart.customizer;

import jatools.component.chart.Chart;
import jatools.component.chart.CommonFinal;
import jatools.component.chart.PlotData;
import jatools.engine.ProtectClass;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

import org.jfree.chart.JFreeChart;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public abstract class Tabs extends JPanel implements ChangeListener, ProtectClass,
    javax.swing.event.ChangeListener {
    static String[] dataPrompts = {
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.ֻ��һ������Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ����������������.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ����������������.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ����������������.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ����������������.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ����������������.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ����������������.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ����������������.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ����ֻ��һ��,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ��������,����Ϊ��ֵ��.",
            "X���ǩ�������Ͳ���.��ʾ���������,����Ϊ��ֵ��,��һ�б�ʾ��Сֵ���ڶ��б�ʾ���ֵ.",
            "X���ǩ�������Ͳ���.��ʾ���������,����Ϊ��ֵ��.��һ�б�ʾ��Сֵ���ڶ��б�ʾ���ֵ",
            "X���ǩ�������Ͳ���.��ʾ����������,����Ϊ��ֵ��,���ηֱ������,���,����,���̼�.",
            "X���ǩ�������Ͳ���.��ʾ����������,����Ϊ��ֵ��,���ηֱ������,���,����,���̼�.",
            "X�����Ͳ���.��ʾ��������������,�ֱ��ʾ��ʼʱ��ͽ���ʱ��,��ʾ�����ͱ���Ϊ������."
        };
    protected Chart javachart;
    protected JFreeChart chart;
//    protected ChartBean chartBean;
    private ChangeListener parent;
    protected DataSelector dataPanel;
    protected JTabbedPane tp;
    
    public Tabs(){
    	
    }

    /**
     * DOCUMENT ME!
     *
     * @param l
     *            DOCUMENT ME!
     */
    public void addChangeListener(ChangeListener l) {
        parent = l;
    }

    protected abstract void initTabbed();

    protected abstract void refershTabContent();

    /**
     * DOCUMENT ME!
     *
     * @param e
     *            DOCUMENT ME!
     */
    public void fireChange(Object object) {
        if (parent != null) {
            parent.fireChange(object);
        }
    }

    /*    */
    /**
             * DOCUMENT ME!
             *
             * @param l
             *            DOCUMENT ME!
             */

    /*
     * public void removePropertyChangeListener(PropertyChangeListener l) {
     * support.removeElement(l); }
     */

    /**
     * DOCUMENT ME!
     *
     * @param cb
     *            DOCUMENT ME!
     */
    public final void setChart(JFreeChart chart, int type) {
        try {
            /*
             * chartBean = (ChartBean) cb; chart = chartBean.getChart();
             */
            this.chart = chart;
        } catch (java.lang.ClassCastException e) {
            System.out.println("oops!");
            System.out.println("this is not a javachart bean!");
            System.out.println("it's " + e.getMessage());

            return;
        }

        initializeCustomizer(type);
    }

    /**
     * DOCUMENT ME!
     *
     * @param object DOCUMENT ME!
     */
    public void setObject(Object object) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void stateChanged(ChangeEvent e) {
    	
    	Properties props = new Properties();
    	
    	props = this.javachart.getProperties();
    	
        if (e.getSource() instanceof SelectCommand) {
        	
        	
            SelectCommand cmd = (SelectCommand) e.getSource();

            Class cls = cmd.field.getColumnClass();
            
            if (cls != null) {
                if (cmd.type == SelectCommand.PLOTDATA) {
                    if ((!(Number.class.isAssignableFrom(cls)))) {
                        cmd.error = "��ʾ�������������ֵ��!";
                    }
                } 
                }
        } else {
            boolean update = false;

            if ((dataPanel.getReader() != null) && (dataPanel.getLabelField() != null) &&
                    (dataPanel.getShowData().length > 0)) {
                update = true;

//                if ((chart instanceof HiLoBarChart || chart instanceof HorizHiLoBarChart) &&
//                        (dataPanel.getShowData().length < 2)) {
//                    update = false;
//                } else if (chart instanceof HiLoCloseChart && (dataPanel.getShowData().length < 3)) {
//                    update = false;
//                } else if (chart instanceof CandlestickChart &&
//                        (dataPanel.getShowData().length < 4)) {
//                    update = false;
//                }
            }

            dataPanelToJavaChart();
            
            this.javachart.refersh();
            this.javachart.setProperties(props);

            if (update) {
                fireChange(null);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    public void dataPanelToJavaChart() {
        boolean update = false;
        //		if (dataPanel.getReader() != null && dataPanel.getLabelField() != null
        //				&& dataPanel.getShowData().length > 0) {
        //			update = true;
        //			if ((chart instanceof HiLoBarChart || chart instanceof HorizHiLoBarChart)
        //					&& dataPanel.getShowData().length < 2) {
        //				update = false;
        //			} else if (chart instanceof HiLoCloseChart
        //					&& dataPanel.getShowData().length < 3) {
        //				update = false;
        //			} else if (chart instanceof CandlestickChart
        //					&& dataPanel.getShowData().length < 4) {
        //				update = false;
        //			}
        //		}
        //
        //		if (update) {
        javachart.setReader(dataPanel.getReader());
        javachart.setLabelField(dataPanel.getLabelField());

        ArrayList show = new ArrayList();

        for (int i = 0; i < dataPanel.getShowData().length; i++) {
            show.add(dataPanel.getShowData()[i]);
        }

        javachart.setPlotData(show);

        //		}
    }

    protected final void initializeCustomizer(int type) {
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = CommonFinal.INSETS;
        gbc.fill = GridBagConstraints.BOTH;

        tp = new JTabbedPane();

        gbc.weightx = 1;
        gbc.weighty = 1;

        dataPanel = new DataSelector();
        initDataPanel();
        dataPanel.addChangeListener(this);

        dataPanel.setPrompt(dataPrompts[type]);
        dataPanel.setType(type);
        tp.add(dataPanel, "���ݼ�");

        initTabbed();

        add(tp, gbc);
    }

    /**
     * DOCUMENT ME!
     */
    public void initDataPanel() {
        //	if (javachart.getGraphReader() == null) {
        PlotData[] data = null;

        if (javachart.getPlotData() != null) {
            data = (PlotData[]) javachart.getPlotData().toArray(new PlotData[0]);
        }

        dataPanel.init2(javachart.getReader(), javachart.getLabelField(), data);

        //	}
    }

    /**
     * DOCUMENT ME!
     *
     * @param javachart DOCUMENT ME!
     */
    public void setJavaChart(Chart javachart, int type) {
        try {
            /*
             * chartBean = (ChartBean) cb; chart = chartBean.getChart();
             */
            this.javachart = javachart;
            this.chart = javachart.chart;
        } catch (java.lang.ClassCastException e) {
            System.out.println("oops!");
            System.out.println("this is not a javachart bean!");
            System.out.println("it's " + e.getMessage());

            return;
        }

        initializeCustomizer(type);
    }
}
