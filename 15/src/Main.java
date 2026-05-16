//1. Создать файл XML и соответствующую ему схему XSD.
//2. При разработке XSD использовать простые и комплексные типы, перечисления, шаблоны и предельные значения.
//3. Сгенерировать класс, соответствующий данному описанию.
//4. Создать приложение для разбора XML-документа и инициализации коллекции объектов информацией из XML-файла.
// Для разбора использовать SAX, DOM и StAX-парсеры. Для сортировки объектов использовать интерфейс Comparator.
//5. Произвести проверку XML-документа с привлечением XSD.
//6. Определить метод, производящий преобразование разработанного XML-документа в документ, указанный в каждом задании.

//1 variant
//1. Оранжерея.
//Растения, содержащиеся в оранжерее, имеют следующие характеристики:
//— Name — название растения;
//— Soil — почва для посадки, которая может быть следующих типов: подзолистая, грунтовая, дерново-подзолистая;
//— Origin — место происхождения растения;
//— Visual рarameters (должно быть несколько) — внешние параметры: цвет стебля, цвет листьев, средний размер растения;
//— Growing tips (должно быть несколько) — предпочтительные условия произрастания: температура (в градусах), освещение (светолюбиво либо нет), полив (мл в неделю);
//— Multiplying — размножение: листьями, черенками либо семенами.
//Корневой элемент назвать Flower.
//С помощью XSL преобразовать XML-файл в формат HTML, где отобразить растения по предпочитаемой температуре (по возрастанию).

