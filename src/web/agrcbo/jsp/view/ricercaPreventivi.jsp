<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/ricercaPreventivi.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="preventivi"
     scope="page"
     class="it.csi.agrc.Preventivi">
    <%
    preventivi.setDataSource(dataSource);
    preventivi.setAut(aut);
    preventivi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>


<%
  /**
   * select(null) nel senso che cerca tutto
   */
   preventivi.select(null);
   int size=preventivi.getPreventivi().size();

   if ( size>0 ) {
     templ.newBlock("elencoPreventivi");
     templ.set("elencoPreventivi.num",""+size);
     
      Preventivi preventivo;
      for(int i=0;i<size;i++) {
        templ.newBlock("elencoPreventiviBody");
        preventivo=preventivi.getPreventivi().get(i);
        if (i==0) {
          templ.set("elencoPreventivi.elencoPreventivoBody.checked","checked");
        }
        if(preventivi.checkUtilizzoPreventivo(preventivo.getIdPreventivo())) 
        	templ.set("elencoPreventivi.elencoPreventivoBody.checkUtilizzo","true");
        else
        	templ.set("elencoPreventivi.elencoPreventivoBody.checkUtilizzo","false");
        templ.set("elencoPreventivi.elencoPreventivoBody.id_preventivo",preventivo.getIdPreventivo().toString());
        templ.set("elencoPreventivi.elencoPreventivoBody.numero_preventivo",preventivo.getNumero_preventivo());
        templ.set("elencoPreventivi.elencoPreventivoBody.codice_fiscale",preventivo.getCodice_fiscale());
        templ.set("elencoPreventivi.elencoPreventivoBody.importo",Utili.valuta(preventivo.getImporto()));
        templ.set("elencoPreventivi.elencoPreventivoBody.note_aggiuntive",preventivo.getNote_aggiuntive());
      }
  } else {
     templ.newBlock("elencoPreventiviNo");
  }

%>


<%= templ.text() %>


