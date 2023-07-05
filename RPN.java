import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class RPN {
    public static void main(String[] args) {
        String a = "A | B | C & (D | E | F & G) & (H | I)";
        List<String> list = parsed(a);
        System.out.println(list);
        for (String s : list) {
            separator(s);
        }
    }

    private static void separator(String input) {
        List<String> result = new ArrayList<>();
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
        for (Map.Entry<Integer,List<String>> entry : map.entrySet()) {
            List<String> l = entry.getValue();
            String s = l.get(0).startsWith("&") ? l.get(0).substring(1, l.get(0).length()) : l.get(0);
            s = s.replace("(", "").replace(")", "");
            entry.getValue().addAll(Arrays.asList(s.split("\\|")));
            entry.getValue().remove(0);
            sizes.add(entry.getValue().size());
        }

        
        System.out.println(map);
    }

    private static 

    private static List<String> parsed(String input) {
        StringBuilder builder = new StringBuilder();
        int depth = getDepth(input);
        int dim = 0;
        String separator = String.join("", Collections.nCopies(depth+1,"|"));
        for (String s : input.split("")) {
            if (s.equals("(")) {
                dim++;
                //continue;
            }

            if (s.equals(")")) {
                dim--;
                //continue;
            }

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