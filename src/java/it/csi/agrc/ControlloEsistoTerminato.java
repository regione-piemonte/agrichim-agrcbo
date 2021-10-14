package it.csi.agrc;
import it.csi.cuneo.*;

//import java.util.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class ControlloEsistoTerminato extends BeanDataSource
{
  // *************  TERRENI  *************
  public static final String QUERY_CA_TERRENO =
    "SELECT C.ID_RICHIESTA FROM COMPLESSO_SCAMBIO C "
    +"WHERE C.LETTURA_CALCIO IS NOT NULL "
    +"AND C.DILUIZIONE_CALCIO IS NOT NULL "
    +"AND C.VBACL2_PER_ESTRAZIONE IS NOT NULL "
    +"AND C.PESO_TERRENO IS NOT NULL "
    +"AND C.ID_RICHIESTA = ";

  public static final String QUERY_MG_TERRENO =
    "SELECT C.ID_RICHIESTA FROM COMPLESSO_SCAMBIO C "
    +"WHERE C.LETTURA_MAGNESIO IS NOT NULL "
    +"AND C.DILUIZIONE_MAGNESIO IS NOT NULL "
    +"AND C.VBACL2_PER_ESTRAZIONE IS NOT NULL "
    +"AND C.PESO_TERRENO IS NOT NULL "
    +"AND C.ID_RICHIESTA = ";

  public static final String QUERY_K_TERRENO =
    "SELECT C.ID_RICHIESTA FROM COMPLESSO_SCAMBIO C "
    +"WHERE C.LETTURA_POTASSIO IS NOT NULL "
    +"AND C.DILUIZIONE_POTASSIO IS NOT NULL "
    +"AND C.VBACL2_PER_ESTRAZIONE IS NOT NULL "
    +"AND C.PESO_TERRENO IS NOT NULL "
    +"AND C.ID_RICHIESTA = ";

  public static final String QUERY_N_TERRENO =
    "SELECT A.ID_RICHIESTA FROM AZOTO A "
    +"WHERE (A.AZOTO_TOTALE_METODO_ANA IS NOT NULL "
    +"OR A.AZOTO_KJELDAHL IS NOT NULL) "
    +"AND A.ID_RICHIESTA = ";

  public static final String QUERY_P_TERRENO =
    "SELECT F.ID_RICHIESTA FROM FOSFORO_METODO_OLSEN F "
    +"WHERE F.LETTURA_FOSFORO IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String QUERY_CSC_TERRENO =
    "SELECT C.ID_RICHIESTA FROM COMPLESSO_SCAMBIO C "
    +"WHERE C.PESO_SECCO_PROVETTA IS NOT NULL "
    +"AND C.PESO_SECCO_ACQUA_PROVETTA IS NOT NULL "
    +"AND C.PESO_TERRENO IS NOT NULL "
    +"AND C.LETTURA_MAGNESIO_EDTA IS NOT NULL "
    +"AND C.LETTURA_BIANCO_EDTA IS NOT NULL "
    //+"AND C.VBACL2_PER_ESTRAZIONE IS NOT NULL "
    +"AND C.ID_RICHIESTA = ";

  public static final String QUERY_SO_TERRENO =
    "SELECT S.ID_RICHIESTA FROM SOSTANZA_ORGANICA S "
    +"WHERE (S.LETTURA_SOSTANZA_ORGANICA IS NOT NULL "
    +"OR S.CARBONIO_ORGANICO_METODO_ANA IS NOT NULL) "
    +"AND S.ID_RICHIESTA = ";

  public static final String QUERY_STD_TERRENO =
    "SELECT G.ID_RICHIESTA FROM GRANULOMETRIA_STANDARD G "
    +"WHERE G.ARGILLA IS NOT NULL "
    +"AND G.LIMO_TOTALE IS NOT NULL "
    +"AND G.ID_RICHIESTA = ";

  public static final String QUERY_4FRA_TERRENO =
    "SELECT G.ID_RICHIESTA FROM GRANULOMETRIA_A_4_FRAZIONI G "
    +"WHERE G.SABBIA_TOTALE IS NOT NULL "
    +"AND G.LIMO_TOTALE IS NOT NULL "
    +"AND G.LIMO_FINE IS NOT NULL "
    +"AND G.ID_RICHIESTA = ";

  public static final String QUERY_5FRA_TERRENO =
    "SELECT G.ID_RICHIESTA FROM GRANULOMETRIA_A_5_FRAZIONI G "
    +"WHERE G.SABBIA_GROSSA IS NOT NULL "
    +"AND G.SABBIA_TOTALE IS NOT NULL "
    +"AND G.LIMO_TOTALE IS NOT NULL "
    +"AND G.LIMO_FINE IS NOT NULL "
    +"AND G.ARGILLA IS NOT NULL "
    +"AND G.ID_RICHIESTA = ";

  public static final String QUERY_BOJOUCOS =
  " UNION "
  +"SELECT GB.ID_RICHIESTA FROM GRANULOMETRIA_METODO_BOJOUCOS GB "
  +"WHERE GB.ID_RICHIESTA = ";

  public static final String QUERY_SAL_TERRENO =
    "SELECT C.ID_RICHIESTA FROM CONDUCIBILITA_SALINITA C "
    +"WHERE C.CONDUCIBILITA IS NOT NULL "
    +"AND C.ID_RICHIESTA = ";

  public static final String QUERY_FE_TERRENO =
    "SELECT M.ID_RICHIESTA FROM MICROELEMENTI_METODO_DTPA M "
    +"WHERE M.LETTURA_FERRO IS NOT NULL "
    +"AND M.DILUIZIONE_FERRO IS NOT NULL "
    +"AND M.ID_RICHIESTA = ";

  public static final String QUERY_MN_TERRENO =
    "SELECT M.ID_RICHIESTA FROM MICROELEMENTI_METODO_DTPA M "
    +"WHERE M.LETTURA_MANGANESE IS NOT NULL "
    +"AND M.DILUIZIONE_MANGANESE IS NOT NULL "
    +"AND M.ID_RICHIESTA = ";

  public static final String QUERY_ZN_TERRENO =
    "SELECT M.ID_RICHIESTA FROM MICROELEMENTI_METODO_DTPA M "
    +"WHERE M.LETTURA_ZINCO IS NOT NULL "
    +"AND M.DILUIZIONE_ZINCO IS NOT NULL "
    +"AND M.ID_RICHIESTA = ";

  public static final String QUERY_CU_TERRENO =
    "SELECT M.ID_RICHIESTA FROM MICROELEMENTI_METODO_DTPA M "
    +"WHERE M.LETTURA_RAME IS NOT NULL "
    +"AND M.DILUIZIONE_RAME IS NOT NULL "
    +"AND M.ID_RICHIESTA = ";

  public static final String QUERY_B_TERRENO =
    "SELECT M.ID_RICHIESTA FROM MICROELEMENTI_METODO_DTPA M "
    +"WHERE M.LETTURA_BORO IS NOT NULL "
    +"AND M.DILUIZIONE_BORO IS NOT NULL "
    +"AND M.ID_RICHIESTA = ";

  // *************  TUTTI I MATERIALI  *************
  public static final String QUERY_UM =
    "SELECT U.ID_RICHIESTA FROM UMIDITA_CAMPIONE U "
    +"WHERE U.TARA IS NOT NULL "
    +"AND U.PESO_NETTO_UMIDO IS NOT NULL "
    +"AND U.PESO_LORDO_SECCO IS NOT NULL "
    +"AND U.ID_RICHIESTA = ";

  public static final String QUERY_FERRO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE FERRO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_MANGANESE_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE MANGANESE_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_ZINCO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE ZINCO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_RAME_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE RAME_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_PIOMBO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE PIOMBO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_CROMO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE CROMO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_BORO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE BORO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_NICHEL_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE NICHEL_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_CADMIO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE CADMIO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_STRONZIO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE STRONZIO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  public static final String QUERY_ALTRO_METALLO_TOTALE =
      "SELECT ID_RICHIESTA "
      +"FROM METALLI_PESANTI "
      +"WHERE ALTRO_METALLO_TOTALE IS NOT NULL "
      +"AND ID_RICHIESTA = ";

  // *************  FRUTTA  *************

  /*public static final String	QUERY_SOSTANZA_SECCA_FRUTTA =
    "SELECT U.ID_RICHIESTA FROM UMIDITA_CAMPIONE U  "
    +"WHERE U.SOSTANZA_SECCA IS NOT NULL "
    +"AND U.ID_RICHIESTA = ";*/

  public static final String	QUERY_AZOTO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
    +"WHERE F.AZOTO IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_FOSFORO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
    +"WHERE F.FOSFORO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_CALCIO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
   +"WHERE F.CALCIO_PPM IS NOT NULL "
   +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_MAGNESIO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
   +"WHERE F.MAGNESIO_PPM IS NOT NULL "
   +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_POTASSIO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
   +"WHERE F.POTASSIO_PPM IS NOT NULL "
   +"AND F.ID_RICHIESTA = ";
  public static final String	QUERY_FERRO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
    +"WHERE F.FERRO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_MANGANESE_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
    +"WHERE F.MANGANESE_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_ZINCO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
    +"WHERE F.ZINCO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_RAME_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
    +"WHERE F.RAME_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_BORO_FRUTTA =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FRUTTA F "
    +"WHERE F.BORO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";



  // *************  FOGLIE  *************

  /*public static final String	QUERY_SOSTANZA_SECCA_FOGLIE =
    "SELECT U.ID_RICHIESTA FROM UMIDITA_CAMPIONE U  "
    +"WHERE U.SOSTANZA_SECCA IS NOT NULL "
    +"AND U.ID_RICHIESTA = ";*/

  public static final String	QUERY_AZOTO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.AZOTO IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_FOSFORO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.FOSFORO IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_CALCIO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.CALCIO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_MAGNESIO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.MAGNESIO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_POTASSIO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.POTASSIO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_FERRO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.FERRO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_MANGANESE_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.MANGANESE_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_ZINCO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.ZINCO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_RAME_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.RAME_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  public static final String	QUERY_BORO_FOGLIE =
    "SELECT F.ID_RICHIESTA FROM MACRO_MICRO_ELEM_IND_FOGLIE F "
    +"WHERE F.BORO_PPM IS NOT NULL "
    +"AND F.ID_RICHIESTA = ";

  private String idRichiesta;



  public ControlloEsistoTerminato()
  {
  }

  /**
   * Questo metodo viene utilizzato per verificare che siano state effettuate
   * tutte le analisi richieste dal cliente
   * @param errore: se ci sono uno o più errori vengono memorizzati in questo
   * parametro
   * @param message : simile al parametro errore, con la differenza che in
   * questo caso non sono errori necessariamente bloccanti
   * @throws Exception
   * @throws SQLException
   */
  public void controllaAnalisi(StringBuffer errore,StringBuffer message)
  throws Exception, SQLException
  {
    /**
    * Leggo i dati dalla tabella Analisi
    */
    Analisi analisi=new Analisi();
    analisi.setIdRichiesta(getAut().getIdRichiestaCorrente());

    if (Utili.POOLMAN)
    {
     //Sono in ambiente TomCat quindi imposto il pool
     //di PoolMan
     analisi.setGenericPool(getGenericPool());
    }
    else
    {
     //Sono in ambiente WebLogic quindi imposto
     //il DataSource
     analisi.setDataSource(getDataSource());
    }
    analisi.setAut(getAut());

    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;

    StringBuffer query=new StringBuffer("");
    StringBuffer temp=new StringBuffer();
    String codMateriale=getAut().getCodMateriale();
    try
    {
      conn=getConnection();
      Statement stmt = conn.createStatement();

      /**
       * Vado a vedere tutte le analisi richieste associate al campione
       * */
      analisi.select();
      /**
      *  Devo leggere i dati dal database
      **/
      java.util.Enumeration enumAnalisi=analisi.getCodiciAnalisi().elements();
      String codice;
      while(enumAnalisi.hasMoreElements())
      {
        codice=(String)enumAnalisi.nextElement();
        if (codMateriale.equals(Analisi.MAT_TERRENO))
        {
          // ******** Terreni ********
          temp.setLength(0);

          if (Analisi.ANA_PH.equals(codice))
          {
            if (eseguiQueryPH(stmt,temp)) message.append(temp);
            else errore.append(temp);
          }
          else
          {
            if (Analisi.ANA_CALCARETOTALE.equals(codice))
            {
              if (eseguiQueryCaCO3(stmt,temp)) message.append(temp);
              else errore.append(temp);
            }
            else
            {
              if (Analisi.ANA_CALCAREATTIVO.equals(codice))
              {
                if (eseguiQueryCaAtt(stmt,temp)) message.append(temp);
                else errore.append(temp);
              }
              else
              {
                if (Analisi.ANA_PACCHETTO_TIPO.equals(codice))
                {
                  if (eseguiQueryPH(stmt,temp)) message.append(temp);
                  else errore.append(temp);
                  temp.setLength(0);
                  if (eseguiQueryCaCO3(stmt,temp)) message.append(temp);
                  else errore.append(temp);
                  temp.setLength(0);
                  eseguiQueryTerreni(stmt,Analisi.ANA_CALCIO,errore);
                  eseguiQueryTerreni(stmt,Analisi.ANA_MAGNESIO,errore);
                  eseguiQueryTerreni(stmt,Analisi.ANA_POTASSIO,errore);
                  eseguiQueryTerreni(stmt,Analisi.ANA_AZOTO,errore);
                  eseguiQueryTerreni(stmt,Analisi.ANA_FOSFORO,errore);
                  eseguiQueryTerreni(stmt,Analisi.ANA_CAPACITASCAMBIOCATIONICO,errore);
                  eseguiQueryTerreni(stmt,Analisi.ANA_SOSTANZAORGANICA,errore);
                }
                else
                {
                  if (Analisi.ANA_PACCHETTO_COMP_SCAMBIO.equals(codice))
                  {
                    eseguiQueryTerreni(stmt,Analisi.ANA_CALCIO,errore);
                    eseguiQueryTerreni(stmt,Analisi.ANA_MAGNESIO,errore);
                    eseguiQueryTerreni(stmt,Analisi.ANA_POTASSIO,errore);
                    eseguiQueryTerreni(stmt,Analisi.ANA_CAPACITASCAMBIOCATIONICO,errore);
                  }
                  else
                  {
                    if (Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codice))
                    {
                      eseguiQueryTerreni(stmt,Analisi.ANA_FERRO,errore);
                      eseguiQueryTerreni(stmt,Analisi.ANA_MANGANESE,errore);
                      eseguiQueryTerreni(stmt,Analisi.ANA_ZINCO,errore);
                      eseguiQueryTerreni(stmt,Analisi.ANA_RAME,errore);
                    }
                    else
                    {
                      /*if (Analisi.ANA_PACCHETTO_METALLI_PESANTI.equals(codice))
                      {
                      	eseguiQueryTerreni(stmt, Analisi.ANA_FERRO_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_MANGANESE_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_ZINCO_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_RAME_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_BORO_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_CADMIO_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_CROMO_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_NICHEL_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_PIOMBO_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_STRONZIO_TOTALE, errore);
                      	eseguiQueryTerreni(stmt, Analisi.ANA_ALTRO_METALLO_TOTALE, errore);                      	
                      }
                      else
                      {*/
                      	eseguiQueryTerreni(stmt, codice, errore);
                      //}
                    }
                  }
                }
              }
            }
          }
        }
        else
        {
          if (codMateriale.equals(Analisi.MAT_FRUTTA))
          {
                        // ******** Frutta ********
            if (Analisi.ANA_PACCHETTO_TIPO.equals(codice))
            {
              eseguiQueryFrutta(stmt,Analisi.ANA_CALCIO,errore);
              eseguiQueryFrutta(stmt,Analisi.ANA_MAGNESIO,errore);
              eseguiQueryFrutta(stmt,Analisi.ANA_POTASSIO,errore);
              eseguiQueryFrutta(stmt,Analisi.ANA_AZOTO,errore);
              eseguiQueryFrutta(stmt,Analisi.ANA_FOSFORO,errore);
            }
            else
            {
              if (Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codice))
              {
                eseguiQueryFrutta(stmt,Analisi.ANA_FERRO,errore);
                eseguiQueryFrutta(stmt,Analisi.ANA_MANGANESE,errore);
                eseguiQueryFrutta(stmt,Analisi.ANA_ZINCO,errore);
                eseguiQueryFrutta(stmt,Analisi.ANA_RAME,errore);
                eseguiQueryFrutta(stmt,Analisi.ANA_BORO,errore);
              }
              else eseguiQueryFrutta(stmt,codice,errore);
            }
          }
          else
          {
            // ******** Foglie , erbacee ********
            if (Analisi.ANA_PACCHETTO_TIPO.equals(codice))
            {
              eseguiQueryFoglie(stmt,Analisi.ANA_CALCIO,errore);
              eseguiQueryFoglie(stmt,Analisi.ANA_MAGNESIO,errore);
              eseguiQueryFoglie(stmt,Analisi.ANA_POTASSIO,errore);
              eseguiQueryFoglie(stmt,Analisi.ANA_AZOTO,errore);
              eseguiQueryFoglie(stmt,Analisi.ANA_FOSFORO,errore);
            }
            else
            {
              if (Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codice))
              {
                eseguiQueryFoglie(stmt,Analisi.ANA_FERRO,errore);
                eseguiQueryFoglie(stmt,Analisi.ANA_MANGANESE,errore);
                eseguiQueryFoglie(stmt,Analisi.ANA_ZINCO,errore);
                eseguiQueryFoglie(stmt,Analisi.ANA_RAME,errore);
                eseguiQueryFoglie(stmt,Analisi.ANA_BORO,errore);
              }
              else eseguiQueryFoglie(stmt,codice,errore);
            }
          }
        }
      }

      stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("controllaAnalisi della classe ControlloEsistoTerminato");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("controllaAnalisi della classe ControlloEsistoTerminato"
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

  /**
   * Qesto metodo esegue una query diversa a seconda del valore del parametro
   * codice
   * @param stmt:mi permette di eseguire la query
   * @param codice: indica la query da eseguire
   * @param message: contiene l'eventuale messagio di errore relativo al
   * all'analisi richiesta non rispettata
   * @return vale true se è stato rispettata l'analisi richiesta dal cliente,
   * false altrimenti
   * @throws Exception
   * @throws SQLException
   */
  public boolean eseguiQueryTerreni(Statement stmt,String codice,StringBuffer message)
  throws Exception, SQLException
  {
    String query=null;
    boolean ris=false;
    String errore="";

    if (Analisi.ANA_CALCIO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_CA_TERRENO;
      errore="Ca: risultati dell'analisi «complesso di scambio» incompleti\\n";
    }
    else if (Analisi.ANA_MAGNESIO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_MG_TERRENO;
      errore="Mg: risultati dell'analisi «complesso di scambio» incompleti\\n";
    }
    else if (Analisi.ANA_POTASSIO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_K_TERRENO;
      errore="K: risultati dell'analisi «complesso di scambio» incompleti\\n";
    }
    else if (Analisi.ANA_AZOTO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_N_TERRENO;
      errore="N: risultati dell'analisi «azoto» incompleti\\n";
    }
    else if (Analisi.ANA_FOSFORO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_P_TERRENO;
      errore="P: risultati dell'analisi «fosforo OLSEN» incompleti\\n";
    }
    else if (Analisi.ANA_CAPACITASCAMBIOCATIONICO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_CSC_TERRENO;
      errore="CSC: risultati dell'analisi «complesso di scambio» incompleti\\n";
    }
    else if (Analisi.ANA_SOSTANZAORGANICA.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_SO_TERRENO;
      errore="SO: risultati dell'analisi «sostanza organica» incompleti\\n";
    }
    else if (Analisi.ANA_STANDARD.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_STD_TERRENO;
      query+=getIdRichiesta();
      query+=ControlloEsistoTerminato.QUERY_BOJOUCOS;
      errore="Std: risultati dell'analisi «granulometria metodo automatico» incompleti\\n";
    }
    else if (Analisi.ANA_A4FRAZIONI.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_4FRA_TERRENO;
      query+=getIdRichiesta();
      query+=ControlloEsistoTerminato.QUERY_BOJOUCOS;
      errore="4FRA: risultati dell'analisi «granulometria metodo automatico» incompleti\\n";
    }
    else if (Analisi.ANA_A5FRAZIONI.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_5FRA_TERRENO;
      query+=getIdRichiesta();
      query+=ControlloEsistoTerminato.QUERY_BOJOUCOS;
      errore="5FRA: risultati dell'analisi «granulometria metodo automatico» incompleti\\n";
    }
    else if (Analisi.ANA_SALINITA.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_SAL_TERRENO;
      errore="Sal: risultati dell'analisi «conducibilità e salinità» incompleti\\n";
    }
    else if (Analisi.ANA_FERRO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_FE_TERRENO;
      errore="Fe: risultati dell'analisi «microelementi» incompleti\\n";
    }
    else if (Analisi.ANA_MANGANESE .equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_MN_TERRENO;
      errore="Mn: risultati dell'analisi «microelementi» incompleti\\n";
    }
    else if (Analisi.ANA_ZINCO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_ZN_TERRENO;
      errore="Zn: risultati dell'analisi «microelementi» incompleti\\n";
    }
    else if (Analisi.ANA_RAME.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_CU_TERRENO;
      errore="Cu: risultati dell'analisi «microelementi» incompleti\\n";
    }
    else if (Analisi.ANA_BORO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_B_TERRENO;
      errore="B: risultati dell'analisi «microelementi» incompleti\\n";
    }
    else if (Analisi.ANA_UMIDITA.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_UM;
      errore="Um: risultati dell'analisi «umidità del campione» incompleti\\n";
    }
    else if (Analisi.ANA_FERRO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_FERRO_TOTALE;
      errore = "FeTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    else if (Analisi.ANA_MANGANESE_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_MANGANESE_TOTALE;
      errore = "MnTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    else  if (Analisi.ANA_ZINCO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_ZINCO_TOTALE;
      errore = "ZnTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    else if (Analisi.ANA_RAME_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_RAME_TOTALE;
      errore = "CuTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    else if (Analisi.ANA_PIOMBO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_PIOMBO_TOTALE;
      errore = "PbTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    else if (Analisi.ANA_CROMO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_CROMO_TOTALE;
      errore = "CrTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
   /* else if (Analisi.ANA_BORO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_BORO_TOTALE;
      errore = "BTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    */
    else if (Analisi.ANA_NICHEL_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_NICHEL_TOTALE;
      errore = "NiTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    else if (Analisi.ANA_CADMIO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_CADMIO_TOTALE;
      errore = "CdTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
  /*  else if (Analisi.ANA_STRONZIO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_STRONZIO_TOTALE;
      errore = "SrTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }
    */
    /*else if (Analisi.ANA_ALTRO_METALLO_TOTALE.equals(codice))
    {
      query = ControlloEsistoTerminato.QUERY_ALTRO_METALLO_TOTALE;
      errore = "MetTot: risultati dell'analisi «metalli pesanti» incompleti\\n";
    }*/

    ResultSet rset = null;
    query+=getIdRichiesta();
    //CuneoLogger.debug(this, "Query "+query);
    rset=stmt.executeQuery(query);
    if (rset.next()) ris=true;
    if (!ris) message.append(errore);
    rset.close();
    return ris;
  }

  /**
   * Qesto metodo esegue una query diversa a seconda del valore del parametro
   * scelta
   * @param stmt:mi permette di eseguire la query
   * @param scelta: indica la query da eseguire
   * @param message: contiene l'eventuale messagio di errore relativo al
   * all'analisi richiesta non rispettata
   * @return vale true se è stato rispettata l'analisi richiesta dal cliente,
   * false altrimenti
   * @throws Exception
   * @throws SQLException
   */
  public boolean eseguiQueryFrutta(Statement stmt,String codice,StringBuffer message)
  throws Exception, SQLException
  {
    String query=null;
    boolean ris=false;
    String errore="";

    if (Analisi.ANA_UMIDITA.equals(codice))
    {
      query=ControlloEsistoTerminato.QUERY_UM;
      errore="Um: risultati dell'analisi «umidità del campione» incompleti\\n";
    }
    if (Analisi.ANA_AZOTO.equals(codice))
    {
      query=ControlloEsistoTerminato.QUERY_AZOTO_FRUTTA ;
      errore="N%: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_FOSFORO.equals(codice))
    {
      query=ControlloEsistoTerminato.QUERY_FOSFORO_FRUTTA ;
      errore="P ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_CALCIO.equals(codice))
    {
      query=ControlloEsistoTerminato.QUERY_CALCIO_FRUTTA ;
      errore="Ca ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_MAGNESIO.equals(codice))
    {
      query=ControlloEsistoTerminato.QUERY_MAGNESIO_FRUTTA ;
      errore="Mg ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_POTASSIO.equals(codice))
    {
      query=ControlloEsistoTerminato.QUERY_POTASSIO_FRUTTA;
      errore="K ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_FERRO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_FERRO_FRUTTA;
      errore="Fe ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_MANGANESE.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_MANGANESE_FRUTTA;
      errore="Mn ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_ZINCO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_ZINCO_FRUTTA;
      errore="Zn ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_RAME.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_RAME_FRUTTA;
      errore="Cu ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_BORO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_BORO_FRUTTA;
      errore="B ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }

    ResultSet rset = null;
    query+=getIdRichiesta();
    //CuneoLogger.debug(this, "Query "+query);
    rset=stmt.executeQuery(query);
    if (rset.next()) ris=true;
    if (!ris) message.append(errore);
    rset.close();
    return ris;
  }

  /**
   * Qesto metodo esegue una query diversa a seconda del valore del parametro
   * scelta
   * @param stmt:mi permette di eseguire la query
   * @param scelta: indica la query da eseguire
   * @param message: contiene l'eventuale messagio di errore relativo al
   * all'analisi richiesta non rispettata
   * @return vale true se è stato rispettata l'analisi richiesta dal cliente,
   * false altrimenti
   * @throws Exception
   * @throws SQLException
   */
  public boolean eseguiQueryFoglie(Statement stmt,String codice,StringBuffer message)
  throws Exception, SQLException
  {
    String query=null;
    boolean ris=false;
    String errore="";

    if (Analisi.ANA_UMIDITA.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_UM;
      errore="Um: risultati dell'analisi «umidità del campione» incompleti\\n";
    }

    if (Analisi.ANA_AZOTO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_AZOTO_FOGLIE ;
      errore="N%: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_FOSFORO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_FOSFORO_FOGLIE ;
      errore="P%: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_CALCIO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_CALCIO_FOGLIE ;
      errore="Ca ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_MAGNESIO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_MAGNESIO_FOGLIE;
      errore="Mg ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_POTASSIO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_POTASSIO_FOGLIE;
      errore="K ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_FERRO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_FERRO_FOGLIE;
      errore="Fe ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_MANGANESE.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_MANGANESE_FOGLIE;
      errore="Mn ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_ZINCO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_ZINCO_FOGLIE;
      errore="Zn ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_RAME.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_RAME_FOGLIE;
      errore="Cu ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }
    if (Analisi.ANA_BORO.equals(codice))
    {
      query= ControlloEsistoTerminato.QUERY_BORO_FOGLIE;
      errore="B ppm: risultati dell'analisi «macro e micro elementi-indici» incompleti\\n";
    }

    ResultSet rset = null;
    query+=getIdRichiesta();
    //CuneoLogger.debug(this, "Query "+query);
    rset=stmt.executeQuery(query);
    if (rset.next()) ris=true;
    if (!ris) message.append(errore);
    rset.close();
    return ris;
  }

  /**
   * Esegue il controllo sulla richiesta di analisi del PH
   * @param stmt: mi permette di eseguire la query
   * @param message: se uno dei tre campi è valorizzato della tabella è
   * valorizzato ma non è PH_ACQUA contiene un messagio di avvertimento
   * @return resituisce true se tutto è andato a buon fine, altrimenti false
   * @throws Exception
   * @throws SQLException
   */
  public boolean eseguiQueryPH(Statement stmt,StringBuffer message)
  throws Exception, SQLException
  {
    StringBuffer query=null;
    boolean ris=false;
    ResultSet rset = null;
    query=new StringBuffer("SELECT R.PH_ACQUA FROM REAZIONE_SUOLO R ");
    query.append("WHERE (R.PH_ACQUA IS NOT NULL ");
    query.append("OR R.PH_CLORURO_POTASSIO IS NOT NULL ");
    query.append("OR R.PH_TAMPONE IS NOT NULL) ");
    query.append("AND R.ID_RICHIESTA = ").append(getIdRichiesta());
    //CuneoLogger.debug(this, "Query "+query.toString());
    rset=stmt.executeQuery(query.toString());
    if (rset.next())
    {
      ris=true;
      if (rset.getString("PH_ACQUA")==null) message.append("Il pH inserito non è quello in acqua\\n");
    }
    else message.append("pH: risultati dell'analisi «reazione del suolo» incompleti\\n");
    rset.close();
    return ris;
  }

  /**
   * Esegue il controllo sulla richiesta di analisi del CaCO3
   * @param stmt: mi permette di eseguire la query
   * @param message: se uno dei campi (LETTURA_CALCIMETRO,PRESSIONE_ATMOSFERICA
   * ,TEMPERATURA) non è valorizzato contiene un messagio che specifica il
   * dato mancante
   * @return resituisce true se tutto è andato a buon fine, altrimenti false
   * @throws Exception
   * @throws SQLException
   */
  public boolean eseguiQueryCaCO3(Statement stmt,StringBuffer message)
  throws Exception, SQLException
  {
    StringBuffer query=null;
    boolean ris=false;
    ResultSet rset = null;
    query=new StringBuffer("SELECT LETTURA_CALCIMETRO,PRESSIONE_ATMOSFERICA,  ");
    query.append("TEMPERATURA FROM CALCIMETRIA ");
    query.append("WHERE ID_RICHIESTA = ").append(getIdRichiesta());
    //CuneoLogger.debug(this, "Query "+query.toString());
    rset=stmt.executeQuery(query.toString());
    if (rset.next())
    {
      ris=true;
      if (rset.getString("LETTURA_CALCIMETRO")==null) message.append("LETTURA_CALCIMETRO non è stato valorizzato\\n");
      if (rset.getString("PRESSIONE_ATMOSFERICA")==null) message.append("PRESSIONE_ATMOSFERICA non è stato valorizzato\\n");
      if (rset.getString("TEMPERATURA")==null) message.append("TEMPERATURA non è stato valorizzato\\n");
    }
    else message.append("CaCO3: risultati dell'analisi «calcimetria» incompleti\\n");
    rset.close();
    return ris;
  }

  /**
   * Esegue il controllo sulla richiesta di analisi del CaCO3
   * @param stmt: mi permette di eseguire la query
   * @param message: se il campo (CALCARE_ATTIVO) non è valorizzato contiene un
   * messagio che specifica il dato mancante
   * @return resituisce true se tutto è andato a buon fine, altrimenti false
   * @throws Exception
   * @throws SQLException
   */
  public boolean eseguiQueryCaAtt(Statement stmt,StringBuffer message)
  throws Exception, SQLException
  {
    StringBuffer query=null;
    boolean ris=false;
    ResultSet rset = null;
    query=new StringBuffer("SELECT CALCARE_ATTIVO ");
    query.append("FROM CALCIMETRIA ");
    query.append("WHERE ID_RICHIESTA = ").append(getIdRichiesta());
    //CuneoLogger.debug(this, "Query "+query.toString());
    rset=stmt.executeQuery(query.toString());
    if (rset.next())
    {
      ris=true;
      if (rset.getString("CALCARE_ATTIVO")==null) message.append("CALCARE_ATTIVO non è stato valorizzato\\n");
    }
    else message.append("CaAtt: risultati dell'analisi «calcimetria» incompleti\\n");
    rset.close();
    return ris;
  }

  public void setIdRichiesta(String idRichiesta)
  {
    this.idRichiesta = idRichiesta;
  }
  public String getIdRichiesta()
  {
    return idRichiesta;
  }
}