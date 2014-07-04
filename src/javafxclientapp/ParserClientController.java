/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxclientapp;

import java.net.URI;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.w3c.dom.Document;
import java.net.URL;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author MichaelOm
 */
public class ParserClientController implements Initializable {
    String rootURL="";
    String processedURL="";
    WebView webview;
    WebEngine webengine;
    
    @FXML
    private TextField rootURLName;
    @FXML
    private TextField urlName;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSetRoot;
    @FXML
    private Button btnStartParsing;
    
    @FXML
    private WebView siteView;
    @FXML
    private TableView <NodeElement> nodesView;
    
    @FXML        
    private void handleURLSearch()   {
        try {
            String toGo=urlName.getText();
            URI uriToGo=new URI(urlName.getText());
            String scheme=uriToGo.getScheme();
            String ssp=uriToGo.getSchemeSpecificPart();
            if(scheme==null)  scheme="http";
            
            URI uri=new URI(scheme, ssp, null);
        
            siteView.getEngine().load(uri.toString());
        } catch(Exception ex) {
            
        }
        
        
    }
    
    @FXML
    private void handleURLSetRoot() {
        rootURL=processedURL.toString();
        rootURLName.setText(rootURL);
    }
    
    @FXML
    private void handleStartParsing()   {
        WebTarget clientTarget;
        
        ObservableList<NodeElement> nodes=nodesView.getItems();
        nodes.clear();
        Client client=ClientBuilder.newClient();
        client.register(ParserClientMsgReader.class);
        clientTarget=client.target("http://localhost:8080/ServerParserWebApp/parser");
        GenericType<List<NodeElement>> listNodes=new GenericType<List<NodeElement>>(){};
        List<NodeElement> nodesElts=clientTarget.request("text/plain").get(listNodes);
        for(NodeElement nodeName:nodesElts)  {
            nodes.add(nodeName);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        webengine = siteView.getEngine();
        webengine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>(){
                    public void changed(ObservableValue ov, State oldStat, State newState )   {
                        if(newState==Worker.State.SUCCEEDED)   {
                            Document doc=webengine.getDocument();
                            processedURL=doc.getDocumentURI();
                        }
                    }
                } 
        );

        
        
    }
    
}
