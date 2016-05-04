package com.upchina.rongCloud.test;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.base.Charsets;
import com.upchina.api.param.KvParam;
import com.upchina.api.protocol.HttpPostProtocol;

public class TestStock {

	public static void main(String[] args) throws Exception {
		String url = "http://192.168.6.199:101/Service_mn.asmx/GetBargainsById";
		 String result = HttpPostProtocol.builder()
	                .url(url)
	                .charset(Charsets.UTF_8)
	                .send(KvParam.builder()
	                        .set("userid", "zoujiamin")
	                        .set("gametype", "1"));
		 System.out.println(result);
		 StringReader read = new StringReader(result); 
		 InputSource source = new InputSource(read);  
		 SAXParserFactory factory = SAXParserFactory.newInstance();
		 SAXParser parser = factory.newSAXParser();
		 XMLReader reader = parser.getXMLReader(); 
		 MyHandler handler = new MyHandler();  
         reader.setContentHandler(handler);
         reader.parse(source);
         ArrayList list = handler.getBooks(); 
         String code = "";
         for(int i = 0; i < list.size(); i++) {  
        	 System.out.println(list.get(i));
        	 code += list.get(i);
         }
         
//		 parser.parse(source,new MyHandler());
	}
	
	
}
class MyHandler extends DefaultHandler {  
    //将读取的内容存放到一个book对象中，存放到list集合中  
      
    ArrayList<String> list = new ArrayList<String>();  
    private String book;  
      
    @Override  
    public void startElement(String uri, String localName, String qName,  
            Attributes attributes) throws SAXException {  
        if ("string".equals(qName)) {
        	book = "";
        }
    }  
  
    @Override  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
    	String name = new String(ch,start,length);
    	book += name;
    }  
  
    @Override  
    public void endElement(String uri, String localName, String qName)  
            throws SAXException {  
//        if("书".equals(qName)) {  
//            list.add(book);  
//            book = null;  
//        }
		if ("string".equals(qName)) {
			list.add(book);
			book = null;
		}
//        currentTag = null;  
    }  
  
      
    public ArrayList<String> getBooks() {  
        return list;  
    }  
  
  
}  