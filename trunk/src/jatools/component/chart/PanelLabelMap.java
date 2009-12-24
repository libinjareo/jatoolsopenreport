package jatools.component.chart;

import jatools.component.chart.chart.Dataset;

import java.util.HashMap;



public class PanelLabelMap {
	private static HashMap labelMap = new HashMap();
	static{
		labelMap.put("0", "����ͼ");
		labelMap.put("1", "����ͼ");
		labelMap.put("2", "��ͼ");
	}
	
	public static String getLabel(String type){
		return (String) labelMap.get(type);
	}
	
	
}
