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
        while (entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();
            if (!name.startsWith("_"))
            {
                list.add(name);
                if (!entry.isDirectory()) {
                    if (name.lastIndexOf('/') != -1)
                        list.remove(name.substring(0, name.indexOf('/') + 1));
                }
            }
        }
        for (String s : list)
            new_tree.addElement(s);
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