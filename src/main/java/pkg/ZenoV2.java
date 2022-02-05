package pkg;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

public class ZenoV2 {
	public static void main(String[] args) {
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void start() {
		double capital = 20000.00;
		double swingHigh = 422.25;
		double swingLow = 400.00;
 
		System.out.println("Capital:    $" + capital);
		System.out.println("Swing High: $" + swingHigh);
		System.out.println("Swing Low:  $" + swingLow);

		System.out.printf("Range: 	    $%3.3f", (swingHigh - swingLow));
		System.out.println(
				"\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		//ArrayList<Double> fibRetracements = fibRetracements(swingHigh, swingLow);
		ArrayList<Double> halfFibRetracements = halfFibRetracements(swingHigh, swingLow);
		//ArrayList<Double> fiveCentRetracements = fiveCentRetracements(swingHigh, swingLow);
		ExecutorService executor = Executors.newFixedThreadPool(1);
		//executor.execute(new nTwo(swingHigh, swingLow, capital, halfFibRetracements));
		executor.execute(new twoN(swingHigh, swingLow, capital, halfFibRetracements));
		//executor.execute(new nFact(swingHigh, swingLow, capital, halfFibRetracements));
		executor.shutdown();
	}
	
	public static ArrayList<Double> fiveCentRetracements(double swingHigh, double swingLow) {
		ArrayList<Double> fib = new ArrayList<Double>();
		double diff = swingHigh - swingLow;
		for (int i = 1; i <= diff/5; i++) {
				fib.add(swingHigh - (0.5 * i));
		}
		return fib;
	}
	
	public static ArrayList<Double> fibRetracements(double swingHigh, double swingLow) {
		ArrayList<Double> fib = new ArrayList<Double>();
		double diff = swingHigh - swingLow;
		for (int i = 1; i <= 6; i++) {
			switch (i) {
			case 1:
				fib.add(swingHigh - diff * 0.236);
			case 2:
				fib.add(swingHigh - diff * 0.382);
			case 3:
				fib.add(swingHigh - diff * 0.500);
			case 4:
				fib.add(swingHigh - diff * 0.618);
			case 5:
				fib.add(swingHigh - diff * 0.786);
			case 6:
				fib.add(swingHigh - diff * 1.00);
			}
			if (fib.size() >= 6) {
				break;
			}
		}
		return fib;
	}

	public static ArrayList<Double> halfFibRetracements(double swingHigh, double swingLow) {
		ArrayList<Double> fib = new ArrayList<Double>();
		double diff = swingHigh - swingLow;
		for (int i = 1; i <= 12; i++) {
			switch (i) {
			case 1:
				fib.add(swingHigh - diff * 0.118);
			case 2:
				fib.add(swingHigh - diff * 0.236);
			case 3:
				fib.add(swingHigh - diff * 0.309);
			case 4:
				fib.add(swingHigh - diff * 0.382);
			case 5:
				fib.add(swingHigh - diff * 0.441);
			case 6:
				fib.add(swingHigh - diff * 0.500);
			case 7:
				fib.add(swingHigh - diff * 0.559);
			case 8:
				fib.add(swingHigh - diff * 0.618);
			case 9:
				fib.add(swingHigh - diff * 0.702);
			case 10:
				fib.add(swingHigh - diff * 0.786);
			case 11:
				fib.add(swingHigh - diff * 0.893);
			case 12:
				fib.add(swingHigh - diff * 1.00);
				break;
			}
			if (fib.size() >= 12) {
				break;
			}
		}
		return fib;
	}
}

class nTwo implements Runnable {
	double swingHigh = 0;
	double swingLow = 0;
	double capital = 0;
	ArrayList<Double> fibRetracements;

	public nTwo(double swingHigh, double swingLow, double capital, ArrayList<Double> fibRetracements) {
		this.swingHigh = swingHigh;
		this.swingLow = swingLow;
		this.capital = capital;
		this.fibRetracements = fibRetracements;
	}

	public void run() {
		nTwoCompute(swingHigh, swingLow, fibRetracements);
	}

	public void nTwoCompute(double swingHigh, double swingLow, ArrayList<Double> fibRetracements) {
		System.out.println("\n\nRate of growth: n^2\n");
		double n = 0.001;
		int fibC = 0;
		ArrayList<Double> l1 = new ArrayList<>();
		ArrayList<Double> l2 = new ArrayList<>();
		ArrayList<Double> l3 = new ArrayList<>();
		ArrayList<Double> l4 = new ArrayList<>();
		ArrayList<Double> l5 = new ArrayList<>();

		while (true) {
			try {
				double x = 0;
				double totalSharesBought = 0;
				double totalAmountBought = 0;
				for (int i = 0; i < fibRetracements.size(); i++) {
					double temp = Math.round(Math.pow(i + n, 2));
					x = fibRetracements.get(i);
					totalSharesBought += (temp);
					totalAmountBought += (temp * x);

					l1.add(x);
					l2.add(temp);
					l3.add(temp * x);
					l4.add(totalAmountBought / totalSharesBought);
					l5.add(totalAmountBought);

				}

				if (totalAmountBought < capital) {
					n += 0.001;
					l1.clear();
					l2.clear();
					l3.clear();
					l4.clear();
					l5.clear();
				} else if (totalAmountBought - capital > 100) {
					n = 0.001;
					l1.clear();
					l2.clear();
					l3.clear();
					l4.clear();
					l5.clear();
					fibRetracements.remove(fibC);
					fibC++;
				} else {
					System.out.printf("%-10s" + "%-8s" + "%15s" + "%20s" + "%21s" + "%23s", "Order #", " | Price",
							" | Shares", " | Order Cost", " | Average Price", " | Capital Used");
					for (int i = 0; i < fibRetracements.size(); i++) {
						System.out.println(
								"\n------------------------------------------------------------------------------------------------------------------");
						System.out.printf(
								"%3d" + "%10s" + "$%3.4f" + "%7s" + "%-5.0f" + "%11s" + "$%-5.2f" + "%12s" + "$%5.3f"
										+ "%18s" + "$%5.2f",
								i + 1, " | ", l1.get(i), " | ", l2.get(i), " | ", l3.get(i), " | ", l4.get(i), " | ",
								l5.get(i));
					}
					printInfo.printInfoM(x, totalAmountBought, totalSharesBought);
					callGraph.printGraph(swingHigh, swingLow, l1, l2, l4);
					break;
				}
			} catch (Exception e) {
				System.out.println("Exception at: " + LocalTime.now() + " -- Exception: " + e.toString() + "\n");
			}
		}
	}
}

class twoN implements Runnable {
	double swingHigh = 0;
	double swingLow = 0;
	double capital = 0;
	ArrayList<Double> fibRetracements;

	public twoN(double swingHigh, double swingLow, double capital, ArrayList<Double> fibRetracements) {
		this.swingHigh = swingHigh;
		this.swingLow = swingLow;
		this.capital = capital;
		this.fibRetracements = fibRetracements;
	}

	public void run() {
		twoNCompute(swingHigh, swingLow, fibRetracements);
	}

	public void twoNCompute(double swingHigh, double swingLow, ArrayList<Double> fibRetracements) {
		System.out.println("\n\nRate of growth: 2^n\n");
		double n = 0.001;
		int fibC = 0;
		ArrayList<Double> l1 = new ArrayList<>();
		ArrayList<Double> l2 = new ArrayList<>();
		ArrayList<Double> l3 = new ArrayList<>();
		ArrayList<Double> l4 = new ArrayList<>();
		ArrayList<Double> l5 = new ArrayList<>();
		
		boolean flag = false;
				
		while (true) {
			try {
				double x = 0;
				double totalSharesBought = 0;
				double totalAmountBought = 0;
				for (int i = 0; i < fibRetracements.size(); i++) {
					double temp = Math.round(Math.pow(2 + n, i));
					x = fibRetracements.get(i);
					totalSharesBought += (temp);
					totalAmountBought += (temp * x);
					l1.add(x);
					l2.add(temp);
					l3.add(temp * x);
					l4.add(totalAmountBought / totalSharesBought);
					l5.add(totalAmountBought);
				}
				if (totalAmountBought < capital && flag == false) {
					//System.out.println("totalAmountBought < capital");
					n += 0.001;
					l1.clear();
					l2.clear();
					l3.clear();
					l4.clear();
					l5.clear();
				} else if (totalAmountBought > capital + 100 && flag == false) {
					//System.out.println("totalAmountBought > capital");
					n =  0.001;
					l1.clear();
					l2.clear();
					l3.clear();
					l4.clear();
					l5.clear();
					//System.out.println(fibC + " " + fibRetracements.size());
					if(fibRetracements.size() <= 6) {
						System.out.println("Could not be accomplished with alotted capital, best case scenario:\n");
						flag = true;
						continue;
					}
					else if(fibC < fibRetracements.size()) {
						//System.out.println("Yaga");
						fibRetracements.remove(fibC);
						fibC++;
					}

				} else {  
					System.out.printf("%-10s" + "%-8s" + "%15s" + "%20s" + "%21s" + "%23s" + "%23s", "Order #", " | Price",
							" | Shares", " | Order Cost", " | Average Price", " | Capital Used", " | Fib Level");
					for (int i = 0; i < fibRetracements.size(); i++) {
						System.out.println(
								"\n------------------------------------------------------------------------------------------------------------------");
						System.out.printf(
								"%3d" + "%10s" + "$%3.4f" + "%7s" + "%-5.0f" + "%11s" + "$%-5.2f" + "%12s" + "$%5.3f"
										+ "%18s" + "$%5.2f" + "%18s" + "%5.2f",
								i + 1, " | ", l1.get(i), " | ", l2.get(i), " | ", l3.get(i), " | ", l4.get(i), " | ",
								l5.get(i), " | ", fibRetracements.get(i));
					}
					
					
					
					double sl1 = swingLow - ((swingHigh - swingLow) * 0.618);
					double sl2 = swingLow - ((swingHigh - swingLow) * 1.618);
					
					System.out.println("\n\n168%   retracement level: $"+ sl1);
					System.out.println("261.8% retracement level: $" + sl2);
					
					printInfo.printInfoM(x, totalAmountBought, totalSharesBought);
					callGraph.printGraph(swingHigh, swingLow, l1, l2, l4);
					break;
				 }
			} catch (Exception e) {
				System.out.println("Exception at: " + LocalTime.now() + " -- Exception: " + e.toString() + "\n" + e.toString());
				e.printStackTrace();
				break;
			}
		}
	}
}

class nFact implements Runnable {
	double swingHigh = 0;
	double swingLow = 0;
	double capital = 0;
	ArrayList<Double> fibRetracements;

	public nFact(double swingHigh, double swingLow, double capital, ArrayList<Double> fibRetracements) {
		this.swingHigh = swingHigh;
		this.swingLow = swingLow;
		this.capital = capital;
		this.fibRetracements = fibRetracements;
	}

	public void run() {
		nFactCompute(swingHigh, swingLow, fibRetracements);
	}

	public void nFactCompute(double swingHigh, double swingLow, ArrayList<Double> fibRetracements) {
		System.out.println("\n\nRate of growth: n!\n");
		double n = 0.001;
		int fibC = 0;
		ArrayList<Double> l1 = new ArrayList<>();
		ArrayList<Double> l2 = new ArrayList<>();
		ArrayList<Double> l3 = new ArrayList<>();
		ArrayList<Double> l4 = new ArrayList<>();
		ArrayList<Double> l5 = new ArrayList<>();

		while (true) {
			try {
				double x = 0;
				double totalSharesBought = 0;
				double totalAmountBought = 0;
				double res = 0.001 + n;
				int c = 2;
				for (int i = 0; i < fibRetracements.size(); i++) {
					res *= c++;
					double temp = res;
					if (temp < 1) {
						break;
					}
					x = fibRetracements.get(i);
					totalSharesBought += (temp);
					totalAmountBought += (temp * x);

					l1.add(x);
					l2.add(temp);
					l3.add(temp * x);
					l4.add(totalAmountBought / totalSharesBought);
					l5.add(totalAmountBought);

				}

				if (totalAmountBought < capital) {
					n += 0.001;
					l1.clear();
					l2.clear();
					l3.clear();
					l4.clear();
					l5.clear();
				} else if (totalAmountBought - capital > 100) {
					n = 0.001;
					l1.clear();
					l2.clear();
					l3.clear();
					l4.clear();
					l5.clear();
					fibRetracements.remove(fibC);
					fibC++;
				} else {
					System.out.printf("%-10s" + "%-8s" + "%15s" + "%20s" + "%21s" + "%23s", "Order #", " | Price",
							" | Shares", " | Order Cost", " | Average Price", " | Capital Used");
					for (int i = 0; i < l1.size(); i++) {
						System.out.println(
								"\n------------------------------------------------------------------------------------------------------------------");
						System.out.printf(
								"%3d" + "%10s" + "$%3.4f" + "%7s" + "%-5.0f" + "%11s" + "$%-5.2f" + "%12s" + "$%5.3f"
										+ "%18s" + "$%5.2f",
								i + 1, " | ", l1.get(i), " | ", l2.get(i), " | ", l3.get(i), " | ", l4.get(i), " | ",
								l5.get(i));
					}
					printInfo.printInfoM(x, totalAmountBought, totalSharesBought);
					callGraph.printGraph(swingHigh, swingLow, l1, l2, l4);
					break;
				}
			} catch (Exception e) {
				System.out.println("Exception at: " + LocalTime.now() + " -- Exception: " + e.toString() + "\n");
			}
		}
	}
}

class printInfo {
	public static void printInfoM(double x, double totalAmountBought, double totalSharesBought) {
		DecimalFormat f = new DecimalFormat("##.000");
		DecimalFormat s = new DecimalFormat("##");
		DecimalFormat v = new DecimalFormat("##.00");
		if (x < 1.0) {
			System.out.println("\nTerminal Stock Price:    $0" + f.format(x));
		} else {
			System.out.println("\nTerminal Stock Price:    $" + f.format(x));
		}
		if (totalAmountBought / totalSharesBought < 1.0) {
			System.out.println("Total Average Price:     $0" + f.format(totalAmountBought / totalSharesBought));
		} else {
			System.out.println("Total Average Price:     $" + f.format(totalAmountBought / totalSharesBought));
		}
		System.out.println("Total Shares:            #" + s.format(totalSharesBought));
		System.out.println("Total Capital Required:  $" + v.format(totalAmountBought));
	}
}

class callGraph {
	public static void printGraph(final double swingHigh, final double swingLow, final ArrayList<Double> prices,
			final ArrayList<Double> shares, final ArrayList<Double> avePrices) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new XYLineChartExample(swingHigh, swingLow, prices, shares, avePrices).setVisible(true);
			}
		});
	}
}