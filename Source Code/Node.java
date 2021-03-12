public class Node {
    private char character;
    private int frequency;
    // Set as null since on creation there will be no left nor right node.
    private Node left = null;
    private Node right = null;

    /**
     * This is the constructor to create an empty node.
     */
    public Node(){}

    /**
     * This is the constructor to create a node with a character and a frequency.
     * The node will be used in a binary tree.
     * 
     * @param character This is the character assicaited with the node.
     * @param frequency This is the frequency of this character within a training piece of text.
     */
    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    /**
     * This is the getter method for characters
     * 
     * @return the character of a node in the binary tree.
     */
    public char getCharacters(){
        return character;
    }

    /**
     * This is the getter method for frequency.
     * 
     * @return the frequency of the character within a training piece of data.
     */
    public int getFrequency(){
        return frequency;
    }

    /**
     * This will get the node to the left of this node.
     * 
     * @return the left node.
     */
    public Node getLeft(){
        return left;
    }

    /**
     * This will get the node to the right of this node.
     * 
     * @return the right node.
     */
    public Node getRight(){
        return right;
    }

    /**
     * This will add a number to the frequency of this node.
     * 
     * @param frequency this is the number getting added to the frequency.
     */
    public void addFrequency(int frequency){
        this.frequency += frequency;
    }

    /**
     * This is the setter method for the left node.
     * 
     * @param left a node that will be to the left of this one.
     */
    public void setLeft(Node left){
        this.left = left;
    }

    /**
     * This is the setter method for the right node.
     * 
     * @param right a node that will be right of this one.
     */
    public void setRight(Node right){
        this.right = right;
    }

    /**
     * This will return the string of all nodes.
     * There are two special cases for ' ' and \n
     * since this causes formatting issues.
     */
    public String toString(){
        if (character == '\n'){
            return "Node - [Characters: NEWLINE, Frequency: "+frequency+"]\nLeft of NEWLINE:\n "+left+" \nRight of NEWLINE:\n "+right;
        } else if (character == ' '){
            return "Node - [Characters: SPACE, Frequency: "+frequency+"]\nLeft of SPACE:\n "+left+" \nRight of SPACE:\n "+right;
        }
        return "Node - [Characters: "+character+", Frequency: "+frequency+"]\nLeft of "+character+":\n "+left+" \nRight of "+character+":\n "+right;
    }
}
