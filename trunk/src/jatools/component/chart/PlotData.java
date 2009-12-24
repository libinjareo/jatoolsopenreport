package jatools.component.chart;

import jatools.accessor.AutoAccessor;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class PlotData extends AutoAccessor {
    String field;
    Tip tooltip;

    /**
     * 
     *
     * @param field ��ʾ��ǩ�ֶ�,һ����"�˿�","���"��ֵ���ֶ�
     * @param tooltip ��ʾ��tooltip,
     */
    public PlotData(String field, Tip tooltip) {
        this.field = field;
        this.tooltip = tooltip;
    }

    /**
     * Creates a new ShowData object.
     */
    public PlotData() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getField() {
        return field;
    }

    /**
     * DOCUMENT ME!
     *
     * @param field DOCUMENT ME!
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Tip getTooltip() {
        return tooltip;
    }

    /**
     * DOCUMENT ME!
     *
     * @param tooltip DOCUMENT ME!
     */
    public void setTooltip(Tip tooltip) {
        this.tooltip = tooltip;
    }
}
