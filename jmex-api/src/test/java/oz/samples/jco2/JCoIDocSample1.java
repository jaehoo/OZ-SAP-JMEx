package oz.samples.jco2;///**
// * JCoIDocSample1.java
// * Property of SAP AG, Walldorf
// * (c) Copyright SAP AG, Walldorf, 2002.
// * All rights reserved.
// */
//
//import com.sap.mw.idoc.*;
//import com.sap.mw.idoc.jco.*;
//import com.sap.mw.jco.*;
//
///**
// * This sample program demonstrates how to create a simple Material Master IDoc
// * (MATMAS02) from scratch, fill it with sample data and send it to an SAP
// * system.
// *
// * @version 1.0
// * @author  SAP AG, Walldorf
// */
//public class JCoIDocSample1
//{
//    public static void main(String[] args)
//    {
//        try {
//            //create a JCo client pool
//            JCO.addClientPool( "MyPool",    //pool name
//                               3,           //maximum pool connections
//                               "000",       //SAP client
//                               "userid",    //user ID
//                               "password",  //password
//                               "EN",        //language
//                               "appserver", //app server host name
//                               "sysnr" );   //system number
//
//            //create an IDoc repository
//            IDoc.Repository idocRep = JCoIDoc.createRepository("MyIDocRepository", "MyPool");
//
//            //create a new and empty MATMAS02 document
//            System.out.print("Creating IDoc...");
//            IDoc.Document doc = JCoIDoc.createDocument(idocRep, "MATMAS02");
//
//            //get the root segment from the document
//            //The root segment does not contain any fields or data. It is only
//            //used as the standard parent segment and won't be transmitted when
//            //the document is sent to an SAP system.
//            IDoc.Segment segment = doc.getRootSegment();
//
//            //create and add a new and empty child segment of type E1MARAM
//            //and fill the segment data
//            segment = segment.addChild("E1MARAM");
//            segment.setField("MSGFN", "005");
//            segment.setField("MATNR", "BOXCOOKIES");
//            segment.setField("ERSDA", "20020801");
//            segment.setField("ERNAM", "TIGGER");
//            segment.setField("PSTAT", "KBG");
//            segment.setField("MTART", "FERT");
//            segment.setField("MBRSH", "L");
//            segment.setField("MATKL", "G1113");
//            segment.setField("MEINS", "PCE");
//            segment.setField("BLANZ", "000");
//            segment.setField("BRGEW", "0.550");
//            segment.setField("NTGEW", "0.000");
//            segment.setField("GEWEI", "KGM");
//            segment.setField("VPSTA", "KBG");
//
//            //create and add a new and empty child segment of type E1MAKTM
//            //and fill the segment data
//            segment = segment.addChild("E1MAKTM");
//            segment.setField("MSGFN", "005");
//            segment.setField("SPRAS", "D");
//            segment.setField("MAKTX", "Schachtel mit Keksen");
//            segment.setField("SPRAS_ISO", "DE");
//
//            //create and add a new and empty sibling segment of type E1MAKTM (same type)
//            //and fill the segment data
//            segment = segment.addSibling();
//            segment.setField("MSGFN", "005");
//            segment.setField("SPRAS", "E");
//            segment.setField("MAKTX", "Box of cookies");
//            segment.setField("SPRAS_ISO", "EN");
//
//            //create and add a new and empty sibling segment of type E1MARCM
//            //and fill the segment data
//            segment = segment.addSibling("E1MARCM");
//            segment.setField("MSGFN", "005");
//            segment.setField("WERKS", "0001");
//            segment.setField("PSTAT", "BG");
//            segment.setField("PLIFZ", "0");
//            segment.setField("WEBAZ", "0");
//            segment.setField("PERKZ", "M");
//            segment.setField("AUSSS", "0.00");
//            segment.setField("BESKZ", "E");
//            segment.setField("AUTRU", "X");
//
//            //create and add a new and empty sibling segment of type E1MBEWM
//            //and fill the segment data
//            segment = segment.addSibling("E1MBEWM");
//            segment.setField("MSGFN", "005");
//            segment.setField("BWKEY", "0001");
//            segment.setField("VPRSV", "S");
//            segment.setField("VERPR", "0.00");
//            segment.setField("STPRS", "15.50");
//            segment.setField("PEINH", "1");
//            segment.setField("BKLAS", "7920");
//            segment.setField("VJVPR", "S");
//            segment.setField("VJVER", "0.00");
//            segment.setField("VJSTP", "15.50");
//            segment.setField("LFGJA", "2002");
//            segment.setField("LFMON", "08");
//            segment.setField("PSTAT", "BG");
//            segment.setField("KALN1", "000100126602");
//            segment.setField("KALNR", "000100126603");
//            segment.setField("EKALR", "X");
//            segment.setField("VPLPR", "0.00");
//            segment.setField("VJBKL", "7920");
//            segment.setField("VJPEI", "1");
//            segment.setField("BWPEI", "0");
//
//            //prepare document for sending and set the appropriate control data
//            doc.setMessageType("MATMAS");
//            doc.setRecipientPartnerType("LS");
//            doc.setRecipientPartnerNumber("TSTCLNT000");
//            doc.setSenderPort("SAPJCOIDOC");
//            doc.setSenderPartnerType("LS");
//            doc.setSenderPartnerNumber("JCOCLNT000");
//
//            System.out.println(" done.");
//
//            //check the whole document's syntax
//            try {
//                System.out.print("Checking IDoc syntax...");
//                doc.checkSyntax();
//                System.out.println(" done.");
//            }
//            catch ( IDoc.Exception ex ) {
//                System.out.println(" Syntax error: " + ex);
//                System.exit(0);
//            }
//
//            //get a JCo client from previously created client pool
//            JCO.Client client = JCO.getClient("MyPool");
//
//            //create a new transaction ID
//            String tid = client.createTID();
//
//            //send the document to the SAP system asynchronously
//            System.out.print("Sending IDoc...");
//            client.send(doc, tid);
//
//            //confirm the transaction ID
//            client.confirmTID(tid);
//            System.out.println(" done.");
//
//            //release the JCo client and return it back to the pool
//            JCO.releaseClient(client);
//        }
//        catch ( Exception ex ) {
//            System.out.println("Application error: " + ex);
//        }
//    }//method main
//}//class JCoIDocSample1