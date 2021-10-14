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
import inetsoft.report.lens.*;
import inetsoft.report.io.*;
//import inetsoft.report.lens.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfAnalisiRichieste extends PdfServletAgrc
{
  private static final long serialVersionUID = 6417989477232164174L;

  HttpSession session;
  ServletContext context;
  Autenticazione aut;
  Object dataSource;

  public PdfAnalisiRichieste()
  {
    this.setOutputName("analisiRichieste.pdf");
    this.setTemplateName("pdfAnalisiRichieste.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
  		TabularSheet xss)
      throws Exception
  {
    // Questo PDF va generato solo se è stato effettuato con successo il login
    session = request.getSession();
    aut = (Autenticazione)session.getAttribute("aut");
    controllaAut(aut);

    CuneoLogger.debug(this, "PdfRisultatoAnalisi.stampaPdf() - Inizio creazione pdf");
    ImagePainter logoRegione = new ImagePainter(this.getImage("logoRegione.gif"));
    /*ImagePainter logoRegione = new ImagePainter(aut.getFirma());
    CuneoLogger.debug(this, "Larghezza firma (pixel)="+logoRegione.getPreferredSize().width);
    CuneoLogger.debug(this, "Altezza firma (pixel)="+logoRegione.getPreferredSize().height);*/
    xss.setElement("imgLogoRegione",logoRegione);

    String idRichiesta=request.getParameter("idRichiestaPDF");

    context=session.getServletContext();
    if (Utili.POOLMAN)
      dataSource=context.getAttribute("poolBean");
    else
      dataSource=context.getAttribute("dataSourceBean");

    //Footer
    ReportUtils.setFooter(xss);

    //Impostazione dei dati relativi alla testa del PDF: sono dati che si trovano
    //all'interno della tabella parametro e vengono precaricati all'avvio dell'applicativo
    // nel bean BeanParametri
    BeanParametri beanParametriApplication=(BeanParametri)context.getAttribute("beanParametriApplication");
    
    
    String direzione = "";
    
    if(beanParametriApplication.getDirezione()!=null)
    {
    	direzione = beanParametriApplication.getDirezione();
    }
    
    xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+direzione);
  

   // xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());
    xss.setElement("tbxSettore",beanParametriApplication.getSettore()+"\n"+beanParametriApplication.getLabAgr());


    //BeanAnalisi beanAnalisi = (BeanAnalisi) context.getAttribute("beanAnalisi");
    //java.util.Hashtable analisiHash=beanAnalisi.getAnalisi();

    EtichettaCampione etichettaCampionePDF=new EtichettaCampione();
    etichettaCampionePDF.setDataSource(dataSource);
    etichettaCampionePDF.setAut(aut);

    long idRichieste[]=Utili.longIdTokenize(idRichiesta,",");

    if (idRichieste.length>0)
    {
      etichettaCampionePDF.select(idRichieste[0]+"");

      Laboratorio laboratorioAnalisi = new Laboratorio(dataSource, aut);

      String etichetta=null;
      laboratorioAnalisi.select(null,beanParametriApplication.getPartitaIVALab());

      etichetta="SEDE: "+laboratorioAnalisi.getIndirizzoPdf();
      laboratorioAnalisi.select(etichettaCampionePDF.getCodLabAnalisi(),beanParametriApplication.getPartitaIVALab());
      etichetta+="\nLABORATORIO DI ANALISI\n"+laboratorioAnalisi.getIndirizzoPdf();
      
      if(beanParametriApplication.getLabConsegna2()!=null)
      {
        etichetta+="\n"+beanParametriApplication.getLabConsegna2();
      }
     // etichetta+="\n"+beanParametriApplication.getLabConsegna2();
      xss.setElement("tbxIndirizzo",etichetta);
    }

    DefaultTableLens tblAnalisi=new DefaultTableLens(xss.getTable("tblAnalisi"));

    PdfServlet.resetTableHeader(tblAnalisi, idRichieste.length);   

    Analisi analisi=new Analisi();

    String codice,note,data,numero,comune,descMateriale=null,specie;
    java.util.Enumeration enumAnalisi;
    analisi.setDataSource(dataSource);
    analisi.setAut(aut);
    for (int i=1;i<=idRichieste.length;i++)
    {
      etichettaCampionePDF.setIdRichiesta(idRichieste[i-1]+"");
      etichettaCampionePDF.selectPerPDFAnalisiRichieste();

      data=etichettaCampionePDF.getDataRicezione();

      numero=etichettaCampionePDF.getNumeroCampione();
      comune=etichettaCampionePDF.getDescComune();
      descMateriale=etichettaCampionePDF.getDescMateriale();
      specie=etichettaCampionePDF.getDescSpecieColtura();
      if (data!=null) tblAnalisi.setObject(i,0,data);
      if (numero!=null) tblAnalisi.setObject(i,1,numero);
      if (comune!=null) tblAnalisi.setObject(i,2,comune);
      if (specie!=null) tblAnalisi.setObject(i,3,specie);
      analisi.setIdRichiesta(idRichieste[i-1]);
      analisi.select();
      enumAnalisi=analisi.getCodiciAnalisi().elements();

      // Patch 19/10/2004
      // fisso la larghezza del campo "note"
      // La tabella è larga 11,69 - 0,59 - 0,59 = 10,51 pollici
      // cioè 10,51 * 72 = 756,72 = 756 pixel
      // La tabella ha 25 colonne, numerate da 0 a 24
      // La colonna del campo "note" è dunque la numero 24
      // Fissiamo la larghezza del campo ad 1 pollice = 72 pixel
      tblAnalisi.setColWidth(24,72);

      while(enumAnalisi.hasMoreElements())
      {
        codice=(String)enumAnalisi.nextElement();

        if (it.csi.agrc.Analisi.ANA_PACCHETTO_MICROELEMENTI.equals(codice))
        {
          if (!etichettaCampionePDF.getCodiceMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
          {
            tblAnalisi.setObject(i,22,"X");
          }
          tblAnalisi.setObject(i,18,"X");
          tblAnalisi.setObject(i,21,"X");
          tblAnalisi.setObject(i,20,"X");
          tblAnalisi.setObject(i,19,"X");
        }
        if (it.csi.agrc.Analisi.ANA_PACCHETTO_COMP_SCAMBIO.equals(codice))
        {
          tblAnalisi.setObject(i,10,"X");
          tblAnalisi.setObject(i,6,"X");
          tblAnalisi.setObject(i,7,"X");
          tblAnalisi.setObject(i,5,"X");
        }
        if (it.csi.agrc.Analisi.ANA_PACCHETTO_TIPO.equals(codice))
        {
          if (etichettaCampionePDF.getCodiceMateriale().equals(it.csi.agrc.Analisi.MAT_TERRENO))
          {
            tblAnalisi.setObject(i,4,"X");
            tblAnalisi.setObject(i,11,"X");
            tblAnalisi.setObject(i,10,"X");
            tblAnalisi.setObject(i,12,"X");
          }
          tblAnalisi.setObject(i,6,"X");
          tblAnalisi.setObject(i,7,"X");
          tblAnalisi.setObject(i,9,"X");
          tblAnalisi.setObject(i,5,"X");
          tblAnalisi.setObject(i,8,"X");
        }


        if (it.csi.agrc.Analisi.ANA_PH.equals(codice))
        {
          tblAnalisi.setObject(i,4,"X");
        }
        if (it.csi.agrc.Analisi.ANA_CALCIO.equals(codice))
        {
          tblAnalisi.setObject(i,5,"X");
        }
        if (it.csi.agrc.Analisi.ANA_MAGNESIO.equals(codice))
        {
          tblAnalisi.setObject(i,6,"X");
        }
        if (it.csi.agrc.Analisi.ANA_POTASSIO.equals(codice))
        {
          tblAnalisi.setObject(i,7,"X");
        }
        if (it.csi.agrc.Analisi.ANA_AZOTO.equals(codice))
        {
          tblAnalisi.setObject(i,8,"X");
        }
        if (it.csi.agrc.Analisi.ANA_FOSFORO.equals(codice))
        {
          tblAnalisi.setObject(i,9,"X");
        }
        if (it.csi.agrc.Analisi.ANA_CAPACITASCAMBIOCATIONICO.equals(codice))
        {
          tblAnalisi.setObject(i,10,"X");
        }
        if (it.csi.agrc.Analisi.ANA_SOSTANZAORGANICA.equals(codice))
        {
          tblAnalisi.setObject(i,11,"X");
        }
        if (it.csi.agrc.Analisi.ANA_CALCARETOTALE.equals(codice))
        {
          tblAnalisi.setObject(i,12,"X");
        }
        if (it.csi.agrc.Analisi.ANA_CALCAREATTIVO.equals(codice))
        {
          tblAnalisi.setObject(i,13,"X");
        }
        if (it.csi.agrc.Analisi.ANA_STANDARD.equals(codice))
        {
          tblAnalisi.setObject(i,14,"X");
        }
        if (it.csi.agrc.Analisi.ANA_A4FRAZIONI.equals(codice))
        {
          tblAnalisi.setObject(i,15,"X");
        }
        if (it.csi.agrc.Analisi.ANA_A5FRAZIONI.equals(codice))
        {
          tblAnalisi.setObject(i,16,"X");
        }
        if (it.csi.agrc.Analisi.ANA_SALINITA.equals(codice))
        {
          tblAnalisi.setObject(i,17,"X");
        }
        if (it.csi.agrc.Analisi.ANA_FERRO.equals(codice))
        {
          tblAnalisi.setObject(i,18,"X");
        }
        if (it.csi.agrc.Analisi.ANA_RAME.equals(codice))
        {
          tblAnalisi.setObject(i,19,"X");
        }
        if (it.csi.agrc.Analisi.ANA_ZINCO.equals(codice))
        {
          tblAnalisi.setObject(i,20,"X");
        }
        if (it.csi.agrc.Analisi.ANA_MANGANESE.equals(codice))
        {
          tblAnalisi.setObject(i,21,"X");
        }
        if (it.csi.agrc.Analisi.ANA_BORO.equals(codice))
        {
          tblAnalisi.setObject(i,22,"X");
        }
        if (it.csi.agrc.Analisi.ANA_UMIDITA.equals(codice))
        {
          tblAnalisi.setObject(i,23,"X");
        }
        note=analisi.getNote();
        if (note==null) note="";
        tblAnalisi.setObject(i,24,note);
      }
    }
    xss.setElement("tblAnalisi", tblAnalisi);

    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "tblAnalisi", true);  

    DefaultTextLens txtMateriale=new  DefaultTextLens();
    if (descMateriale!=null) txtMateriale.setText(descMateriale);
    xss.setElement("txtMateriale", txtMateriale);


  }

  protected void creaPdf(HttpServletRequest request,
                             HttpServletResponse response,
                             OutputStream outStampa)
      throws Exception
  {
    try
    {

      //InputStream fis = getStream(this.getTemplateName());
      //ReportSheet xss = (ReportSheet)Builder.getBuilder(Builder.TEMPLATE, fis).read("./");

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

}
