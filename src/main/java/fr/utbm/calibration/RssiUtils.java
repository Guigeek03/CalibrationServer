package fr.utbm.calibration;

import java.awt.Point;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;

public class RssiUtils {

    private static final int RSSI_ZERO = -95;
    private static final double EPSILON = 0.001;

//	private static final int Min = 0;
//	private static final int Max = 10;
//	private static final String[] first = { "AB34", "CD432", "FR673", "PO432", "XL672"};
//	private static final String[] second = { "PF34", "CD432", "FR673", "PO432", "OD532"};
//
//
//	public static void main(String[] args) {
//		NavigableMap<String,Double> m1 = new TreeMap<String,Double>();
//		NavigableMap<String,Double> m2 = new TreeMap<String,Double>();
//		int i = 0;
//		double rssi;
//		
//		while(i < 5){
//			m1.put(first[i], (double) Min + (Math.random() * ((Max - Min) + 1)));
//			m2.put(second[i], (double) Min + (Math.random() * ((Max - Min) + 1)));
//			i++;
//		}
//		
//		rssi = rssi_distance(m1,m2);
//		
//		for (Entry<String, Double> entry : m1.entrySet())
//		{
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
//		
//		System.out.println(m1.lastEntry());
//		
//		System.out.println(" RSSI = " + rssi);
//
//	}
    static Double rssi_distance(NavigableMap<String, Double> m1, NavigableMap<String, Double> m2) {
        Iterator<Entry<String, Double>> it = m1.entrySet().iterator();
        Entry<String, Double> tmp = null;
        Entry<String, Double> tmp2 = null;
        double ret = 0;

        while (it.hasNext()) {
            tmp = it.next();
            tmp2 = m2.ceilingEntry(tmp.getKey());

            if (tmp2 == null) { // to avoid the java.lang.NullPointerException
                tmp2 = m2.floorEntry(tmp.getKey());
            }

            if (tmp.getKey().equals(tmp2.getKey())) {
                System.out.println("IN 1 : " + tmp.getKey());
                System.out.println("IN 2 : " + m2.ceilingEntry(tmp.getKey()));
                ret += (tmp.getValue() - tmp2.getValue()) * (tmp.getValue() - tmp2.getValue());
            } else {
                System.out.println("OUT 1 : " + tmp.getKey());
                ret += (tmp.getValue() - RSSI_ZERO) * (tmp.getValue() - RSSI_ZERO);
            }
        }

        it = m2.entrySet().iterator();

        while (it.hasNext()) {
            tmp = it.next();
            tmp2 = m1.ceilingEntry(tmp.getKey());

            if (tmp2 == null) { // to avoid the java.lang.NullPointerException
                tmp2 = m1.floorEntry(tmp.getKey());
            }

            if (!tmp.getKey().equals(tmp2.getKey())) {
                System.out.println("OUT 1 : " + tmp.getKey());
                ret += (tmp.getValue() - RSSI_ZERO) * (tmp.getValue() - RSSI_ZERO);
            } else {
                System.out.println("nothing");
            }
        }

        return ret;
    }

    public static Point closest_in_rssi(NavigableMap<String, Double> m1, NavigableMap<Point, NavigableMap<String, Double>> db) {
        Iterator<Entry< Point, NavigableMap<String, Double>>> it = db.entrySet().iterator();
        Entry<Point, NavigableMap<String, Double>> tmp = null;
        Point nearestPoint = null;
        double min = 0;
        double tmp_min = 0;

        if (it.hasNext()) {
            tmp = it.next();
            min = rssi_distance(m1, tmp.getValue());
            nearestPoint = tmp.getKey();
        }

        while (it.hasNext()) {
            tmp = it.next();
            tmp_min = rssi_distance(m1, tmp.getValue());
            if (tmp_min < min) {
                min = tmp_min;
                nearestPoint = tmp.getKey();
            }
        }

        return nearestPoint;
    }

    public static NavigableMap<Double, Point> k_closest_in_rssi(
            NavigableMap<String, Double> m, int k, NavigableMap<Point, NavigableMap<String, Double>> db) {
        NavigableMap<Double, Point> element = null;
        Entry<Double, Point> max_dist;
        Iterator<Entry< Point, NavigableMap<String, Double>>> it = db.entrySet().iterator();

        Entry<Point, NavigableMap<String, Double>> tmp;
        double tmp_dist;
        int i = 0;
        while (it.hasNext() && i < k) {
            tmp = it.next();
            element.put(rssi_distance(m, tmp.getValue()), tmp.getKey());
            i++;
        }

        max_dist = element.lastEntry();
        it = db.entrySet().iterator();

        while (it.hasNext()) {
            tmp = it.next();
            tmp_dist = rssi_distance(m, tmp.getValue());
            if (tmp_dist < max_dist.getKey()) {
                element.remove(max_dist.getKey());
                element.put(tmp_dist, tmp.getKey());
                max_dist = element.lastEntry();
            }
        }

        return element;
    }

    public static Point KWeightedAverage(NavigableMap<String, Double> m,
            int k, NavigableMap<Point, NavigableMap<String, Double>> db) {

        NavigableMap<Double, Point> tmp = k_closest_in_rssi(m, k, db);
        Iterator< Entry<Double, Point>> it = tmp.entrySet().iterator();
        Entry<Double, Point> element = null;
        double weight_sum = 0;
        Point p = null;

        while (it.hasNext()) {
            element = it.next();
            p.x += ((double) element.getValue().x / (element.getKey() + EPSILON));
            p.y += ((double) element.getValue().y / (element.getKey() + EPSILON));
            weight_sum += 1 / (element.getKey() + EPSILON);
        }

        p.x = (int) (p.x / weight_sum);
        p.y = (int) (p.y / weight_sum);

        return p;
    }

    public static Point viterbi(Point viterbi_data[][]) {
        int n = viterbi_data[0].length;
        int k = viterbi_data[1].length;
        Point res = viterbi_data[n - 1][0];
        int max = (int) Math.pow(k, n);
        double min_dist = 0, dist;
        int j, i;

        for (j = 0; j < n - 1; j++) {
            min_dist += viterbi_data[j][0].distance(viterbi_data[j + 1][0]);
        }

        for (i = 0; i < max; i++) {
            dist = 0;
            for (j = 0; j < n - 1; j++) {
                dist += viterbi_data[n - 1 - j][(int) ((i / Math.pow(k, j)) % k)].distance(viterbi_data[n - 2 - j][(int) ((i / Math.pow(k, j + 1)) % k)]);
            }
            if (dist < min_dist) {
                min_dist = dist;
                res = viterbi_data[n - 1][i % k];
            }
        }

        return res;
    }
}