import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import javax.xml.stream.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //1. валидация xml по xsd
        boolean valid = validateXML("src/flowers.xml", "src/flower.xsd");
        System.out.println("XML валиден: " + valid);
        if (!valid) return;

        //2. парсинг dom
        System.out.println("\nDOM - парсер:");
        List<Flower> domFlowers = parseDOM("src/flowers.xml");
        printFlowers(domFlowers);

        //3. парсинг sax
        System.out.println("\nSAX - парсер:");
        List<Flower> saxFlowers = parseSAX("src/flowers.xml");
        printFlowers(saxFlowers);

        //4. парсинг stax
        System.out.println("\nStAX - парсер:");
        List<Flower> staxFlowers = parseStAX("src/flowers.xml");
        printFlowers(staxFlowers);

        //5. сортировка по температуре (возрастание)
        domFlowers.sort(Comparator.comparingInt(f -> f.getGrowing().getTemperature()));
        System.out.println("\nОтсортировано по температуре (возрастание):");
        printFlowers(domFlowers);

        //6. xslt преобразование в html
        transformToHTML("src/flowers.xml", "src/transform.xsl", "src/output.html");
        System.out.println("\nHTML-отчёт создан: output.html");
    }

    //валидация xsd
    private static boolean validateXML(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка валидации: " + e.getMessage());
            return false;
        }
    }

    private static List<Flower> parseDOM(String xmlPath) throws Exception {
        List<Flower> flowers = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(xmlPath));
        doc.getDocumentElement().normalize();

        NodeList plants = doc.getElementsByTagName("Plant");
        for (int i = 0; i < plants.getLength(); i++) {
            Element plant = (Element) plants.item(i);
            Flower f = new Flower();
            f.setId(plant.getAttribute("id"));
            f.setName(getTagValue("Name", plant));
            f.setSoil(getTagValue("Soil", plant));
            f.setOrigin(getTagValue("Origin", plant));

            Element visualElem = (Element) plant.getElementsByTagName("VisualParameters").item(0);
            Flower.VisualParameters vp = new Flower.VisualParameters();
            vp.setStemColor(getTagValue("StemColor", visualElem));
            vp.setLeafColor(getTagValue("LeafColor", visualElem));
            vp.setAverageSize(Integer.parseInt(getTagValue("AverageSize", visualElem)));
            f.setVisual(vp);

            Element growElem = (Element) plant.getElementsByTagName("GrowingTips").item(0);
            Flower.GrowingTips gt = new Flower.GrowingTips();
            gt.setTemperature(Integer.parseInt(getTagValue("Temperature", growElem)));
            gt.setLight(Boolean.parseBoolean(getTagValue("Light", growElem)));
            gt.setWatering(Integer.parseInt(getTagValue("Watering", growElem)));
            f.setGrowing(gt);

            f.setMultiplying(getTagValue("Multiplying", plant));
            flowers.add(f);
        }
        return flowers;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList list = element.getElementsByTagName(tag);
        return (list.getLength() == 0) ? "" : list.item(0).getTextContent();
    }

    private static List<Flower> parseSAX(String xmlPath) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        FlowerSAXHandler handler = new FlowerSAXHandler();
        saxParser.parse(new File(xmlPath), handler);
        return handler.getFlowers();
    }

    //обработчик sax (внутренний класс)
    static class FlowerSAXHandler extends DefaultHandler {
        private List<Flower> flowerList = new ArrayList<>();
        private Flower currentFlower;
        private Flower.VisualParameters currentVisual;
        private Flower.GrowingTips currentGrowing;
        private StringBuilder data = new StringBuilder();

        public List<Flower> getFlowers() { return flowerList; }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            data.setLength(0);
            switch (qName) {
                case "Plant":
                    currentFlower = new Flower();
                    currentFlower.setId(attributes.getValue("id"));
                    break;
                case "VisualParameters": currentVisual = new Flower.VisualParameters(); break;
                case "GrowingTips": currentGrowing = new Flower.GrowingTips(); break;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String content = data.toString().trim();
            switch (qName) {
                case "Name": currentFlower.setName(content); break;
                case "Soil": currentFlower.setSoil(content); break;
                case "Origin": currentFlower.setOrigin(content); break;
                case "StemColor": currentVisual.setStemColor(content); break;
                case "LeafColor": currentVisual.setLeafColor(content); break;
                case "AverageSize": currentVisual.setAverageSize(Integer.parseInt(content)); break;
                case "Temperature": currentGrowing.setTemperature(Integer.parseInt(content)); break;
                case "Light": currentGrowing.setLight(Boolean.parseBoolean(content)); break;
                case "Watering": currentGrowing.setWatering(Integer.parseInt(content)); break;
                case "Multiplying": currentFlower.setMultiplying(content); break;
                case "VisualParameters": currentFlower.setVisual(currentVisual); break;
                case "GrowingTips": currentFlower.setGrowing(currentGrowing); break;
                case "Plant": flowerList.add(currentFlower); break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            data.append(ch, start, length);
        }
    }

    private static List<Flower> parseStAX(String xmlPath) throws Exception {
        List<Flower> flowers = new ArrayList<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlPath));

        Flower f = null;
        Flower.VisualParameters vp = null;
        Flower.GrowingTips gt = null;
        String content = "";
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    String tag = reader.getLocalName();
                    switch (tag) {
                        case "Plant":
                            f = new Flower();
                            f.setId(reader.getAttributeValue(null, "id"));
                            break;
                        case "VisualParameters": vp = new Flower.VisualParameters(); break;
                        case "GrowingTips": gt = new Flower.GrowingTips(); break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    content = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    String endTag = reader.getLocalName();
                    switch (endTag) {
                        case "Name": f.setName(content); break;
                        case "Soil": f.setSoil(content); break;
                        case "Origin": f.setOrigin(content); break;
                        case "StemColor": vp.setStemColor(content); break;
                        case "LeafColor": vp.setLeafColor(content); break;
                        case "AverageSize": vp.setAverageSize(Integer.parseInt(content)); break;
                        case "Temperature": gt.setTemperature(Integer.parseInt(content)); break;
                        case "Light": gt.setLight(Boolean.parseBoolean(content)); break;
                        case "Watering": gt.setWatering(Integer.parseInt(content)); break;
                        case "Multiplying": f.setMultiplying(content); break;
                        case "VisualParameters": f.setVisual(vp); break;
                        case "GrowingTips": f.setGrowing(gt); break;
                        case "Plant": flowers.add(f); break;
                    }
                    break;
            }
        }
        reader.close();
        return flowers;
    }

    //xslt преобразование в html
    private static void transformToHTML(String xml, String xsl, String html) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(new File(xsl)));
        transformer.transform(new StreamSource(new File(xml)), new StreamResult(new File(html)));
    }

    private static void printFlowers(List<Flower> list) {
        for (Flower f : list) System.out.println(f);
    }
}