import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class Main {

    public static void main(String[] args) {
        File file = new File("src\\main\\java\\data.xml");
        File result = new File("src\\main\\java\\data2.json");
        listToJSON(result, parseXML(file));
    }

    public static List<Employee> parseXML(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            List<Employee> list = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                try {
                    String[] dataEmployee = node.getTextContent().split("\n");
                    for (int j = 0; j < dataEmployee.length; j++) {
                        dataEmployee[j] = dataEmployee[j].replaceAll(" ", "");
                    }
                    list.add(new Employee(Long.parseLong(dataEmployee[1]), dataEmployee[2], dataEmployee[3], dataEmployee[4], Integer.parseInt(dataEmployee[5])));
                } catch (Exception exception) {}
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void listToJSON (File file, List<Employee> list) {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(gson.toJson(list, listType));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}