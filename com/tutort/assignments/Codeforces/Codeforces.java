package com.tutort.assignments.Codeforces;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Codeforces implements Runnable {

    static int modulo = (int) 1e9 + 7;
    static FastReader sc = new FastReader();
    static int st = -1;
    static ArrayList<ArrayList<Integer>> tree;

    static int count = 0;

    public static void main(String[] args) throws IOException {
        new Thread(null, new Codeforces(), "Main", 1 << 28).start();
    }

    public static void sieve(TreeSet<Integer> primes) {
        boolean[] arr = new boolean[(2 * (int) 1e5) + 1];
        Arrays.fill(arr, true);
        arr[1] = false;
        arr[2] = true;

        for (int i = 1; i < arr.length; i++) {
            if (!arr[i]) continue;

            primes.add(i);
            for (int j = i; j < arr.length; j += i) {
                arr[j] = false;
            }
        }
    }

    public static void dfs(int[] max, int parent, int cur, int dist) {
        ArrayList<Integer> node = tree.get(cur);
        int cnt = 0;
        for (Integer i : node) {
            if (i != parent) {

                dfs(max, cur, i, dist + 1);

            }
        }
        if (dist > max[0]) {
            max[0] = dist;
            max[1] = cur;

        }
    }

    public static int binarySearch(int i, int maxsum, int[] arr, long[] prefix) {
        long cur = 0;
        if (i > 0) cur = prefix[i - 1];
        int low = i;
        int high = arr.length - 1;
        int ans = i;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (prefix[mid] - cur > maxsum) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static long query(int l, int r, long[] trees, long[] arr) {
        long ans = query(0, arr.length - 1, l, r, trees, 0);
        return ans;
    }

    public static long query(int st, int end, int l, int r, long[] trees, int idx) {
        if (st > r || end < l) {

            return 1;
        }
        if (st >= l && end <= r) {

            long arr = trees[idx];
            return arr;
        }
        int mid = (st + end) / 2;

        long arr1 = query(st, mid, l, r, trees, 2 * idx + 1);
        long arr2 = query(mid + 1, end, l, r, trees, 2 * idx + 2);
        return arr1 + arr2;
    }

    public static void update(long[] trees, long diff, int idx, long[] arr) {
        updateTrees(trees, diff, idx, 0, arr.length - 1, 0);
        arr[idx] += diff;
    }

    public static void updateTrees(long[] trees, long diff, int idx, int st, int end, int curidx) {
        if (st > idx || end < idx) {
            return;
        }
        if (st == idx && end == idx) {
            trees[curidx] += diff;
            return;
        }

        int mid = (st + end) / 2;

        updateTrees(trees, diff, idx, st, mid, 2 * curidx + 1);
        updateTrees(trees, diff, idx, mid + 1, end, 2 * curidx + 2);

        trees[curidx] = (trees[2 * curidx + 1] + trees[2 * curidx + 2]);
    }

    public static long[] create(long[] arr) {
        long[] trees = new long[4 * arr.length];
        createTrees(trees, arr, 0, arr.length - 1, 0);
        return trees;
    }

    public static void createTrees(long[] trees, long[] arr, int st, int end, int idx) {
        if (st == end) {
            trees[idx] = arr[st];
            // trees[idx];
            return;
        }

        int mid = (st + end) / 2;

        createTrees(trees, arr, st, mid, 2 * idx + 1);
        createTrees(trees, arr, mid + 1, end, 2 * idx + 2);

        trees[idx] = (trees[idx * 2 + 1] + trees[2 * idx + 2]);
    }

    public static void solve() {
    }

    public static boolean mapRemove(TreeMap<Integer, Integer> map, int val) {
        if (!map.containsKey(val)) return false;
        map.put(val, map.get(val) - 1);
        if (map.get(val) <= 0) map.remove(val);
        return true;
    }

    public static void mapadd(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    public static int mapcnt(TreeMap<Integer, Integer> map, int val) {
        if (!map.containsKey(val)) return 0;
        return map.get(val);

    }

    static long nCr(int n, int r) {
        return fact(n) / (fact(r) *
                fact(n - r));
    }

    // Returns factorial of n
    static long fact(int n) {
        if (n == 0)
            return 1;
        long res = 1;
        for (int i = 2; i <= n; i += 2)
            res = res * i;
        return res;
    }

    static long power(long x, long y, long p) {
        long res = 1; // Initialize result

        while (y > 0) {

            // If y is odd, multiply x with result
            if ((y & 1) != 0)
                res = res * x;

            // y must be even now
            y = y >> 1; // y = y/2
            x = x * x; // Change x to x^2
        }
        return res % p;
    }

    static long moduloMultiplication(long a,
                                     long b, long mod) {

        // Initialize result
        long res = 0;

        // Update a if it is more than
        // or equal to mod
        a %= mod;

        while (b > 0) {

            // If b is odd, add a with result
            if ((b & 1) > 0) {
                res = (res + a) % mod;
            }

            // Here we assume that doing 2*a
            // doesn't cause overflow
            a = (2 * a) % mod;

            b >>= 1; // b = b / 2
        }
        return res;
    }

    public static void print(String str) {
        System.out.println(str);
    }

    public static void print(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append((arr[i]) + " ");
        }
        print(sb.toString());
    }

    public static void print(long[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i] + " ");
        }
        print(sb.toString());
    }

    public static void print(boolean f, StringBuilder sb) {
        if (f) sb.append("YES\n");
        else sb.append("NO\n");
    }

    static long g1cd(long a, long b) {
        if (b == 0) return a;
        else return g1cd(b, a % b);
    }

    static long lcm(long a, long b) {
        return ((a * b)) / gcd(a, b);
    }

    static long sum(long n) {
        return (n * (n + 1L)) / 2L;
    }

    public static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int isPrime(int n) {
        if (n <= 1) return -1;
        if (n == 2 || n == 3) return -1;
        if (n % 2 == 0) return 2;
        if (n % 3 == 0) return 3;
        for (int i = 5; i <= Math.sqrt(n); i = i + 6) {
            if (n % i == 0) return i;
            if (n % (i + 2) == 0) return i + 2;
        }
        return -1;
    }

    public static int modInverse(int q, int prime) {
        // Calculate the multiplicative inverse of q modulo prime
        return BigInteger.valueOf(q).modInverse(BigInteger.valueOf(prime)).intValue();
    }

    public static int probabilityModulo(double probability) {
        // Extract the integer part and decimal part
        int p = (int) probability;  // Integer part
        double decimalPart = probability - p;  // Decimal part

        // Convert decimal part to a large integer
        long q = (long) (decimalPart * 1e9);  // Scaling to preserve precision

        // Calculate (p * q^-1) modulo 10^9 + 7
        int MOD = 1000000007;
        int qInv = modInverse((int) q, MOD);
        int result = (int) (((long) p * qInv) % MOD);
        return result;
    }

    static long gcd(long a, long b) {
        if (a == 0)
            return b;
        else if (b == 0)
            return a;
        if (a < b)
            return gcd(a, b % a);
        else
            return gcd(b, a % b);
    }

    public void run() {
        int tests = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < tests; t++) {
            int n = sc.nextInt();
            int maxlen = sc.nextInt() - 1;
            int power = sc.nextInt();
            int ans = 0;

            char[] ch = sc.next().toCharArray();
            int cur = maxlen;

            for (int i = 0; i < ch.length; i++) {
                //  System.out.print(cur+" ");
                if (ch[i] == '0') cur--;
                else cur = maxlen;

                if (cur < 0) {
                    int k = i;
                    ans++;
                    for (; i < Math.min(ch.length, k + power); i++) {
                        ch[i] = '1';

                    }
                    i--;
                    cur = maxlen;

                }
            }
            // print(Arrays.toString(ch));
            sb.append(ans + "\n");
        }
        print(sb + " ");
    }

    public boolean isValid(int x, int y, char[][] arr) {
        return x < arr.length && x >= 0 && y < arr[0].length && y >= 0;
    }

    public int binarySearch(int idx, long[] trees, long[] arr, int fact) {
        int st = idx;
        int high = arr.length - 1;
        int ans = idx;
        int low = idx;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (query(st, mid, trees, arr) % fact == 0) {
                high = mid - 1;
            } else {
                print(st + " " + mid + " " + query(st, mid, trees, arr));
                low = mid + 1;
                ans = mid;
            }

        }
        print(ans + " ");
        return ans;
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                if (st.hasMoreTokens()) {
                    str = st.nextToken("\n");
                } else {
                    str = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}


