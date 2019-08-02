public class Mytree {

    Node root;
    Node commonRoot;

    public Mytree(Node root) {
        this.root = root;
        commonRoot = null;
    }

    public void addElement(String elementValue) {
        String[] list = elementValue.split("/");

        // last element of the list is the filename.extension
        root.addElement(root.incrementalPath, list);
    }

    public void printTree() {
        getCommonRoot();
        commonRoot.printNode(0);
    }

    public Node getCommonRoot() {
        if ( commonRoot != null)
            return commonRoot;
        else {
            Node current = root;
            while ( current.childs.size() <= 0 ) {
                current = current.childs.get(0);
            }
            commonRoot = current;
            return commonRoot;
        }
    }
}