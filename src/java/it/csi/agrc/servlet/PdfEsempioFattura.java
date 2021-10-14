package it.csi.agrc.servlet;

//import java.io.*;
import inetsoft.report.StyleConstants;
import inetsoft.report.TabularSheet;
import inetsoft.report.lens.DefaultTableLens;
import inetsoft.report.lens.DefaultTextLens;
import inetsoft.report.painter.ImagePainter;
import it.csi.agrc.AllegatoEmailVO;
import it.csi.agrc.Autenticazione;
import it.csi.agrc.BeanParametri;
import it.csi.agrc.CampioneFatturato;
import it.csi.agrc.EtichettaCampione;
import it.csi.agrc.EtichettaCampioni;
import it.csi.agrc.Fattura;
import it.csi.agrc.Laboratorio;
import it.csi.agrc.StoricoIva;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.ReportUtils;
import it.csi.cuneo.Utili;

import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfEsempioFattura extends PdfServletAgrc
{
  private static final long serialVersionUID = 670332203511899116L;

  HttpSession session;
  ServletContext context;
  Autenticazione aut;
  Object dataSource;

  public PdfEsempioFattura()
  {
    this.setOutputName("FatturaAnalisiCampione.pdf");
    this.setTemplateName("pdfEsempioFattura.srt");
  }

  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
  	super.service(request, response);

  	BeanParametri beanParametriApplication = (BeanParametri)context.getAttribute("beanParametriApplication");

    EtichettaCampione etichettaCampione = new EtichettaCampione();
    etichettaCampione.setDataSource(dataSource);
    etichettaCampione.setAut(aut);

    String richieste = request.getParameter("richieste");

  	//AGRICHIM-90
  	//Terminata la generazione del documento PDF il sistema invia via email la fattura appena emessa, come allegato dell'email.
  	//L'email viene inviata con i seguenti parametri:

  	//Mittente: indirizzo predefinito per l'invio delle email
  	it.csi.cuneo.Mail eMail = new it.csi.cuneo.Mail(); 
  	eMail.setEMailSender(beanParametriApplication.getMailMittenteAgrichim());

  	try
		{
    	//Destinatario: viene impostato come destinatario l'indirizzo email legato a "ANAGRAFICA_TECNICO" di "ETICHETTA_CAMPIONE"
    	//(se non valorizzato viene preso "ANAGRAFICA_PROPRIETARIO" e in assenza anche di questo "ANAGRAFICA_UTENTE").
    	//Nel caso in cui l'indirizzo email non sia valorizzato l'email non viene inviata.
    	//Nel caso in cui la fattura comprenda più richieste di analisi verrà inviata un'unica email con tanti destinatari quante sono le richieste collegate.  		
			String[] destinatari = etichettaCampione.selectEmailForCreaFatturaByIdRichiestaRange(richieste);

	  	if (destinatari != null && destinatari.length > 0)
	  	{
	  		//Mittente
	  		eMail.setHost(beanParametriApplication.getHostServerPosta());

	  		//Destinatario (può essere più di uno)
	  		eMail.setEMailReceiver(destinatari);

	  		//Oggetto: Fattura per richiesta analisi in corso di emissione
	  		eMail.setSubject("Fattura per richiesta analisi in corso di emissione");

	  		//Testo email
	  		String contenutoEmail = "E' in corso l'emissione della fattura per la/e richiesta/e "
	  		    + "di analisi numero $$NUM_RICHIESTA. Con le nuove modalità di fatturazione "
	  		    + "elettronica, la fattura sara' inviata tramite il Sistema di Interscambio "
	  		    + "nazionale al Codice Destinatario del richiedente che e' stato fornito "
	  		    + "al momento della presentazione della richiesta.";
	  		eMail.setContenutoEmail(contenutoEmail.replace("$$NUM_RICHIESTA", richieste));

	  		//Allegato: pdf della fattura generato dal sistema
	  		//eMail.addAllegati(new AllegatoEmailVO((byte[]) request.getAttribute(REQUEST_FILE), (String) request.getAttribute(REQUEST_FILE_NAME)));

	  		//Invio email
	  		eMail.inviaMailConAllegati();
	  	}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

  protected void stampaPdf(HttpServletRequest request,
                           TabularSheet xss)
      throws Exception
  {
    String idFattura=null,anno=null;

    // Questo PDF va generato solo se è stato effettuato con successo il login
    session = request.getSession();
    aut = (Autenticazione)session.getAttribute("aut");
    controllaAut(aut);


    CuneoLogger.debug(this, "PdfEsempioFattura.stampaPdf() - Inizio creazione pdf");
    ImagePainter logoRegione = new ImagePainter(this.getImage("logoRegione.gif"));
    xss.setElement("imgLogoRegione",logoRegione);
    
    idFattura=request.getParameter("idFatturaPDF");
    anno=request.getParameter("annoPDF");

    context=session.getServletContext();
    if (Utili.POOLMAN)
      dataSource=context.getAttribute("poolBean");
    else
      dataSource=context.getAttribute("dataSourceBean");

    Laboratorio laboratorio = new Laboratorio(dataSource, aut);

    //Impostazione dei dati relativi alla testa del PDF: sono dati che si trovano
    //all'interno della tabella parametro e vengono precaricati all'avvio dell'applicativo
    // nel bean BeanParametri
    BeanParametri beanParametriApplication=(BeanParametri)context.getAttribute("beanParametriApplication");


    laboratorio.select(null,beanParametriApplication.getPartitaIVALab());
    /***************************************************************
     * Header comune a tutte le pagine
     */
    xss.setElement("tbxIndirizzo","SEDE: "+laboratorio.getIndirizzoPdf());

    String direzione = "";
    
    if(beanParametriApplication.getDirezione()!=null)
    {
    	direzione = beanParametriApplication.getDirezione();
    }
    
    xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+direzione);

  //  xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());
    xss.setElement("tbxSettore",beanParametriApplication.getSettore()+"\n"+beanParametriApplication.getLabAgr());



    /**
     * Leggo i dati riguardanti l'intestatario della fattura
     * */
    Fattura fatturaPDF=new Fattura();
    fatturaPDF.setDataSource(dataSource);
    fatturaPDF.setAut(aut);
    fatturaPDF.select(idFattura,anno);
    
    StoricoIva storicoIva = new StoricoIva();
    storicoIva.setDataSource(dataSource);
    storicoIva.setAut(aut);
    storicoIva.selectStoricoIvaByData(Utili.parseDate(fatturaPDF.getDataFattura()));    

    DefaultTextLens textBox124=new  DefaultTextLens();
    if ("S".equals(fatturaPDF.getAnnullata()))
      textBox124.setText("ANNULLATA ");
    else  textBox124.setText(" ");
    xss.setElement("TextBox124", textBox124);

    DefaultTextLens textBox10=new  DefaultTextLens();
    textBox10.setText(fatturaPDF.getRagioneSociale()+" ");
    xss.setElement("TextBox10", textBox10);

    DefaultTextLens textBox12=new  DefaultTextLens();
    textBox12.setText(fatturaPDF.getDataFattura()+" ");
    xss.setElement("TextBox12", textBox12);

    DefaultTextLens textBox14=new  DefaultTextLens();
    textBox14.setText(fatturaPDF.getIndirizzo()+" ");
    xss.setElement("TextBox14", textBox14);

    DefaultTextLens textBox16=new  DefaultTextLens();
    textBox16.setText(fatturaPDF.getNumeroFattura()+"/F ");
    xss.setElement("TextBox16", textBox16);

    DefaultTextLens textBox18=new  DefaultTextLens();
    textBox18.setText(fatturaPDF.getCap()+" ");
    xss.setElement("TextBox18", textBox18);

    DefaultTextLens textBox20=new  DefaultTextLens();
    textBox20.setText(fatturaPDF.getComune()+" ");
    xss.setElement("TextBox20", textBox20);

    DefaultTextLens textBox24=new  DefaultTextLens();
    textBox24.setText(fatturaPDF.getSiglaProvincia()+" ");
    xss.setElement("TextBox24", textBox24);

    DefaultTextLens textBox27=new  DefaultTextLens();
    textBox27.setText(fatturaPDF.getPartitaIvaOCf()+" ");
    xss.setElement("TextBox27", textBox27);


    /**
     * Leggo i dati dei campioni fatturati
     * */
    CampioneFatturato campioneFatturatoPDF=new CampioneFatturato();
    campioneFatturatoPDF.setDataSource(dataSource);
    campioneFatturatoPDF.setAut(aut);
    Vector camp=campioneFatturatoPDF.select(idFattura,anno);
    int size=camp.size();

    EtichettaCampioni etichettePDF=new EtichettaCampioni();
    etichettePDF.setDataSource(dataSource);
    etichettePDF.setAut(aut);
    etichettePDF.selectForFatturaPDF(idFattura,anno);

    DefaultTableLens tblFatture=new DefaultTableLens(xss.getTable("tblFatture"));

    //Intestazione tabella
    ReportUtils.setIntestazioneTabella(xss, tblFatture, "tblFatture");
    
    //Righe il cui contenuto non può andare a capo
    tblFatture.setRowLineWrap(0, false);
    tblFatture.setRowLineWrap(2, false);
    tblFatture.setRowLineWrap(3, false);
    tblFatture.setRowLineWrap(4, false);
    
    CampioneFatturato campione=null;
    EtichettaCampione e=null;
    double imponibile=0;
    double iva=0;
    double importoSpedizione=0;
    double totaleImponibile=0;
    double totaleIVA=0;
    double totaleImportoSpedizione=0;
    String etichetta=null;

    boolean inBianco = false;
    for (int i=1;i<=size;i++)
    {
    	if (etichettePDF.size() > 0)
    	{
	      e= (EtichettaCampione) etichettePDF.get(i-1);
	      etichetta="Analisi "+e.getDescMateriale();
	      if (e.getDescrizioneEtichetta()==null)
	        etichetta+=" ";
	      else
	        etichetta+="- "+e.getDescrizioneEtichetta();
	      etichetta+=" di "+e.getProprietario();
    	}

      //CuneoLogger.debug(this, "etichetta "+etichetta);

      tblFatture.addRow();
      int posRiga = tblFatture.getRowCount() - 1;
      
      campione=(CampioneFatturato) camp.get(i-1);
      tblFatture.setObject(posRiga,0,campione.getIdRichiesta());
      if (campione.getIdRichiesta() == null)
      {
      
      	//per AGRICHIM - 103
      	inBianco=true;
      	
      	//AGRICHIM-35
      	//Emissione fattura in bianco
      	etichetta = fatturaPDF.getDescrizione();
      	
      	totaleImportoSpedizione = Double.parseDouble((fatturaPDF.getImportoSpedizione()).replace(',','.'));
      }
      else
      {
        importoSpedizione=Double.parseDouble((campione.getImportoSpedizione()).replace(',','.'));
        totaleImportoSpedizione+=importoSpedizione;      	
      }
      
      tblFatture.setObject(posRiga,1,etichetta);
      tblFatture.setObject(posRiga,2,storicoIva.getIvaFormattata());
      tblFatture.setObject(posRiga,3,campione.getImportoImponibile().replace('.',','));
      imponibile=Double.parseDouble((campione.getImportoImponibile()).replace(',','.'));
      totaleImponibile+=imponibile;

      tblFatture.setObject(posRiga,4,campione.getImportoIva().replace('.',','));
      iva=Double.parseDouble((campione.getImportoIva()).replace(',','.'));
      totaleIVA+=iva;

      //IMPOSTO L'ALLINEAMENTO PER TUTTE LE ALTRE RIGHE
      tblFatture.setAlignment(posRiga,0,StyleConstants.H_CENTER);
      tblFatture.setAlignment(posRiga,1,StyleConstants.H_LEFT);
      tblFatture.setAlignment(posRiga,2,StyleConstants.H_CENTER);
      tblFatture.setAlignment(posRiga,3,StyleConstants.H_RIGHT);
      tblFatture.setAlignment(posRiga,4,StyleConstants.H_RIGHT);
    }
    if(inBianco)
    {
    	tblFatture.removeColumn(0);
    }
    xss.setElement("tblFatture", tblFatture);

    DefaultTableLens tblTotali=new DefaultTableLens(xss.getTable("tblTotali"));

    tblTotali.setRowFont(3,new Font("SansSerif",Font.BOLD,10));

    tblTotali.setObject(0,1,Utili.nf2.format(totaleImponibile).replace('.',','));
    tblTotali.setObject(1,1,Utili.nf2.format(totaleIVA).replace('.',','));
    tblTotali.setObject(2,1,Utili.nf2.format(totaleImportoSpedizione).replace('.',','));
    tblTotali.setObject(3,1,Utili.nf2.format(totaleImponibile+totaleIVA+totaleImportoSpedizione).replace('.',','));

    tblTotali.setAlignment(0,0,StyleConstants.H_RIGHT);
    tblTotali.setAlignment(1,0,StyleConstants.H_RIGHT);
    tblTotali.setAlignment(2,0,StyleConstants.H_RIGHT);
    tblTotali.setAlignment(3,0,StyleConstants.H_RIGHT);
    tblTotali.setAlignment(0,1,StyleConstants.H_RIGHT);
    tblTotali.setAlignment(1,1,StyleConstants.H_RIGHT);
    tblTotali.setAlignment(2,1,StyleConstants.H_RIGHT);
    tblTotali.setAlignment(3,1,StyleConstants.H_RIGHT);

    //Bordi
    for (int r = -1; r < tblTotali.getRowCount(); r++)
    {
    	tblTotali.setColBorder(r, -1, StyleConstants.NO_BORDER);
    	tblTotali.setRowBorder(r, 0, StyleConstants.NO_BORDER);	 
    }
    
    xss.setElement("tblTotali", tblTotali);
    
    /**
     * Questo viene usato quando non devo far visualizzare il footer
     * */
    //CuneoLogger.debug(this, "fatturaPDF.getPagata() "+fatturaPDF.getPagata());
    if ("S".equals(fatturaPDF.getPagata()))
      xss.setFooterFromEdge(0);
    else
    {
      //valorizzo gli estremi di pagamento
      xss.setElement("txt2",beanParametriApplication.getTXT2());
      //xss.setElement("txt3",beanParametriApplication.getTXT3());
      xss.setElement("txt5",beanParametriApplication.getTXT5());
      xss.setElement("txt6",beanParametriApplication.getTXT6());
      
      //Se fattura NON è pagata NON viene visualizzata la relativa label
      ReportUtils.removeRows(xss, "lblFatturaPagata", 1);
    }
  }

  /**
   * Intestazione tabella
   * @param report
   * @param tableName
   */
  public static void setIntestazioneTabella(TabularSheet report, String tableName) throws Exception
  {
    setIntestazioneTabella(report, tableName, false);
  }

  /**
   * Intestazione tabella
   * @param report
   * @param tableName
   * @param isPageOverflowProblem
   */
  public static void setIntestazioneTabella(TabularSheet report, String tableName, boolean isPageOverflowProblem) throws Exception
  {
  	DefaultTableLens dtl = new DefaultTableLens(report.getTable(tableName));
  	
    if (isPageOverflowProblem)
    {
    	dtl.setHeaderRowCount(0);
    }
    else
    {
      //Se vale true invece significa che la tabella sborda sul footer della pagina;
      //per risolvere il problema la tabella sia da designer sia da codice NON deve avere l'intestazione impostata
    }

    Font fontBold = ReportUtils.getFontBold(report, tableName);
    int iRowsHeader = dtl.getRowCount();
    int iColCount = dtl.getColCount();
    
    for (int r = 0; r < iRowsHeader; r++)
    {
    	dtl.setRowFont(r, fontBold);
      for (int c = 0; c < iColCount; c++)
      {
      	dtl.setAlignment(r, c, StyleConstants.H_CENTER);
      }
    }
    
    report.setElement(tableName, dtl);
  }
  
}