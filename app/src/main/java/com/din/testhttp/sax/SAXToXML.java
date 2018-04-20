package com.din.testhttp.sax;

import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by dinzhenyan on 2018/3/30.
 */

public class SAXToXML extends DefaultHandler {

    private String nodeName;
    private StringBuilder name;
    private StringBuilder price;

    private TextView textView;

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void startDocument() throws SAXException {
        name = new StringBuilder();
        price = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //  记录当前节点名
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //  根据当前的节点名判断将内容添加到哪一个StringBuilder对象中
        if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("price".equals(nodeName)) {
            price.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("food".equals(localName)) {
            textView.append("\n name is  :  " + name.toString().trim());
            textView.append("\n price is  :  " + price.toString().trim());
            textView.append("\n");
            //  将StringBuilder清空
            name.setLength(0);
            price.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
