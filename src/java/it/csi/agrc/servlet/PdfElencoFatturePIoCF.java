package it.csi.agrc.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import it.csi.agrc.*;
import it.csi.cuneo.*;
import it.csi.cuneo.servlet.PdfServlet;

import java.awt.*;
//import java.util.*;
import inetsoft.report.*;
//import inetsoft.report.internal.*;
import inetsoft.report.painter.*;
import inetsoft.report.io.*;
import inetsoft.report.lens.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piant�, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfElencoFatturePIoCF extends PdfServletAgrc
{
  private static final long serialVersionUID = 756013858258013772L;
  
  HttpSession session;
  ServletContext context;
  Autenticazione aut;
  Object dataSource;

  public PdfElencoFatturePIoCF()
  {
    this.setOutputName("elencoFatture.pdf");
    this.setTemplateName("pdfElencoFatturePIoCF.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
                           TabularSheet xss)
      throws Exception
  {

    BeanRicerca beanRicerca;

    // Questo PDF va generato solo se � stato effettuato con successo il login
    session = request.getSession();
    aut = (Autenticazione)session.getAttribute("aut");
    controllaAut(aut);

    beanRicerca = (BeanRicerca)session.getAttribute("beanRicerca");

    CuneoLogger.debug(this, "pdfElencoFatture.stampaPdf() - Inizio creazione pdf");
    ImagePainter logoRegione = new ImagePainter(this.getImage("logoRegione.gif"));
    xss.setElement("imgLogoRegione",logoRegione);

    context=session.getServletContext();
    if (Utili.POOLMAN)
      dataSource=context.getAttribute("poolBean");
    else
      dataSource=context.getAttribute("dataSourceBean");

    //Footer
    ReportUtils.setFooter(xss);

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

    //xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());
    xss.setElement("tbxSettore",beanParametriApplication.getSettore()+"\n"+beanParametriApplication.getLabAgr());



    /**
     * Valorizzo la tabella che visualizza i criteri di ricerca adottai
     * */
    int numRigheTblCriteri=0;
    if (beanRicerca.getDataDaGiorno()!=null && !"".equals(beanRicerca.getDataDaGiorno())) numRigheTblCriteri++;
    if (beanRicerca.getNumeroFatturaA()!=null && !"".equals(beanRicerca.getNumeroFatturaA())) numRigheTblCriteri++;

    DefaultTableLens tblCriteri=new DefaultTableLens(xss.getTable("tblCriteri"));
    if (numRigheTblCriteri>0)
    {
      tblCriteri.setRowCount(numRigheTblCriteri);

      for (int i=0;i<numRigheTblCriteri;i++)
      {
        tblCriteri.setFont (i,0,new Font("SansSerif",Font.BOLD,10));
        tblCriteri.setFont(i,1,new Font("SansSerif",Font.BOLD,10));
        tblCriteri.setFont(i,2,new Font("SansSerif",Font.BOLD,10));
        tblCriteri.setFont(i,3,new Font("SansSerif",Font.BOLD,10));
        tblCriteri.setColBorder(i,0,StyleConstants.NO_BORDER);
        tblCriteri.setColBorder(i,1,StyleConstants.NO_BORDER);
        tblCriteri.setColBorder(i,2,StyleConstants.NO_BORDER);
      }
      tblCriteri.setColWidth(0,260);
      tblCriteri.setColWidth(1,90);
      tblCriteri.setColWidth(2,30);
      tblCriteri.setColWidth(3,100);

      int riga=0;

      if (beanRicerca.getDataDaGiorno()!=null &&
          !"".equals(beanRicerca.getDataDaGiorno()))
      {
        tblCriteri.setObject(riga,0," Elenco fatture con data fattura compresa tra ");
        tblCriteri.setObject(riga,1," "+beanRicerca.getDataDaGiorno()+"/"
                             +beanRicerca.getDataDaMese()+"/"+beanRicerca.getDataDaAnno()+" ");
        tblCriteri.setObject(riga,2," e");
        tblCriteri.setObject(riga,3," "+beanRicerca.getDataAGiorno()+"/"
                             +beanRicerca.getDataAMese()+"/"+beanRicerca.getDataAAnno()+" ");
        riga++;
      }
      if (beanRicerca.getNumeroFatturaA()!=null &&
          !"".equals(beanRicerca.getNumeroFatturaA()))
      {
        tblCriteri.setObject(riga,0," Elenco fatture con anno/numero compresi tra ");
        tblCriteri.setObject(riga,1," "+beanRicerca.getAnnoFattura()+"/"
                             +beanRicerca.getNumeroFattura());
        tblCriteri.setObject(riga,2," e");
        tblCriteri.setObject(riga,3," "+beanRicerca.getAnnoFatturaA()+"/"
                             +beanRicerca.getNumeroFatturaA());
      }

      xss.setElement("tblCriteri", tblCriteri);
    }
    else
      xss.setVisible("tblCriteri",false);

   /**
     * Leggo l'elenco delle fatture da visualizzare nel PDF
     * */
    Fatture fatture=new Fatture();
    fatture.setAut(aut);
    fatture.setDataSource(dataSource);
    fatture.elencoFatture();
    int size=fatture.size();
    DefaultTableLens tblFatture=new DefaultTableLens(xss.getTable("tblFatture"));

    PdfServlet.resetTableHeader(tblFatture, size+3);

    /**
     * Imposto la riga di intestazione
     * */
    tblFatture.setAlignment(0,0,StyleConstants.H_CENTER);
    tblFatture.setAlignment(0,1,StyleConstants.H_CENTER);
    tblFatture.setAlignment(0,2,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(0,3,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(0,4,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(0,5,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(0,6,StyleConstants.H_CENTER);
    tblFatture.setAlignment(0,7,StyleConstants.H_CENTER);

    for(int i=1;i<=size;i++)
      {
        tblFatture.setAlignment(i,0,StyleConstants.H_CENTER);
        tblFatture.setAlignment(i,1,StyleConstants.H_CENTER);
        tblFatture.setAlignment(i,2,StyleConstants.H_RIGHT);
        tblFatture.setAlignment(i,3,StyleConstants.H_RIGHT);
        tblFatture.setAlignment(i,4,StyleConstants.H_RIGHT);
        tblFatture.setAlignment(i,5,StyleConstants.H_RIGHT);
        tblFatture.setAlignment(i,6,StyleConstants.H_CENTER);
        tblFatture.setAlignment(i,7,StyleConstants.H_CENTER);
      }

     /**
     * Imposto l'ultima riga
     * */
    tblFatture.setAlignment(size+2,0,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(size+2,1,StyleConstants.H_LEFT);
    tblFatture.setAlignment(size+2,2,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(size+2,3,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(size+2,4,StyleConstants.H_RIGHT);
    tblFatture.setAlignment(size+2,5,StyleConstants.H_RIGHT);

    if ( size>0 )
    {
      /*tblFatture.setColWidth(0,65);
      tblFatture.setColWidth(1,55);
      tblFatture.setColWidth(2,85);
      tblFatture.setColWidth(3,75);
      tblFatture.setColWidth(4,85);
      tblFatture.setColWidth(5,65);
      tblFatture.setColWidth(6,65);
      tblFatture.setColWidth(7,65);*/
      Fattura f=null;
      String data,numero,pagata,statoFattura;
      double totale,imponibile,IVA,costoSpedizione;
      double totaleTOT=0,imponibileTOT=0,IVATOT=0,costoSpedizioneTOT=0;
      for(int i=0;i<size;i++)
      {
        f=fatture.get(i);
        data=f.getDataFattura();
        numero=f.getNumeroFattura();

        try
        {
          imponibile=Double.parseDouble(f.getImponibile());
        }
        catch(Exception e)
        {
          imponibile=0;
        }
        try
        {
          IVA=Double.parseDouble(f.getIVA());
        }
        catch(Exception e)
        {
          IVA=0;
        }
        try
        {
          costoSpedizione=Double.parseDouble(f.getImportoSpedizione());
        }
        catch(Exception e)
        {
          costoSpedizione=0;
        }

        pagata=f.getPagata();
        statoFattura=f.getAnnullata();

        if (data==null) data="";
        if (numero==null) numero="";
        if (pagata==null) pagata="N";
        if ("S".equals(pagata)) pagata="Si";
        if ("N".equals(pagata)) pagata="No";

        totale=imponibile+IVA+costoSpedizione;

        if ("S".equals(statoFattura)) statoFattura="ANNULLATA";
        else
        {
          statoFattura="";
          imponibileTOT+=imponibile;
          IVATOT+=IVA;
          costoSpedizioneTOT+=costoSpedizione;
          totaleTOT+=totale;
        }

        tblFatture.setObject(i+1,0,data);
        tblFatture.setObject(i+1,1,numero);
        tblFatture.setObject(i+1,2,Utili.nf2.format(imponibile));
        tblFatture.setObject(i+1,3,Utili.nf2.format(IVA));
        tblFatture.setObject(i+1,4,Utili.nf2.format(costoSpedizione));
        tblFatture.setObject(i+1,5,Utili.nf2.format(totale));
        tblFatture.setObject(i+1,6,pagata);
        tblFatture.setObject(i+1,7,statoFattura);
      }

      String ragioneSociale=f.getRagioneSociale();
      String indirizzo=f.getIndirizzo();
      String cap=f.getCap();
      String comune=f.getComune();
      String provincia=f.getSiglaProvincia();

      if (ragioneSociale==null) ragioneSociale="";
      if (indirizzo==null) indirizzo="";
      if (cap==null) cap="";
      if (comune==null) comune="";
      if (provincia==null) provincia="";

      /**
      * Inserisco i dati dell'intestatario della fattua
      * */

      xss.setElement("txtBoxRagSociale",ragioneSociale);
      xss.setElement("txtBoxIndirizzo",indirizzo);
      xss.setElement("txtBoxComune",cap+"    "+comune+"    "+provincia);
      xss.setElement("txtBoxPIVA","P.IVA / C.F: "+beanRicerca.getCodFisPIVA());


      tblFatture.setObject(size+1,3,"  ");

      tblFatture.setObject(size+2,0,"Totale (escl. ");
      tblFatture.setObject(size+2,1,"Annullate)");

      tblFatture.setFont (size+2,2,new Font("SansSerif",Font.BOLD,10));
      tblFatture.setFont (size+2,3,new Font("SansSerif",Font.BOLD,10));
      tblFatture.setFont (size+2,4,new Font("SansSerif",Font.BOLD,10));
      tblFatture.setFont (size+2,5,new Font("SansSerif",Font.BOLD,10));

      tblFatture.setObject(size+2,2,Utili.nf2.format(imponibileTOT));
      tblFatture.setObject(size+2,3,Utili.nf2.format(IVATOT));
      tblFatture.setObject(size+2,4,Utili.nf2.format(costoSpedizioneTOT));
      tblFatture.setObject(size+2,5,Utili.nf2.format(totaleTOT));

      setNoBorderWithTitle(tblFatture);
      xss.setElement("tblFatture", tblFatture);
      xss.setVisible("txtBoxNessunaFattura",false);
      xss.setVisible("Newline81",false);
    }
    else
    {
      xss.setElement("txtBoxDatiIntestatario", "");
      xss.setElement("txtBoxRagSociale", "");
      xss.setElement("txtBoxIndirizzo", "");
      xss.setElement("txtBoxComune", "");
      xss.setElement("txtBoxPIVA", "");
      xss.setVisible("tblFatture",false);
    }
  }

  protected void creaPdf(HttpServletRequest request,
                             HttpServletResponse response,
                             OutputStream outStampa)
      throws Exception
  {
    try
    {

      //InputStream fis = getStream(this.getTemplateName());
      //XStyleSheet xss = (XStyleSheet)Builder.getBuilder(Builder.TEMPLATE, fis).read("./");

      //Lettura template con la gestione della cache
      TabularSheet xss = (TabularSheet) createReport(this.getTemplateName()); 

      stampaPdf(request, xss);

      PDFPrinter pdf = new PDFPrinter(outStampa);
      pdf.setPageSize(11.692913385826771653543307086614,8.2677165354330708661417322834646);
      PrintJob pj = pdf.getPrintJob();
      xss.print(pj);
      pdf.close();
    }
    catch (Exception e)
    {
      //e.printStackTrace();
      throw e;
    }
  }


  protected final void setNoBorder(DefaultTableLens tblLens)
  {
    tblLens.setColBorder(StyleConstants.NO_BORDER);
    tblLens.setRowBorder(StyleConstants.NO_BORDER);
    int iMax=tblLens.getRowCount();
    int jMax=tblLens.getColCount();
    for (int i=0; i<iMax; i++)
      for (int j=0; j<jMax; j++) {
          tblLens.setColBorder(i, j, StyleConstants.NO_BORDER);
          tblLens.setRowBorder(i, j, StyleConstants.NO_BORDER);
      }
  }

  protected final void setNoBorderWithTitle(DefaultTableLens tblLens) {
    tblLens.setColBorder(StyleConstants.NO_BORDER);
    tblLens.setRowBorder(StyleConstants.NO_BORDER);
    int iMax=tblLens.getRowCount();
    int jMax=tblLens.getColCount();
    for (int i=0; i<iMax; i++)
      for (int j=0; j<jMax; j++) {
      	if (j != (jMax - 1))
      	{
      		tblLens.setColBorder(i, j, StyleConstants.NO_BORDER);
      	}
        if (i!=0) {
          tblLens.setRowBorder(i, j, StyleConstants.NO_BORDER);
        }
      }
  }

}
