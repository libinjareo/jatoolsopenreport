package jatools.component.chart;

import jatools.component.chart.applet.ChartUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.Properties;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Rotation;

public class ChartStyle {
	public ChartStyle(){
		
	}
	public static void setChartStyle(JFreeChart chart,Properties props){
		//����
		String titleString = props.getProperty("titleString");
		String titleFont = props.getProperty("titleFont");
		String titleColor = props.getProperty("titleColor");
		
		String background = props.getProperty("background");//������ɫ
		String backgroundOutline = props.getProperty("backgroundOutline");//�߽���
		String plotarea = props.getProperty("plotarea");//��ͼ����ɫ
		String plotareaOutline = props.getProperty("plotareaOutline");//��ͼ��������ɫ
		
		//ͼ��
		String legendbackground = props.getProperty("legendbackground");
		String legendVisible = props.getProperty("legendVisible");
		String legendItemFont = props.getProperty("legendItemFont");
		String legendItemColor = props.getProperty("legendItemColor");
		String legendPosition = props.getProperty("legendPosition");
		
		String axisLabel = props.getProperty("axisLabel");
		String axisLabelFont = props.getProperty("axisLabelFont");
		String axisLabelColor = props.getProperty("axisLabelColor");
		
		String rangeAxisLabel = props.getProperty("rangeAxisLabel");
		String rangeAxisLabelFont = props.getProperty("rangeAxisLabelFont");
		String rangeAxisLabelColor = props.getProperty("rangeAxisLabelColor");
		
		String axisTickLabelsVisible = props.getProperty("axisTickLabelsVisible");
		String axisTickLabelsFont = props.getProperty("axisTickLabelsFont");
		String axisTickLabelsColor = props.getProperty("axisTickLabelsColor");
		String axisLineColor = props.getProperty("axisLineColor");
		String axisTickMarkColor = props.getProperty("axisTickMarkColor");
		
		String rangeAxisTickLabelsVisible = props.getProperty("rangeAxisTickLabelsVisible");
		String rangeAxisTickLabelsFont = props.getProperty("rangeAxisTickLabelsFont");
		String rangeAxisTickLabelsColor = props.getProperty("rangeAxisTickLabelsColor");
		String rangeAxisLineColor = props.getProperty("rangeAxisLineColor");
		String rangeAxisTickMarkColor = props.getProperty("rangeAxisTickMarkColor");
		
		String axisLocation = props.getProperty("axisLocation");
		String rangeAxisLocation = props.getProperty("rangeAxisLocation");
		
		String domainGridlinesVisible = props.getProperty("domainGridlinesVisible");
		String domainGridlineColor = props.getProperty("domainGridlineColor");
		
		String rangeGridlinesVisible = props.getProperty("rangeGridlinesVisible");
		String rangeGridlineColor = props.getProperty("rangeGridlineColor");
		
		String ItemLabelsVisible = props.getProperty("ItemLabelsVisible");
		
		
		if(background != null){
			chart.setBackgroundPaint(ChartUtil.getColor(background));
		}
		else{
			chart.setBackgroundPaint(Color.WHITE);
		}
		
		if(backgroundOutline != null){
			chart.setBorderPaint(ChartUtil.getColor(backgroundOutline));//�߽�������ɫ
//			chart.setBorderStroke(new BasicStroke(10));//�߽������ʴ�
			chart.setBorderVisible(true);// �߽������Ƿ�ɼ�
		}

		//ͼƬ����
		if(titleString !=null){
			chart.setTitle(titleString);
			chart.getTitle().setFont(ChartUtil.getFont(titleFont));
			chart.getTitle().setPaint(ChartUtil.getColor(titleColor));
		}
		
		//ͼ��
		LegendTitle legend = (LegendTitle) chart.getLegend();
		if(legendbackground != null)
			legend.setBackgroundPaint(ChartUtil.getColor(legendbackground));
		if(legendVisible !=null){
			legend.setVisible(Boolean.parseBoolean(legendVisible));
			legend.setItemFont(ChartUtil.getFont(legendItemFont));
			legend.setItemPaint(ChartUtil.getColor(legendItemColor));
		}
		if(legendPosition != null){
			if(legendPosition.equals("RectangleEdge.BOTTOM"))
				legend.setPosition(RectangleEdge.BOTTOM);
			else if(legendPosition.equals("RectangleEdge.TOP"))
				legend.setPosition(RectangleEdge.TOP);
			else if(legendPosition.equals("RectangleEdge.LEFT"))
				legend.setPosition(RectangleEdge.LEFT);
			else if(legendPosition.equals("RectangleEdge.RIGHT"))
				legend.setPosition(RectangleEdge.RIGHT);
		}
		
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		Plot plot = chart.getPlot();
		plot.setNoDataMessage("��������ʾ");

		if (plot instanceof CategoryPlot) {
			CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();// ͼ���
			plot.setBackgroundAlpha(0.6f);
			plot.setForegroundAlpha(0.9F);// ����͸����
			
			if(plotarea != null){
				plot.setBackgroundPaint(ChartUtil.getColor(plotarea));//��ͼ����ɫ
			}
			
			if(domainGridlinesVisible != null)
				categoryPlot.setDomainGridlinesVisible(Boolean.parseBoolean(domainGridlinesVisible));//�����Ƿ���ʾ��ֱ������
			if(domainGridlineColor != null)
				categoryPlot.setDomainGridlinePaint(ChartUtil.getColor(domainGridlineColor));//���ô�ֱ��������ɫ
			
			if(rangeGridlinesVisible != null)
				categoryPlot.setRangeGridlinesVisible(Boolean.parseBoolean(rangeGridlinesVisible));//�����Ƿ���ʾˮƽ������
			else
				categoryPlot.setRangeGridlinesVisible(false);
			if(rangeGridlineColor != null)
				categoryPlot.setRangeGridlinePaint(ChartUtil.getColor(rangeGridlineColor));//����ˮƽ��������ɫ
			
			
			if(plotareaOutline != null){
				categoryPlot.setOutlinePaint(ChartUtil.getColor(plotareaOutline));//ͼʾ�߽�������ɫ
//			categryPlot.setOutlineStroke(new BasicStroke(10));//   ͼʾ�߽������ʴ�
				categoryPlot.setOutlineVisible(true);
			}
			//�����ݺ��������ʾλ��
			if(axisLocation != null){
				if(axisLocation.equals("bottom"))
					categoryPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);//�������ڵײ�
				else
					categoryPlot.setDomainAxisLocation(AxisLocation.TOP_OR_LEFT);//�������ڶ���
			}
			
			if(rangeAxisLocation != null){
				if(rangeAxisLocation.equals("left"))
					categoryPlot.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);//�����������
				else
					categoryPlot.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);//���������ұ�
			}
			
			// x������
			CategoryAxis domainAxis = categoryPlot.getDomainAxis();
			if(axisLabel != null){
				domainAxis.setLabel(axisLabel);
				domainAxis.setLabelFont(ChartUtil.getFont(axisLabelFont));//���������
				domainAxis.setLabelPaint(ChartUtil.getColor(axisLabelColor));//�������ɫ
			}
			
			if(axisTickLabelsVisible != null){
				domainAxis.setTickLabelsVisible(Boolean.parseBoolean(axisTickLabelsVisible));
			}
			
			if(axisTickLabelsFont != null){
				domainAxis.setTickLabelFont(ChartUtil.getFont(axisTickLabelsFont));//����ֵ����
			}
			if(axisTickLabelsColor != null){
				domainAxis.setTickLabelPaint(ChartUtil.getColor(axisTickLabelsColor));
			}
			
			if(axisLineColor != null){
				domainAxis.setAxisLinePaint(ChartUtil.getColor(axisLineColor));//������ɫ
			}
			
			if(axisTickMarkColor != null){
				domainAxis.setTickMarkPaint(ChartUtil.getColor(axisTickMarkColor));//������ɫ
			}
			
			String axisAngle = props.getProperty("axisAngle");
			
			if(axisAngle != null){
				double d = Double.parseDouble(axisAngle);
				if(d > 0.0){
					if(d == 1.5707963267948966)
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
					else
						domainAxis.setCategoryLabelPositions(ChartUtil
								.createRightRotationLabelPositions(-d));
				}
				if(d < -0.0){
					if(d == -1.5707963267948966)
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
					else
						domainAxis.setCategoryLabelPositions(CategoryLabelPositions
								.createUpRotationLabelPositions(-d));
				}
				if(d == 0.0){
					domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
				}
				
			}
			
			//y������
			NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
			
			if(rangeAxisLabel != null){
				rangeAxis.setLabel(rangeAxisLabel);
				rangeAxis.setLabelPaint(ChartUtil.getColor(rangeAxisLabelColor));
				rangeAxis.setLabelFont(ChartUtil.getFont(rangeAxisLabelFont));
			}
			
			if(rangeAxisTickLabelsVisible != null){
				rangeAxis.setTickLabelsVisible(Boolean.parseBoolean(rangeAxisTickLabelsVisible));
			}
			
			if(rangeAxisTickLabelsFont != null){
				rangeAxis.setTickLabelFont(ChartUtil.getFont(rangeAxisTickLabelsFont));//����ֵ����
			}
			if(rangeAxisTickLabelsColor != null){
				rangeAxis.setTickLabelPaint(ChartUtil.getColor(rangeAxisTickLabelsColor));
			}
			
			if(rangeAxisLineColor != null){
				rangeAxis.setAxisLinePaint(ChartUtil.getColor(rangeAxisLineColor));//������ɫ
			}
			
			if(rangeAxisTickMarkColor != null){
				rangeAxis.setTickMarkPaint(ChartUtil.getColor(rangeAxisTickMarkColor));//������ɫ
			}
			
			String numberFormat = props.getProperty("numberFormat");
			if(numberFormat != null)
				rangeAxis.setNumberFormatOverride(new DecimalFormat(numberFormat));
			
			// VALUE_TEXT_ANTIALIAS_OFF��ʾ�����ֵĿ���ݹر�
			chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			
            // ����item(bar)��ǩֵ��λ���������滹����bar��
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER)); 
			if(renderer instanceof LineAndShapeRenderer){
				if(ItemLabelsVisible != null)
					((LineAndShapeRenderer)renderer).setBaseShapesVisible(Boolean.parseBoolean(ItemLabelsVisible));//��ʾ����ͼ�ϵ����ݱ�־
			}
