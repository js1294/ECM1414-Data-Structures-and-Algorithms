import java.util.Map;

public class BinaryTree {
    
    // This is the root node that the binary tree starts at. This will store the total number of characters.
    private Node root = new Node();
    
    /**
     * This is the constructor for the binary tree.
     * This is used to iterate through the map and them to the tree. 
     * 
     * @param map This is the map that links both characters and their frequencies.
     */
    public BinaryTree(Map<Character, Integer> map){
        for(Character key: map.keySet()) {
            char character = key.charValue();
            int frequency = map.get(key);
            addNode(root,frequency, character);
        }
    }
    
    /**
     * This is the method to compress a file using the binary tree made by the constructor.
     * 
     * @param file This is the file that will be compressed.
     * @param map This is the the map that links both the characters and frequencies.
     * @return This will return a list of binary that it ready to be stored in a file.
     */
    public byte[] compress(String file, Map<Character, Integer> map){
        byte[] result = new byte[file.length()];
        for (int index = 0; index < file.length(); index++) {
            char character = file.charAt(index);
            double frequency = map.get(character);
            result[index] = getLeaf(root, frequency, character, (byte)0);
        }
        return result;
    }

    /**
     * This is the method to decompress a file back into a readable form.
     * 
     * @param file This is the file in binary.
     * @param map This is the map between characters and integers. NOTE: this must be the same as what compressed the file.
     * @return This will return the string of the file.
     */
    public String decompress(byte[] file){
        StringBuilder result = new StringBuilder();
        for (byte b : file) {
            byte[] byteList = createBinaryList(b);
            Node current = root;
            for(int i = 0; i < byteList.length; i++){
                if (byteList[i] == 0 && current.getLeft() != null){
                    current = current.getLeft();
                }else if (byteList[i] == 1 && current.getRight() != null){
                    current = current.getRight();
                }
            }
            try {
                result.append(current.getCharacters());  
            } catch (Exception e) {
                HuffmanCoding.readError(e);
                System.exit(1);
            }
            
        }
        return result.toString();
    }

    /**
     * The getter method for the root node.
     * 
     * @return the root node.
     */
    public Node getRoot(){
        return root; 
    }

    /**
     * The method used to get the leaf from the tree. This will traverse down the tree recursivly.
     * 
     * @param current This is the current node that is being checked.
     * @param frequency This is the frequency of the node we are searching for.
     * @param character This is the character of the node we are searching for.
     * @param binary This is the binary that will be produced as a result of searching.
     * @return
     */
    public byte getLeaf(Node current, double frequency, char character, byte binary){
        if (frequency == current.getFrequency() && character == current.getCharacters()) {
            return binary;
        } 
        if (frequency < current.getFrequency() && current.getLeft() != null) {
            binary += 0b0;
            return getLeaf(current.getLeft(), frequency, character, binary);
        }else if (frequency >= current.getFrequency() && current.getRight() != null){
            binary = 0b1;
            return getLeaf(current.getRight(), frequency, character, binary);
        }else {
            return binary;
        }       
    }

    /**
     * This is will return a string of the root.
     */
    public String toString(){
        return "Root"+root.toString();
    }

    /**
     * This is the method to add new nodes on to the binary tree. This works recursivly. 
     * 
     * @param current This is the current node being checked.
     * @param frequency This is the frequency of the node being added.
     * @param character This is the character of the node being added.
     * @return This is the a node so that it will be able to add a new node when it is null.
     */
    private Node addNode(Node current, int frequency, char character){
        if (current == null) {
            root.addFrequency(frequency);
            return new Node(character, frequency);
        }

        if (frequency >= current.getFrequency() || current.getRight() == null) {
            current.setRight(addNode(current.getRight(), frequency, character));
        } else if (frequency < current.getFrequency() || current.getLeft() == null) {
            current.setLeft(addNode(current.getLeft(), frequency, character));
        }
        return current;
    }

    /**
     * This will create a list of binary values.
     * It will do the inverse of the method getLeaf().
     * 
     * @param b This is a byte of data
     * @return This will return the character that represented this data.
     */
    private byte[] createBinaryList(byte b){
        byte[] byteList = new byte[8];
        int index = 0;
        while(b > 0){
            byteList[index++] = (byte)(b % (byte)2);
            b = (byte)(b / 10);
        }
        return byteList;
    }
}
