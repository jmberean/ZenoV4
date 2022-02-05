package pkg;

import java.awt.BorderLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XYLineChartExample extends JFrame {

	double swingHigh = 0;
	double swingLow = 0;
	ArrayList<Double> prices = new ArrayList<>();
	ArrayList<Double> shares = new ArrayList<>();
	ArrayList<Double> avePrices = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	public XYLineChartExample(double swingHigh, double swingLow, ArrayList<Double> prices,ArrayList<Double> shares,
			ArrayList<Double> avePrices) {
		super("Zeno Chart");

		this.swingHigh = swingHigh;
		this.swingLow = swingLow;
		this.prices = prices;
		this.shares = shares;
		this.avePrices = avePrices;

		JPanel chartPanel = createChartPanel(swingHigh, swingLow, prices, shares, avePrices);
		add(chartPanel, BorderLayout.CENTER);

		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private JPanel createChartPanel(double swingHigh, double swingLow, ArrayList<Double> prices, ArrayList<Double> shares,
			ArrayList<Double> avePrices) {
		String chartTitle = "Zeno";
		String xAxisLabel = "Order #";
		String yAxisLabel = "Price";

		XYDataset dataset = createDataset(swingHigh, swingLow, prices, shares, avePrices);

		JFreeChart chart = ChartFactory.createScatterPlot(chartTitle, xAxisLabel, yAxisLabel, dataset);

		XYPlot plot = chart.getXYPlot();
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseLinesVisible(true);
		renderer.setBaseItemLabelsVisible(Boolean.TRUE);

		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(3);
		XYItemLabelGenerator generator = new StandardXYItemLabelGenerator("{2}", format, format);
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(true);

		return new ChartPanel(chart);
	}

	private XYDataset createDataset(double swingHigh, double swingLow, ArrayList<Double> prices, ArrayList<Double> shares,
			ArrayList<Double> avePrices) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Price");
		XYSeries series2 = new XYSeries("Average");
		// Leg
		series1.add(-1, swingLow);
		series1.add(-0, swingHigh);
		for (int i = 1; i <= prices.size(); i++) {
			series1.add(i, prices.get(i-1));
			series2.add(i, avePrices.get(i-1));
		}
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		return dataset;
	}
}
