// Codici unicode per le lettere accentate da inserire nei PDF
// \u00EC = ì

package it.csi.agrc.servlet;

//import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import inetsoft.report.TabularSheet;
import inetsoft.report.lens.DefaultTableLens;
import it.csi.agrc.Preventivi;
import it.csi.cuneo.ReportUtils;
import it.csi.cuneo.Utili;


public class PdfAnagraficaCampioneZMA extends PdfAnagraficaCampione
{
  private static final long serialVersionUID = -8482811997199012135L;

  public PdfAnagraficaCampioneZMA()
  {
    this.setOutputName("anagraficaCampioneZMA.pdf");
    this.setTemplateName("pdfAnagraficaCampioneZMA.srt");
  }

  protected void stampaPdf(HttpServletRequest request, TabularSheet xss)
      throws Exception {
    super.stampaPdf(request, xss);
    //seleziono la tabella in pdfAnagraficaCampioneZMA.srt
    tblLens = new DefaultTableLens(xss.getTable("tblPreventivo"));
    ReportUtils.formatTableHeaderOneRow(xss, "lblPreventivo", true);
    ReportUtils.formatTableColumLeftRight(xss, "tblPreventivo", true);
    //select Preventivo
    Preventivi preventivo = new Preventivi(dataSource, aut);
    preventivo.selectPreventivo(idRichiesta);
    if(preventivo.getPreventivi()!=null && !preventivo.getPreventivi().isEmpty()) {
		tblLens.setObject(0,1,preventivo.getPreventivi().get(0).getNumero_preventivo());
		tblLens.setObject(1,1,preventivo.getPreventivi().get(0).getCodice_fiscale());
		tblLens.setObject(2,1,Utili.valuta(preventivo.getPreventivi().get(0).getImporto())+" euro");
		tblLens.setObject(3,1,preventivo.getPreventivi().get(0).getNote_aggiuntive());
		tblLens.setObject(4,1,preventivo.getPreventivi().get(0).getNote());
    }else {
    	tblLens.setObject(0,1,"");
		tblLens.setObject(1,1,"");
		tblLens.setObject(2,1,"");
		tblLens.setObject(3,1,"");
		tblLens.setObject(4,1,"");
    }
    xss.setElement("tblPreventivo", tblLens);
  }
}