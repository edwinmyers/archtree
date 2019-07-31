import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.File;

import javafx.util.Pair;


public class Archtree {


    public static Mytree parse_folders(Enumeration<? extends ZipEntry> entries, String zipName){
        ArrayList<String> list = new ArrayList<String>();
        Mytree new_tree = new Mytree(new Node(zipName, zipName + ":"));
        ArrayList<Pair<String, String>> new_list = new ArrayList<>();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
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
            if (str.lastIndexOf('.') != -1 && (!file.isHidden()))
                new_list.add(new Pair<String, String>(str.substring(str.lastIndexOf('.') + 1), str));
            else if (!str.endsWith("/"))
                new_list.add(new Pair<String, String>("", str));
            else
                new_list.add(new Pair<String, String>(str, str));
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
        if (args.length != 1) {
            System.out.println("usage: java archtree <zipfile.zip> (only one zip supported)");
            System.exit(-1);
        }
        try {
            ZipFile zip = new ZipFile(args[0]);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            String name = zip.getName();
            parse_folders(entries, name.lastIndexOf("/") != -1 ? name.substring(name.lastIndexOf("/") + 1) : name);
        } catch (IOException ezip) {
            System.out.println("Can't open file");
        }
    }
}
