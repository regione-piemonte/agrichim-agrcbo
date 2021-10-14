package it.csi.agrc.servlet;

//import java.io.*;
import javax.servlet.http.*;
//import it.csi.agrc.*;

import inetsoft.report.*;
import inetsoft.report.painter.*;
import it.csi.cuneo.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class PdfRisultatoAnalisi extends PdfServletAgrc
{
  private static final long serialVersionUID = -1055150183602246453L;

  public PdfRisultatoAnalisi()
  {
    this.setOutputName("risultatoAnalisi.pdf");
    this.setTemplateName("pdfRisultatoAnalisi.srt");
  }

  protected void stampaPdf(HttpServletRequest request,
                           TabularSheet xss)
      throws Exception
  {
    CuneoLogger.debug(this, "PdfRisultatoAnalisi.stampaPdf() - Inizio creazione pdf");
    ImagePainter logoRegione = new ImagePainter(this.getImage("logoRegione.gif"));
    xss.setElement("imgLogoRegione",logoRegione);
  }
}