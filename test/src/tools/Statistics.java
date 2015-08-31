package tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
 
/*
 * 数据频率统计
 */
public class Statistics<T> {
    private Map<T, Integer> m = new HashMap<T, Integer>();
 
    public void statistics(T t) {
       Integer freq = m.get(t);
       m.put(t, freq == null ? 1 : freq + 1);
    }
 
    public void getAllKeysStatistics() {
       System.out.println(m);
    }
 
    public void getAllKeys() {
       System.out.println(m.keySet());
    }
 
    public int getKeyStatistics(T t) {
       return m.get(t) == null ? 0 : m.get(t);
 
    }
 
    public static void main(String[] args) {
       Random rand = new Random();
       Statistics<Integer> s = new Statistics<Integer>();
       for (int i = 0; i < 10000; i++) {
           // Produce a number between 0 and 9:
           int r = rand.nextInt(10);
           s.statistics(r);
       }
       s.getAllKeysStatistics();
       s.getAllKeys();
       System.out.println("Key -1:" + s.getKeyStatistics(-1));
       System.out.println("Key 0:" + s.getKeyStatistics(0));
    }
}
