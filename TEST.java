import java.util.*;

import javax.print.event.PrintJobListener;

import org.w3c.dom.css.CSS2Properties;
class TEST{
    public static void main(String[] args) {
        TEST t = new TEST();
        String a = "A | ((B & C) | (D & F)) | (G & H & I) & J";
        String b = "A | B | ((C & D & E) | (F & G & H) & I)";
        System.out.println(String.format("a: %s -> %s",a,t.loader(a)));
        System.out.println(String.format("b: %s -> %s",b,t.loader(b)));
        t.pass(t.loader(a),"B");
    }

    private boolean pass(String ciper, String permission) {
        Set<Integer> set = new HashSet<>();
        int c = 0;
        for (int i=0;i<ciper.length();i++) {
            String s = String.valueOf(ciper.charAt(i));
            c = s.equals("|") ? c + 1 : 0;
            set.add(c);
        }
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        int deep = list.get(list.size()-1);
        String separator = String.join("", Collections.nCopies(deep, "\\|"));
        for (String s : ciper.split(separator)){
            System.out.println(s);

        }
        return false;
    }

    private String loader(String input) {
        int c = 0;
        Set<Integer> set = new HashSet<>();
        for (int i=0;i<input.length();i++) {
            String s = String.valueOf(input.charAt(i));
            if (s.equals("(")) c++;
            if (s.equals(")")) c--;

            set.add(c);
        }
        List<Integer> list = new ArrayList<>(set);
        Collections.sort(list);
        int deep = list.get(list.size()-1);
        //System.out.println(deep);
        
        StringBuilder builder = new StringBuilder();
        c = 0;
        for (int i=0;i<input.length();i++) {
            String s = String.valueOf(input.charAt(i));
            if (s.equals("(")) c++;
            if (s.equals(")")) c--;

            if (s.equals("|")) {
                builder.append(String.join("",Collections.nCopies(deep-c+1, "|")));
                continue;
            } else if (s.equals("&")){
                builder.append(String.join("", Collections.nCopies(c+1, "#")));
                continue;
            }else if (s.equals("(") || s.equals(")") || s.equals(" ")) {
                continue;
            }
            
            builder.append(s);
        }
        return builder.toString();
    }
}