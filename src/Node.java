import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


    public class Node {

        List<Node> childs;
        String data;
        String incrementalPath;

        public Node(String nodeValue, String incrementalPath ) {
            childs = new ArrayList<Node>();
            data = nodeValue;
            this. incrementalPath = incrementalPath;
        }

        public void addElement(String currentPath, String[] list) {

            while( list[0] == null || list[0].equals("") )
                list = Arrays.copyOfRange(list, 1, list.length);

            Node currentChild = new Node(list[0], currentPath+"/" + list[0]);
            if ( list.length == 1 ) {
                childs.add( currentChild );
                return;
            } else {
                int index = childs.indexOf( currentChild );
                if ( index == -1 ) {
                    childs.add( currentChild );
                    currentChild.addElement(currentChild.incrementalPath, Arrays.copyOfRange(list, 1, list.length));
                } else {
                    Node nextChild = childs.get(index);
                    nextChild.addElement(currentChild.incrementalPath, Arrays.copyOfRange(list, 1, list.length));
                }
            }
        }

        @Override
        public boolean equals(Object obj) {
            Node cmpObj = (Node)obj;
            return incrementalPath.equals( cmpObj.incrementalPath ) && data.equals( cmpObj.data );
        }

        public void printNode( int increment ) {
            for (int i = 0; i < increment; i++) {
                System.out.print(" ");
            }
            System.out.println(increment > 1 ? incrementalPath.substring(incrementalPath.lastIndexOf('/')) : incrementalPath);
            for (Node n: childs)
                    n.printNode(increment+2);
        }

        @Override
        public String toString() {
            return data;
        }
    }

