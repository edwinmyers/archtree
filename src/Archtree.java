import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.File;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;


public class Archtree {

    public static void parse(Enumeration<? extends ZipEntry> entries, String zipName){ // method to place all files into their corresponding lists and then into a tree

        ArrayList<String> folder_list = new ArrayList<String>();
        ArrayList<Pair<String, String>> file_list = new ArrayList<>();
        ArrayList<String> hidden_list = new ArrayList<String>();
        Mytree new_tree = new Mytree(new Node(zipName, zipName + ":"));

        while (entries.hasMoreElements())
        {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();
            File file = new File(name);
            Boolean is_hidden = file.isHidden();
            Boolean contains_slash = name.lastIndexOf('/') != -1;

            if (entry.isDirectory() && !is_hidden) // adding non-hidden folders to a separate list
                folder_list.add(name);
            else if (!entry.isDirectory() && !is_hidden){ // adding non-hidden files
                if (contains_slash) // checking if there's at least one slash in the pathname
                    folder_list.remove(name.substring(0, name.lastIndexOf('/') + 1)); // removing folders that have files in them from the folder_list
                if (name.lastIndexOf('.') != -1) // checking for extension, if there is one i'll cut it from the string and put it as Key
                    file_list.add(new Pair<String, String>(name.substring(name.lastIndexOf('.') + 1), name));
                else
                    file_list.add(new Pair<String, String>(name.lastIndexOf("/") != -1 ? name.substring(name.lastIndexOf("/") + 1) : name, name)); // adding files name as its Key in case there's no extension
            }
            else{ // adding hidden folders or files to one hidden_list
                if (contains_slash)
                    folder_list.remove(name.substring(0, name.lastIndexOf('/') + 1)); // removing folders that have files in them from the folder_list
                hidden_list.add(name);
            }
        }

        // adding items from lists to my tree to then print out a directory tree;

        for (int i = 0; i < hidden_list.size(); ++i)
            new_tree.addElement(hidden_list.get(i));
        for (int i = 0; i < folder_list.size(); ++i)
            new_tree.addElement(folder_list.get(i));
        for (int i = 0; i < file_list.size(); ++i)
            new_tree.addElement(file_list.get(i).getValue());

        sort_tree(new_tree.root); // sorting by extension if there is one and by filename in case there is none or all files have the same extension
        new_tree.printTree(); // printing out the tree
    }

    public static void sort_tree(Node root){ // method to sort the tree by extension
        if (root == null)
            return ;

        root.childs.sort(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                String name1 = o1.incrementalPath.substring(o1.incrementalPath.lastIndexOf("."));
                String name2 = o2.incrementalPath.substring(o2.incrementalPath.lastIndexOf("."));
                if (o1.incrementalPath.lastIndexOf(".") == -1 || o2.incrementalPath.lastIndexOf(".") == -1)
                    return o1.incrementalPath.compareTo(o2.incrementalPath);
                else if (name1.compareTo(name2) == 0)
                    return o1.incrementalPath.compareTo(o2.incrementalPath);
                return (name1.compareTo(name2));
            }
        });

        for (Node n: root.childs) {
            sort_tree(n);
        }
    }

    public static void main(String[] args) { // main method
        if (args.length != 1) { // checking for correct argument input
            System.out.println("usage: java archtree <zipfile.zip> (only one zip supported)");
            System.exit(-1);
        }

        try {
            ZipFile zip = new ZipFile(args[0]);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            String name = zip.getName();
            Boolean contains_slash = name.lastIndexOf("/") != -1;
            parse(entries, contains_slash ? name.substring(name.lastIndexOf("/") + 1) : name);
        } catch (IOException e) {
            System.out.println("Can't open file");
        }
    }
}
