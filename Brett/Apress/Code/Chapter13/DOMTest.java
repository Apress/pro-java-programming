import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DOMTest {

  public static void main(String[] args) throws Exception {
    DOMTest dt = new DOMTest(args[0]);
  }

  public DOMTest(String uri) throws Exception {
    DocumentBuilderFactory factory =
        DocumentBuilderFactory.newInstance();
    factory.setValidating(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(uri);
    displayTree(doc.getDocumentElement());
  }

  protected void displayTree(Node node) {
    short nodeType = node.getNodeType();
    switch (nodeType) {
      case Node.ELEMENT_NODE:
        printElement((Element)node);
        break;
      case Node.TEXT_NODE:
        printText((Text)node);
        break;
      case Node.COMMENT_NODE:
        printComment((Comment)node);
        break;
      case Node.CDATA_SECTION_NODE:
        printCDATA((CDATASection)node);
        break;
      case Node.ENTITY_REFERENCE_NODE:
        printEntityReference((EntityReference)node);
        break;
      case Node.PROCESSING_INSTRUCTION_NODE:
        printProcessingInstruction(
            (ProcessingInstruction)node);
        break;
      default:
    }
  }

  protected void printElement(Element node) {
    Node child;
    Attr attr;
    System.out.print("<" + node.getNodeName());
    NamedNodeMap attrs = node.getAttributes();
    int count = attrs.getLength();
    for (int i = 0; i < count; i++) {
      attr = (Attr)(attrs.item(i));
      System.out.print(" " + attr.getName() + "=\"" + attr.getValue() +
                       "\"");
    }
    System.out.print(">");
    NodeList children = node.getChildNodes();
    count = children.getLength();
    for (int i = 0; i < count; i++) {
      child = children.item(i);
      displayTree(child);
    }
    System.out.print("</" + node.getNodeName() + ">");
  }

  protected void printText(CharacterData node) {
    System.out.print(node.getData());
  }

  protected void printComment(Comment node) {
    System.out.print("<!--" + node.getData() + "-->");
  }

  protected void printCDATA(CDATASection node) {
    System.out.print("<![CDATA[" + node.getData() + "]]>");
  }

  protected void printEntityReference(EntityReference node) {
    System.out.print("&" + node.getNodeName() + ";");
  }

  protected void printProcessingInstruction(ProcessingInstruction node) {
    System.out.print("<?" + node.getTarget() + " " + node.getData() + "?>");
  }

}