//			if(renderer instanceof BarRenderer3D){
//				((BarRenderer3D)renderer).setItemLabelAnchorOffset(10D);// ��������ͼ�ϵ�����ƫ��ֵ 
//			}
			if(renderer instanceof BarRenderer){
//				((BarRenderer) renderer).setMaximumBarWidth(0.02); 
//				((BarRenderer) renderer).setItemMargin(0.0);  
//				((BarRenderer) renderer).setMinimumBarLength(0.5);
				
			}
			
			if(ItemLabelsVisible != null)
				renderer.setBaseItemLabelsVisible(Boolean.parseBoolean(ItemLabelsVisible));//��ʾÿ�������ϵ�����
			
			renderer.setBaseItemLabelFont(new Font("����", Font.PLAIN, 12));
		}

		if (plot instanceof PiePlot) {
			// �õ�3D��ͼ��plot����
			PiePlot piePlot = (PiePlot) chart.getPlot();
			plot.setBackgroundAlpha(0.6f);
			piePlot.setStartAngle(290);// ������ת�Ƕ�
			piePlot.setDirection(Rotation.CLOCKWISE);// ������ת����:˳ʱ�뷽��
			piePlot.setForegroundAlpha(0.5f);// ����͸����
			piePlot.setLabelFont((new Font("����", Font.PLAIN, 12)));
			piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(   
            "{0}:{1}({2})"));//���ơ�ֵ���ٷֱ�

		}
		
	}
}