class Pair {
    long first = -1;
    long second = -1;

    Pair(long f, long s) {
        first = f;
        second = s;
    }
}


class Tuple {
    int first = -1;
    int second = -1;
    int third = -1;

    Tuple(int f, int s, int t) {
        first = f;
        second = s;
        third = t;
    }
}


class PairComparator implements Comparator<Pair> {
    @Override
    public int compare(Pair p1, Pair p2) {
        // Compare integers in reverse order
        if (p1.second < p2.second) {
            return 1;
        }
        return -1;
    }
}

class TupleComparator implements Comparator<Tuple> {
    @Override
    public int compare(Tuple p1, Tuple p2) {
        // Compare integers in reverse order
        if (p1.third > p2.third) {
            return 1;
        }
        if (p1.third == p2.third) return 0;

        return -1;
    }
}


class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private final DataInputStream din;
    private final byte[] buffer;
    private int bufferPointer, bytesRead;

    public Reader() {
        din = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public Reader(String file_name) throws IOException {
        din = new DataInputStream(
                new FileInputStream(file_name));
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public String readLine() throws IOException {
        byte[] buf = new byte[64]; // line length
        int cnt = 0, c;
        while ((c = read()) != -1) {
            if (c == '\n') {
                if (cnt != 0) {
                    break;
                } else {
                    continue;
                }
            }
            buf[cnt++] = (byte) c;
        }
        return new String(buf, 0, cnt);
    }

    public int nextInt() throws IOException {
        int ret = 0;
        byte c = read();
        while (c <= ' ') {
            c = read();
        }
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (neg)
            return -ret;
        return ret;
    }

    public long nextLong() throws IOException {
        long ret = 0;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();
        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');
        if (neg)
            return -ret;
        return ret;
    }

    public double nextDouble() throws IOException {
        double ret = 0, div = 1;
        byte c = read();
        while (c <= ' ')
            c = read();
        boolean neg = (c == '-');
        if (neg)
            c = read();

        do {
            ret = ret * 10 + c - '0';
        } while ((c = read()) >= '0' && c <= '9');

        if (c == '.') {
            while ((c = read()) >= '0' && c <= '9') {
                ret += (c - '0') / (div *= 10);
            }
        }

        if (neg)
            return -ret;
        return ret;
    }

    private void fillBuffer() throws IOException {
        bytesRead = din.read(buffer, bufferPointer = 0,
                BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() throws IOException {
        if (din == null)
            return;
        din.close();
    }

}
