import java.util.*;
class C{
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(2,2,2,3));
        List<Map<Integer,Integer>> map = new ArrayList<>();

        for (int i=0;i<getAllElementsProduct(list);i++) {
            map.add(new HashMap<>());
        }

        for (int i=0;i<list.size();i++) {
            make(i,list,map);
        }

        //map.forEach(s->System.out.println(s));
        map.forEach(s->System.out.println(s.values()));
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
}