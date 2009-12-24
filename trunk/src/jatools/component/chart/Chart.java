package jatools.component.chart;

import jatools.accessor.PropertyDescriptor;
import jatools.component.Component;
import jatools.component.ComponentConstants;
import jatools.component.ImageStyle;
import jatools.data.reader.AbstractDatasetReader;
import jatools.data.reader.DatasetReader;
import jatools.imageio.ImageWriter;
import jatools.util.Map;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.jfree.chart.JFreeChart;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Chart extends Component {
	private Properties properties = new Properties();
    private DatasetReader reader;
    private String labelField;
    private ArrayList plotData = new ArrayList();
    public JFreeChart chart;
    
    int exportImageFormat = ImageWriter.FLASH;
	
	/**
     * Creates a new Image object.
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     * @param width DOCUMENT ME!
     * @param height DOCUMENT ME!
     */
    public Chart(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * Creates a new Image object.
     */
    public Chart() {
    }
    
    /**
     * ȡ��ͳ��ͼ�������Լ�
     * @return ���� properties��
     * @uml.property name="graphProperties"
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * ����ͳ��ͼ���Լ�
     * @param properties
     *            Ҫ���õ� properties��
     * @uml.property name="graphProperties"
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * ����ͳ��ͼĳһ����
     *
     * @param prop DOCUMENT ME!
     * @param val DOCUMENT ME!
     */
    public void setProperty(String prop, String val) {
        if (properties != null) {
            properties.setProperty(prop, val);
        }
    }

    /**
     * ����ͳ��ͼ����,�б��ʽ
     *
     * @param prop DOCUMENT ME!
     * @param vals DOCUMENT ME!
     */
    public void setProperty(String prop, Object[] vals) {
        if ((properties != null) && (vals != null)) {
            String str = vals[0] + "";

            for (int i = 1; i < vals.length; i++) {
                str += ("," + vals[1]);
            }

            properties.setProperty(prop, str);
        }
    }

    /**
     * ����ͳ��ͼ���ݼ�
     * @return ���� graphReader��
     * @uml.property name="graphReader"
     */
    public DatasetReader getReader() {
        return reader;
    }

    /**
     * �õ�ͳ��ͼ���ݼ�����
     * @param graphReader
     *            Ҫ���õ� graphReader��
     * @uml.property name="graphReader"
     */
    public void setReader(DatasetReader graphReader) {
        this.reader = graphReader;
        
    }

    /**
     * ȡ��x������ʾ�ı�ǩ��,��,����,id,��
     * @return ���� labelData��
     * @uml.property name="graphLabelField"
     */
    public String getLabelField() {
        return labelField;
    }

    /**
     * ����x���ϱ�ǩ��
     * @param labelData
     *            Ҫ���õ� labelData��
     * @uml.property name="graphLabelField"
     */
    public void setLabelField(String labelData) {
        this.labelField = labelData;
    }

    /**
     * ȡ��y������ʾ��������,��,�˿�,�����,�����Ƕ��,�����Ǽ���,����Ԫ����һ��ShowData����
     * @return ���� graphShowData��
     * @uml.property name="graphShowData"
     */
    public ArrayList getPlotData() {
        return plotData;
    }

    /**
     * ����y������ʾ�ֶ�
     * @param plotData
     *            Ҫ���õ� graphShowData��
     * @uml.property name="graphShowData"
     */
    public void setPlotData(ArrayList showData) {
        this.plotData = showData;
    }
    
    /**
     * ���ͳ��ͼ����
     */
    public void clearProperties() {
        String[] reserved = new String[] {
                "background", "backgroundOutline", "titleString", "titleFont", "titleColor","chartType"
            };
        Enumeration keys = properties.keys();

        while (keys.hasMoreElements()) {
            boolean isDelete = true;
            String key = (String) keys.nextElement();

            for (int i = 0; i < reserved.length; i++) {
                if (key.indexOf(reserved[i]) != -1) {
                    isDelete = false;

                    break;
                }
            }

            if (isDelete) {
                properties.remove(key);
            }
        }
    }
    
    /**
     * ���ͳ��ͼ����
     */
    public void clearProperties2() {
        String[] reserved = new String[] {
                "background", "backgroundOutline","plotarea","plotareaOutline","titleString","titleFont", "titleColor","chartType"
            };
        Enumeration keys = properties.keys();

        while (keys.hasMoreElements()) {
            boolean isDelete = true;
            String key = (String) keys.nextElement();

            for (int i = 0; i < reserved.length; i++) {
                if (key.indexOf(reserved[i]) != -1) {
                    isDelete = false;

                    break;
                }
            }

            if (isDelete) {
                properties.remove(key);
            }
        }
    }
    
    
    public JFreeChart getChart() {
    	if(chart == null){
	    	chart = ChartFactory.createInstance(this,null);
    	}
        return chart;
    }
    
    public void setChart(JFreeChart chart){
    	this.chart = chart;
    }

    /**
     * Ϊxml����ͳ��ͼ���Զ���
     *
     * @return DOCUMENT ME!
     */
    public PropertyDescriptor[] getRegistrableProperties() {
        return new PropertyDescriptor[] {
            ComponentConstants._NAME, ComponentConstants._BORDER,
            //ComponentConstants._REQUIRED_HTML_IMAGE_FORMAT2,
            ComponentConstants._HYPERLINK,
            ComponentConstants._TOOLTIP_TEXT, ComponentConstants._X, ComponentConstants._Y,
            ComponentConstants._WIDTH, ComponentConstants._HEIGHT,
            ComponentConstants._PRINT_STYLE, ComponentConstants._PROPERTIES,
            ComponentConstants._READER, ComponentConstants._LABEL_FIELD,
            ComponentConstants._PLOT_DATA, ComponentConstants._CELL, ComponentConstants._INIT_PRINT,
            ComponentConstants._AFTERPRINT, ComponentConstants._BEFOREPRINT2
        };
    }


	/**
     * ���»���
     */
    public void refersh() {
        chart = null;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @param map DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Properties asProperties(Map map) {
        Properties tmp = new Properties();
        Iterator it = map.names();

        while (it.hasNext()) {
            String key = (String) it.next();

            if (map.get(key) != null) {
                String value = (String) map.get(key);
                tmp.setProperty(key, value);
            }
        }

        return tmp;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ImageStyle getImageCSS() {
        return new ImageStyle(null, 0, 0, false, this.exportImageFormat);
    }
    
}
