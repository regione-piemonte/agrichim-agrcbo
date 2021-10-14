package it.csi.agrc;

import java.io.Serializable;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.Utili;
import it.csi.sigmater.ws.client.sigwgssrvSigwgssrv.InfoParticella;

public class CentroideParticelle extends BeanDataSource implements Serializable {
	
	private static final long serialVersionUID = -2788561524951301445L;

	  private String codiceVerificaElab;

	  public String verificaCodice()
	  {
		  StringBuffer errore=new StringBuffer("");
		  String ris = "";
		  try {
		  ris = codVerificaByCodApp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  if(!ris.equals(codiceVerificaElab))
		  {
			  errore.append(";1");
		  }
			  
		  
		  if (errore.toString().equals("")) return null;
		    else return errore.toString();
	  }
	  
	  
	  public String verificaElabInCorsoUpdate()
	  {
		  StringBuffer errore=new StringBuffer("");
		  String ris = "";
		  int numeroRecordUpdate = 0;
		  try {
		  	  
		  numeroRecordUpdate = lockElab();
		 System.out.println("numeroRecordUpdate " + numeroRecordUpdate);
		 
		  
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		  if(numeroRecordUpdate==0)
		  {
			  errore.append(";1");
		  }
		  
		  if (errore.toString().equals("")) return null;
		    else return errore.toString();
	  }
	  
	  
	  public void avviaElab(BeanParametri beanParametriApplication, String cuaa)
	  {
		 
		  //mando l'email di inizio eleborazione
		  inviaMail(beanParametriApplication, "start");
		  //devo cercare i record da elaborare
		  List<WrkCacloloUtm> elenco = new ArrayList<WrkCacloloUtm>();
		  try {
			 elenco = lista();
			 System.out.println("elenco " + elenco.size());
			 //per ogni elemento devo richiamare il servizio di sigmater
			 for (WrkCacloloUtm wcu : elenco) 
			 {
			   List<InfoParticella> infoParticella= null;
			   SigmaterWs sigmaterWs = new SigmaterWs();
			   infoParticella= sigmaterWs.bindingSigmaterSigwgssrvWs(beanParametriApplication)
			       .cercaParticelleByFilter(wcu.getCodiceIstat(), null, wcu.getSezione(), null, wcu.getParticella(), wcu.getFoglio(), cuaa);
				 //infoParticella= beanParametriApplication.getSigwgssrvSrv().cercaParticelleByFilter(wcu.getCodiceIstat(), null, wcu.getSezione(), null, wcu.getParticella(), wcu.getFoglio());
			 
				 //setto le proprietà con i valori che ritornano
				 if (infoParticella!=null && infoParticella.size()>0 && infoParticella.get(0)!=null)
				 {				   
				    //lettura delle coordinate
				    boolean trovato = false;
				    String coordinataNordUtm = "";
				    String coordinataEstUtm = "";
			      try
			      {
			        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder builder = factory.newDocumentBuilder();
			        InputSource is = new InputSource(new StringReader(infoParticella.get(0).getGeometriaGML()));
			        Document doc = builder.parse(is);
			        NodeList nl = doc.getElementsByTagName("gml:coordinates");
			        if(nl.getLength() > 0)
			        {
			          String value = nl.item(0).getChildNodes().item(0).getNodeValue();
			          if(value != null && value !="")
			          {
			            StringTokenizer strz = new StringTokenizer(value);
			            double x = 0;
			            double y = 0;
			            int numElementi = 0;
			            while (strz.hasMoreElements()) 
			            {
			              StringTokenizer strz2 = new StringTokenizer((String)strz.nextElement(),",");
			              x += new Double((String)strz2.nextElement()).doubleValue();
			              y += new Double((String)strz2.nextElement()).doubleValue();
			              
			              numElementi++;
			            }
			            
			            x = x / numElementi;
			            y = y / numElementi;
			            
			            trovato = true;
			            coordinataNordUtm=Math.round(y)+""; 
			            coordinataEstUtm=Math.round(x)+""; 
			          }
			        }
			      }
			      catch(Exception ex)
			      {
			        CuneoLogger.error(this,"Errore nella lettura dell'xml gml: "+ex.getMessage());
			      }
			      
			      
			      if (trovato)
            {
              //valorizzo le coordinate UTM
              wcu.setCoordinataNord(coordinataNordUtm); 
              wcu.setCoordinataEst(coordinataEstUtm); 
              wcu.setEsitoElaborazione("0");
            }
            else
            {
              wcu.setEsitoElaborazione("1");
              wcu.setCodErr("01");
              wcu.setMsgErr("Coordinate centroide non calcolate");
            }

				   
				   
				   
				   
						/*Polygon polig=
								CastCSIObjectToJSFObject.castCSIPolygonToPolygon(infoParticella.get(0).getGeometria());
						Point pw = polig.getCentroid();
						if (pw!=null)
						{
							//valorizzo le coordinate UTM
							wcu.setCoordinataNord(Math.round(pw.getY())+""); 
							wcu.setCoordinataEst(Math.round(pw.getX())+""); 
							wcu.setEsitoElaborazione("0");
						}
						else
						{
							wcu.setEsitoElaborazione("1");
							wcu.setCodErr("01");
							wcu.setMsgErr("Coordinate centroide non calcolate da metodo CastCSIObjectToJSFObject.castCSIPolygonToPolygon(infoParticella[0].getGeometria());");
						}*/
					}
				 else
				 {
					 wcu.setEsitoElaborazione("1"); //se non viene restituita la infoParticella
					 wcu.setCodErr("01");
					 wcu.setMsgErr("Particella non trovata su Sigmater (servizio SIGWGSSRV.cercaParticelleByFilter)");
				 }
				 updateCalcolo(wcu);
				 
			 }
			 //se tutto va bene devo mandare l'email all'utente
		
			 inviaMail(beanParametriApplication, "end");

			 //se tutto è andato bene devo 'rilasciare' il record
			 unLockElab();
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	  
	  
	 
	  
	  private int unLockElab() throws Exception, SQLException 
	  {
		  if (!isConnection())
		       throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn = null;
		StringBuffer query = null;
		Statement stmt = null;
		
		int update = 0;
		try
		{
		  conn=getConnection();
	      stmt = conn.createStatement();
		  query = new StringBuffer(" UPDATE WRK_APPLICAZIONE_BATCH SET ");
		  query.append(" ELABORAZIONE_IN_CORSO = 'N' ");
		  query.append(" WHERE codice_applicazione = 'CALCOLO_UTM' AND ELABORAZIONE_IN_CORSO = 'S' ");
		  //CuneoLogger.debug(this, query.toString());
		  update = stmt.executeUpdate(query.toString());
		}
		catch(java.sql.SQLException ex)
		{
		  this.getAut().setQuery("update della classe CentroideParticelle");
		  this.getAut().setContenutoQuery(query.toString());
		  ex.printStackTrace();
		  throw (ex);
		}
		catch(Exception e)
		{
		  this.getAut().setQuery("update della classe CentroideParticelle"
		                        +": non è una SQLException ma una Exception"
		                        +" generica");
		  e.printStackTrace();
		  throw (e);
		}
		finally
		{
		  if (conn!=null) conn.close();
		}
		
		return update;
	}


	private void inviaMail(BeanParametri beanParametriApplication, String fase)  {
		  
		     it.csi.cuneo.Mail eMail = new it.csi.cuneo.Mail(); 
			 eMail.setEMailSender(beanParametriApplication.getMailMittenteAgrichim());

	  		//Mittente
	  		eMail.setHost(beanParametriApplication.getHostServerPosta());

	  		//Destinatari
	  	    eMail.setEMailReceiver(beanParametriApplication.getMailDestinatario());
	  	     
	  	    if(fase.equals("end"))
	  	    {
	  	    	//Oggetto: 
	  	    	eMail.setSubject("["+beanParametriApplication.getNomeServerWebApplication()+"] Elaborazione CALCOLO_UTM terminata ");

	  		
	  	    	eMail.setContenutoEmail("L'elaborazione della procedura CALCOLO_UTM è terminata");
	  	    }
	  	    else
	  	    	if(fase.equals("start"))
	  	    	{
	  	    	    //Oggetto: 
		  	    	eMail.setSubject("["+beanParametriApplication.getNomeServerWebApplication()+"] Elaborazione CALCOLO_UTM iniziata ");

		  		
		  	    	eMail.setContenutoEmail("L'elaborazione della procedura CALCOLO_UTM è iniziata");
	  	    	}
	  		
	  		//Invio email
	  		eMail.inviaMail();
		
	}


	private void updateCalcolo(WrkCacloloUtm wcu) throws Exception, SQLException 
	  {
		  if (!isConnection())
		       throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn = null;
		StringBuffer query = null;
		PreparedStatement stmt = null;
		
		
		try
		{
		  
		  query = new StringBuffer(" UPDATE wrk_calcolo_utm SET ");
		  query.append(" coordinata_nord_utm = ?, coordinata_est_utm = ?, data_elaborazione =  current_timestamp, esito_elaborazione= ?, cod_err=?, msg_err=?  ");
		  query.append(" WHERE id_calcolo_utm = ? ");
		 // CuneoLogger.debug(this, query.toString());
		  conn = getConnection();
		  stmt = conn.prepareStatement(query.toString());
		  
		  stmt.setBigDecimal(1, Utili.parseBigDecimal(wcu.getCoordinataNord())); // il campo su db è NUMERIC
		  stmt.setBigDecimal(2, Utili.parseBigDecimal(wcu.getCoordinataEst()));  // il campo su db è NUMERIC
		 // stmt.setDate(3, new java.sql.Date(new java.util.Date().getTime())); //la data di sistema   current_timestamp
		  stmt.setString(3, wcu.getEsitoElaborazione()); 
		  stmt.setString(4, wcu.getCodErr());
		  stmt.setString(5, wcu.getMsgErr());
		  stmt.setLong(6, wcu.getIdCalcolo());
		  
		 int n = stmt.executeUpdate();
		
		}
		catch(java.sql.SQLException ex)
		{
		  this.getAut().setQuery("update della classe CentroideParticelle");
		  this.getAut().setContenutoQuery(query.toString());
		  ex.printStackTrace();
		  throw (ex);
		}
		catch(Exception e)
		{
		  this.getAut().setQuery("update della classe CentroideParticelle"
		                        +": non è una SQLException ma una Exception"
		                        +" generica");
		  e.printStackTrace();
		  throw (e);
		}
		finally
		{
		  if (conn!=null) conn.close();
		}
		
		
	}


	private List<WrkCacloloUtm> lista() throws Exception, SQLException{

		  if (!isConnection())
		       throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn = null;
		Statement stmt =null;
		List<WrkCacloloUtm> el = new ArrayList<WrkCacloloUtm>();
		StringBuffer query=new StringBuffer("");
	    try
	    {
	    	 conn=getConnection();
	         stmt = conn.createStatement();
	         query = new StringBuffer("select id_calcolo_utm, codice_istat, sezione, foglio, particella ");
	         query.append("from wrk_calcolo_utm ");
	         query.append("where esito_elaborazione is null ");
	        // CuneoLogger.debug(this, query.toString());
	         ResultSet rs=stmt.executeQuery(query.toString());
	         while (rs.next())
	         {
	        	 WrkCacloloUtm wcu = new WrkCacloloUtm();
	        	 wcu.setIdCalcolo(rs.getLong("id_calcolo_utm"));
	        	 wcu.setCodiceIstat(rs.getString("codice_istat"));
	        	 wcu.setSezione(rs.getString("sezione"));
	        	 wcu.setParticella(rs.getString("particella"));
	        	 wcu.setFoglio(rs.getString("foglio"));
	        	 el.add(wcu);
	         }
	         
	    }
	    catch(java.sql.SQLException ex)
		{
		  this.getAut().setQuery("select della classe CentroideParticelle");
		  this.getAut().setContenutoQuery(query.toString());
		  ex.printStackTrace();
		  throw (ex);
		}
		catch(Exception e)
		{
		  this.getAut().setQuery("select della classe CentroideParticelle"
		                        +": non è una SQLException ma una Exception"
		                        +" generica");
		  e.printStackTrace();
		  throw (e);
		}
		finally
		{
		  if (conn!=null) conn.close();
		}
		
		return el;
	}


	private int lockElab() throws Exception, SQLException 
	  {
		  if (!isConnection())
		       throw new Exception("Riferimento a DataSource non inizializzato");
		Connection conn = null;
		StringBuffer query = null;
		Statement stmt = null;
		
		int update = 0;
		try
		{
		  conn=getConnection();
	      stmt = conn.createStatement();
		  query = new StringBuffer(" UPDATE WRK_APPLICAZIONE_BATCH SET ");
		  query.append(" ELABORAZIONE_IN_CORSO = 'S' ");
		  query.append(" WHERE codice_applicazione = 'CALCOLO_UTM' AND ELABORAZIONE_IN_CORSO = 'N' ");
		 // CuneoLogger.debug(this, query.toString());
		  update = stmt.executeUpdate(query.toString());
		}
		catch(java.sql.SQLException ex)
		{
		  this.getAut().setQuery("update della classe CentroideParticelle");
		  this.getAut().setContenutoQuery(query.toString());
		  ex.printStackTrace();
		  throw (ex);
		}
		catch(Exception e)
		{
		  this.getAut().setQuery("update della classe CentroideParticelle"
		                        +": non è una SQLException ma una Exception"
		                        +" generica");
		  e.printStackTrace();
		  throw (e);
		}
		finally
		{
		  if (conn!=null) conn.close();
		}
		
		return update;
	}


	private String codVerificaByCodApp()  throws Exception, SQLException
	  {
		  //devo andare sul db per controllare se il codice inserito dall'utente c'è sulla tabella WRK_APPLICAZIONE_BATCH
		  if (!isConnection())
		      throw new Exception("Riferimento a DataSource non inizializzato");
		    Connection conn=null;
		    Statement stmt =null;
		    String codice="";
		    StringBuffer query=new StringBuffer("");
		    try
		    {
		    	 conn=getConnection();
		         stmt = conn.createStatement();
		         query = new StringBuffer("select codice_verifica_elab ");
		         query.append("from wrk_applicazione_batch w ");
		         query.append("where w.codice_applicazione = 'CALCOLO_UTM' ");
		         CuneoLogger.debug(this, query.toString());
		         ResultSet rs=stmt.executeQuery(query.toString());
		         if (rs.next())
		         {
		        	codice=rs.getString("codice_verifica_elab");
		         }
		     return codice;
		    }
		    catch(java.sql.SQLException ex)
		    {
		      this.getAut().setQuery("select della classe DatiAppezzamento");
		      this.getAut().setContenutoQuery(query.toString());
		      throw (ex);
		    }
		    catch(Exception e)
		    {
		      this.getAut().setQuery("select della classe DatiAppezzamento"
		                            +": non è una SQLException ma una Exception"
		                            +" generica");
		      this.getAut().setContenutoQuery(query.toString());
		      throw (e);
		    }
		    finally
		    {
		      if (conn!=null) conn.close();
		    }
		
	  }
	  
	public String getCodiceVerificaElab() {
		return codiceVerificaElab;
	}

	public void setCodiceVerificaElab(String codiceVerificaElab) {
		this.codiceVerificaElab = codiceVerificaElab;
	}

}
