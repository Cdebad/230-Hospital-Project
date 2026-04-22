public class BinarySearchTree {
    private TreeNode root;

    public void add( IdentifiedObject obj ) {
        root = addNode( root, new TreeNode( obj ) );
    }


    public IdentifiedObject find( Identity id ) {
        TreeNode node = findNode( root, id );
        if (node != null)
            return node.data;
        else
            return null;
    }


    private class TreeNode {
        IdentifiedObject data;
        TreeNode left, right;

        public TreeNode(IdentifiedObject object){
            data = object;
            left = null;
            right = null;
        }
    }
    private TreeNode addNode( TreeNode root, TreeNode newNode ) {
        if (root == null) {
            return newNode;
        }
        Identity newID = newNode.data.getIdentity();
        Identity rootID = root.data.getIdentity();

        if (newID.isLessThan(rootID)){
            root.left = addNode(root.left,newNode);
        } else if (rootID.isLessThan(newID)) {
            root.right = addNode(root.right,newNode);
        }


        return root;
    }



    private TreeNode findNode( TreeNode root, Identity id ) {
        if (root == null){
            return null;
        }

        Identity rootID = root.data.getIdentity();

        if (id.match(rootID)){
            return root;
        }

        if (id.isLessThan(rootID)){
            return findNode(root.left,id);
        } else {
            return findNode(root.right,id);
        }
    }


    public BinarySearchTree(){
        root = null;
    }
}