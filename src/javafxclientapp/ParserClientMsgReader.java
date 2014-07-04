/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxclientapp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author MichaelOm
 */
@Provider
@Consumes("{application/json}")
public class ParserClientMsgReader implements MessageBodyReader<List<NodeElement>>{

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return true;
    }

    @Override
    public List<NodeElement> readFrom(Class<List<NodeElement>> type, Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        if(mt.getType().equals("application") && mt.getSubtype().equals("json"))    {
            NodeElement nodeElement=new NodeElement();
            List<NodeElement> nodesElement=new ArrayList<>();
            JsonParser parser=Json.createParser(in);
            while(parser.hasNext()) {
                JsonParser.Event event=parser.next();
                switch (event)  {
                    case START_OBJECT:
                        nodeElement=new NodeElement();
                        break;
                    case END_OBJECT:
                        nodesElement.add(nodeElement);
                        break;
                    case KEY_NAME:
                        String key=parser.getString();
                        parser.next();
                        switch (key)    {
                            case "name" :
                                nodeElement.setNodeName(parser.getString());
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                
            }
            
            
            return nodesElement;
        }
        throw new UnsupportedOperationException("Not supported media type: "+mt);
    }
    
    
}
