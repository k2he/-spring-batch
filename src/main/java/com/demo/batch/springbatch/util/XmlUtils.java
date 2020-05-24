package com.demo.batch.springbatch.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kaihe
 *
 */

@Slf4j
public class XmlUtils {

  public static boolean validateXMLSchema(String xsdPath, String xmlFilePath) {

    try {
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = factory.newSchema(new File(xsdPath));
      Validator validator = schema.newValidator();
      
      // Case #1: Read file from scr/main/resource
      Resource resource = new ClassPathResource(xmlFilePath);
      InputStream inputStream = resource.getInputStream();
      validator.validate(new StreamSource(inputStream));
      
    //Case# 2: Read file from external location
//      validator.validate(new StreamSource(new File(xmlFilePath)));
    } catch (IOException | SAXException e) {
      log.error("Failed to validate schema : " + e.getMessage(), e);
      return false;
    }
    return true;
  }

  public static JsonNode convertToJson(String xmlFilePath) throws IOException {
    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.registerModule(
        new SimpleModule().addDeserializer(JsonNode.class, new CustomJsonNodeDeserializer()));
    
    //Case# 1: Read file in scr/main/resource
    Resource resource = new ClassPathResource(xmlFilePath);
    InputStream inputStream = resource.getInputStream();
    return xmlMapper.readTree(inputStream);
    
    //Case# 2: Read file from external location
//    File xmlFile = new File(xmlFilePath);
//    return xmlMapper.readTree(xmlFile);
  }
}
