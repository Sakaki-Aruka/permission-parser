import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class RPN {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        scanner.close();
        //String a = "A | B | C & (D | E | F & G) & (H | I)";
        //String a = "A | B & (C | D & E) | F";
        List<String> list = parsed(a);
        Map<Integer,List<String>> map = new HashMap<>();

        for (int i=0;i<list.size();i++) {
            map.put(i, separator(list.get(i)));
        }

        String ls = System.getProperty("line.separator");
        System.out.println(String.format("%s===%s",ls,ls));

        for (Map.Entry<Integer,List<String>> entry : map.entrySet()) {
            for (String s : entry.getValue()) {
                System.out.println(s);
            }
        }
    }

    private static List<String> separator(String input) {
        List<String> buffer = new ArrayList<>();
        Map<Integer,List<String>> map = new HashMap<>();
        int dim = 0;
        int start = 0;
        input = input.replace(" ", "");
        List<String> list = Arrays.asList(input.split(""));
        for (int i=0;i<input.length();i++) {
            String s = String.valueOf(input.charAt(i));
            if (s.equals("(")) {
                dim++;
                continue;
            }

            if (s.equals(")")) {
                dim--;
                continue;
            }

            if (s.equals("&") && dim == 0) {
                int index = map.isEmpty() ? 0 : Collections.max(map.keySet()) + 1;
                map.put(index, new ArrayList<>(Arrays.asList(String.join("",list.subList(start, i)))));
                start = i;
                continue;
            }
        }

        map.put(map.isEmpty() ? 0 : Collections.max(map.keySet()) + 1, new ArrayList<>(Arrays.asList(String.join("",list.subList(start, list.size())))));

        List<Integer> sizes = new ArrayList<>();
        List<Map<Integer,Integer>> orders = new ArrayList<>();
        for (Map.Entry<Integer,List<String>> entry : map.entrySet()) {
            List<String> l = entry.getValue();
            String s = l.get(0).startsWith("&") ? l.get(0).substring(1, l.get(0).length()) : l.get(0);
            s = s.replace("(", "").replace(")", "");
            entry.getValue().addAll(Arrays.asList(s.split("\\|")));
            entry.getValue().remove(0);
            sizes.add(entry.getValue().size());
        }

        for (int i=0;i<getAllElementsProduct(sizes);i++) {
            orders.add(new HashMap<>());
        }

        for (int i=0;i<sizes.size();i++) {
            make(i,sizes,orders);
        }

        for (Map<Integer,Integer> m : orders) {
            List<String> ll = new ArrayList<>();
            for (Map.Entry<Integer,Integer> entry : m.entrySet()) {
                ll.add(map.get(entry.getKey()).get(entry.getValue()));
            }
            buffer.add(String.join(",", ll));
        }

        return buffer;

    }

    private static int getAllElementsProduct(List<Integer> list) {
        int result = 1;
        for (int i : list) {
            result *= i;
        }
        return result;
    }

    private static void make(int index, List<Integer> source, List<Map<Integer,Integer>> map) {
        int c = getAllElementsProduct(source.subList(index, source.size()));
        int count = getAllElementsProduct(source.subList(index+1, source.size()));
        int all = getAllElementsProduct(source);
        int now = 0;
        for (int k=0;k<all/c;k++) {
            for (int i=0;i<source.get(index);i++) {
                for (int j=0;j<count;j++) {
                    map.get(now).put(index, i);
                    now++;
                }
            }
        }
    }

    private static List<String> parsed(String input) {
        StringBuilder builder = new StringBuilder();
        int depth = getDepth(input);
        int dim = 0;
        String separator = String.join("", Collections.nCopies(depth+1,"|"));
        for (String s : input.split("")) {
            if (s.equals("(")) dim++;
            if (s.equals(")")) dim--;
            if (s.equals("|") && dim ==0) {
                builder.append(separator);
                continue;
            }

            builder.append(s);
        }

        String sep = String.join("", Collections.nCopies(2, "\\|"));
        return new ArrayList<>(Arrays.asList(builder.toString().split(sep)));
    }

    private static int getDepth(String input) {
        Set<Integer> set = new HashSet<>();
        int d = 0;
        for (String s : input.split("")) {
            if (s.equals("(")) d++;
            if (s.equals(")")) d--;
            set.add(d);
        }
        return Collections.max(set);
    }
}
