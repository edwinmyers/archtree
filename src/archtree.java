import java.io.IOException;

import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.File;
import javafx.util.Pair;



public class archtree {


    public static Mytree parse_folders(Enumeration<? extends ZipEntry> entries){
        ArrayList<String> list = new ArrayList<String>();
        Mytree new_tree = new Mytree(new Node(".", "."));
        ArrayList<Pair<String, String>> new_list = new ArrayList<>();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
            File file = new File(entry.getName());
            String name = entry.getName();
            if (!name.startsWith("_"))
            {
                list.add(name);
                if (!entry.isDirectory()) {
                    if (name.lastIndexOf('/') != -1)
                        list.remove(name.substring(0, name.lastIndexOf('/') + 1));
                }
            }
        }
        for (int i = 0; i < list.size(); ++i){
            String str = list.get(i);
            File file = new File(str);
            if (str.lastIndexOf('.') != -1 && !file.isHidden())
                new_list.add(new Pair(str.substring(str.lastIndexOf('.') + 1), str));
            else
                new_list.add(new Pair("", str));
        }
        Collections.sort(new_list, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                return o1.getKey().toString().compareTo(o2.getKey().toString());
            }
        });
        for (int i = 0; i < list.size(); ++i)
            new_tree.addElement(new_list.get(i).getValue());
        new_tree.printTree();
        return new_tree;
    }

    public static void main(String[] args) {
        try {
            ArrayList files;
            Scanner scan = new Scanner(System.in);
            System.out.println(new Scanner(System.in));
            System.out.println("input zip file path");
            String input = scan.nextLine();
            ZipFile zip = new ZipFile(input);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            parse_folders(entries);

        } catch (IOException e) {
            System.out.println("Can't open file");
        }
    }
}