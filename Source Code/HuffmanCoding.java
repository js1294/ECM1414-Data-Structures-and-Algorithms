import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class HuffmanCoding {

    // A map that links a set of characters with a frequency.
    private Map<Character, Integer> mapCharacterFrequency;

    /**
     * This is the constructor for the class.
     * This is used to read the file and then create the map from this.
     * 
     * @param fileName this is the name of the file to read from.
     */
    public HuffmanCoding(String fileName){
        char[] file = readFile(fileName);
        mapCharacterFrequency = createMap(file);
    }

    /**
     * This is the getter method for the map.
     * 
     * @return the map between character and integer. 
     */
    public Map<Character, Integer> getMap(){
        return mapCharacterFrequency;
    }

    /**
     * This is the method to read a file.
     * 
     * @param fileName this is the name of the file to read
     * @return it will return a list of characters from the file.
     */
    public static char[] readFile(String fileName){
        char[] chars = new char[0];
        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(new File(fileName)))){
            Long fileSize = new File(fileName).length();
            chars = new char[fileSize.intValue()];
            int currentInt = 0; 
            for (int index=0;index<fileSize;index++) {
                currentInt = reader.read();
                chars[index] = (char)currentInt;
            }
        } catch (IOException e) {
            readError(e);
        }
        return chars;
    }

    /**
     * This is the method to write a file.
     * This will overwrite/create a file before writing to it.
     * 
     * @param fileName this is the name of the file to written to.
     * @param file this is the data in binary that will be written to the file.
     */
    public static void writeFile(String fileName, byte[]file){
        createFile(fileName);
        try (BufferedWriter  writer = new BufferedWriter(new FileWriter(fileName))){
            for (byte b : file) {
                writer.write(b);
            }
        } catch (IOException e) {
            readError(e);
        }
    }

    /**
     * This is the method to write a file.
     * This will overwrite/create a file before writing to it.
     * 
     * @param fileName this is the name of the file to written to.
     * @param file this is the data in binary that will be written to the file.
     */
    public static void writeFile(String fileName, String file){
        createFile(fileName);
        try (BufferedWriter  writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write(file);
        } catch (IOException e) {
            readError(e);
        }
    }

    /**
     * This is the tostring method that will return the map as a string.
     */
    public String toString(){
        return mapCharacterFrequency.toString();
    }

    /**
     * This is the method to format an exception for the console.
     * 
     * @param e This is an exception to be formatted.
     */
    public static void readError(Exception e){
        System.out.printf("%s has occurred - %s%n",e.getClass().getCanonicalName(),e.getLocalizedMessage());
    }

    /**
     * This is the method to create a new file.
     * 
     * @param fileName This is the name of the new file.
     */
    private static void createFile(String fileName){
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("Created new file: "+file.toString());
            } else{
                System.out.println("Cleared the file: "+file.toString());
            }
        } catch (IOException e) {
            readError(e);
        }
    }

    /**
     * This is the method to create a map of the character and integer.
     * 
     * @param file this is the list of characters
     * @return the map containing the list of characters in the file and the frequency of each.
     */
    private Map<Character, Integer> createMap(char[] file){
        Map<Character, Integer> map = new HashMap<>();
        for (int index=0;index<file.length;index++){
            Character letter = file[index];
            // This will map the letter and then if the frequency is
            // null will set it to one but if not increment frequency.
            map.compute(letter, (k, freq) -> (freq == null) ? 1 : freq+1);
        }
        map = sortMap(map);
        return map;
    }

    /**
     * This is method to sort the map in order of frequencies, going from largest to smallest.
     * 
     * @param <V> This allows for the frequencies in the map to be comparable. This means that they can be put into a list and sorted by values. 
     * @param map This is the map between characters and their frequencies. 
     * @return This will return a sorted map in the order of frequencies from largest to smallest. 
     */
    private <V extends Comparable<? super V>> Map<Character, Integer> sortMap(Map<Character, Integer> map) {
        // Putting each entry from the map into a list so it can be sorted by the values, although this is smallest to largest..
        List<Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        

        // This reveses the list of entries so that it is goes from largest to smallest.
        List<Entry<Character, Integer>> reverse = new ArrayList<>();
        for (Entry<Character,Integer> entry : list) {
            reverse.add(0, entry);
        }

        // Putting each entry back into the map data structure.
        Map<Character, Integer> result = new LinkedHashMap<>();
        for (Entry<Character, Integer> entry : reverse) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}