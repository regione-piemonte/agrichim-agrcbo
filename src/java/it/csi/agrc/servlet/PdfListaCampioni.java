package it.csi.agrc.servlet;

//import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import it.csi.agrc.*;
import it.csi.cuneo.*;
import it.csi.cuneo.servlet.PdfServlet;

//import java.awt.*;
//import java.util.*;
import inetsoft.report.*;
//import inetsoft.report.internal.*;
import inetsoft.report.painter.*;
import inetsoft.report.lens.*;
/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfListaCampioni extends PdfServletAgrc
{
  private static final long serialVersionUID = 7908302359504137990L;

  HttpSession session;
  ServletContext context;
  Autenticazione aut;
  Object dataSource;

  public PdfListaCampioni()
  {
    this.setOutputName("listaCampioni.pdf");
    this.setTemplateName("pdfListaCampioni.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
                           TabularSheet xss)
      throws Exception
  {
    // Questo PDF va generato solo se è stato effettuato con successo il login
    session = request.getSession();
    aut = (Autenticazione)session.getAttribute("aut");
    controllaAut(aut);

    //CuneoLogger.debug(this, "PdfListaCampioni.stampaPdf() - Inizio creazione pdf");
    ImagePainter logoRegione = new ImagePainter(this.getImage("logoRegione.gif"));
    xss.setElement("imgLogoRegione",logoRegione);

    String idRichiesta=request.getParameter("idRichiestaPDF");
    String codLabConsegna=request.getParameter("codLabConsegna");

    context=session.getServletContext();
    if (Utili.POOLMAN)
      dataSource=context.getAttribute("poolBean");
    else
      dataSource=context.getAttribute("dataSourceBean");

    //Footer
    ReportUtils.setFooter(xss);

    EtichettaCampioni etichettaCampioniPDF=new EtichettaCampioni();
    etichettaCampioniPDF.setDataSource(dataSource);
    etichettaCampioniPDF.setAut(aut);
    etichettaCampioniPDF.caricoListaCampioni(idRichiesta);


    Laboratorio laboratorioConsegna = new Laboratorio(dataSource, aut);


    /***************************************************************
     * Header comune a tutte le pagine
     */

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
    
    //xss.setElement("tbxAssessorato",beanParametriApplication.getAssessorato()+"\n"+beanParametriApplication.getDirezione());
    xss.setElement("tbxSettore",beanParametriApplication.getSettore()+"\n"+beanParametriApplication.getLabAgr());


    String etichetta=null;
    laboratorioConsegna.select(null,beanParametriApplication.getPartitaIVALab());

    etichetta="SEDE: "+laboratorioConsegna.getIndirizzoPdf();
    laboratorioConsegna.select(codLabConsegna,beanParametriApplication.getPartitaIVALab());
    etichetta+="\nLABORATORIO DI CONSEGNA\n"+laboratorioConsegna.getIndirizzoPdf();
   // etichetta+="\n"+beanParametriApplication.getLabConsegna2();
    if(beanParametriApplication.getLabConsegna2()!=null)
    {
      etichetta+="\n"+beanParametriApplication.getLabConsegna2();
    }
    xss.setElement("tbxIndirizzo",etichetta);


    DefaultTableLens tblRichieste=new DefaultTableLens(xss.getTable("tblRichieste"));
    int size=etichettaCampioniPDF.size();
    PdfServlet.resetTableHeader(tblRichieste, size);

    tblRichieste.setColWidth(0,55);
    tblRichieste.setColWidth(1,120);
    tblRichieste.setColWidth(2,66);
    tblRichieste.setColWidth(3,110);
    tblRichieste.setColWidth(4,70);
    tblRichieste.setColWidth(5,70);

    EtichettaCampione e;
    etichetta=null;
    String note=null;
    for (int i=1;i<=size;i++)
    {
      e=(EtichettaCampione)etichettaCampioniPDF.get(i-1);
      tblRichieste.setObject(i,0,e.getIdRichiesta()+" ");
      tblRichieste.setObject(i,1,e.getDescMateriale()+" ");
      tblRichieste.setObject(i,2,(e.getNumeroCampione()!=null?e.getNumeroCampione():"")+"/"+(e.getAnnoCampione()!=null?e.getAnnoCampione():"")+" ");
      etichetta=e.getDescrizioneEtichetta();
      if (etichetta==null) etichetta="";
      tblRichieste.setObject(i,3,etichetta+" ");
      tblRichieste.setObject(i,4,e.getProprietario()+" ");
      tblRichieste.setObject(i,5,e.getRichiedente()+" ");
      note=e.getNote();
    }
    xss.setElement("tblRichieste", tblRichieste);
    //Style
    ReportUtils.formatTableHeaderOneRow(xss, "tblRichieste", true);

    DefaultTableLens tblNote=new DefaultTableLens(xss.getTable("tblNote"));
    if (note!=null)
      tblNote.setObject(0,1,note);
    xss.setElement("tblNote", tblNote);
    //Style
    ReportUtils.formatTableColumLeftRight(xss, "tblNote", false);    

    session.removeAttribute("etichettaCampioniPDF");
  }
}