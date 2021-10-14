<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
    %>
</jsp:useBean>

<%
  String idRichiesta=request.getParameter("idRichiesta");
  String indirizzoEmail=request.getParameter("indirizzoEmail");
  String pagamento=request.getParameter("pagamento");
  String pagamento_hidden=request.getParameter("pagamento_h");
  if(pagamento==null||pagamento.equals(""))
	  pagamento = pagamento_hidden;
  String labAnalisi=request.getParameter("labAnalisi");
  String codLabConsegna=request.getParameter("codLabConsegna");
  String scarto=request.getParameter("scarto");
  String note=request.getParameter("note");
  String data_incasso = request.getParameter("data_incasso");
  String data_incasso_hidden=request.getParameter("data_incasso_h");
  if(data_incasso==null||data_incasso.equals(""))
      data_incasso = data_incasso_hidden;
  if (indirizzoEmail==null) indirizzoEmail="";
  boolean mail=false;
  if ("S".equals(request.getParameter("mail"))) mail=true;
  if (mail) mail=Utili.controllaMail(indirizzoEmail,50);

  /**
  *  Gestisco il passaggio dei campioni dallo stato di analisi richiesta
  * allo stato di campione di laboratorio o l'eventuale non accettazione
  */
  etichettaCampione.accettazioneScartoAnalRic(idRichiesta,pagamento,labAnalisi,scarto,note, data_incasso);

  /**
   * carico il bean con i dati che mi serviranno sia per l'ìnvio della mail
   * sia per la creazione del pdf. Dato che il pdf lo creo nel servlet, questo
   * bean deve essere di request
   * */



  if (mail)
  {
    /**
     * La parte di codice seguente è utilizzata per preparare e spedire l'email
     **/
    if (note==null) note="";
    etichettaCampioni.caricoListaCampioni(idRichiesta,note);
    it.csi.cuneo.Mail eMail=new it.csi.cuneo.Mail();
    eMail.setEMailSender(beanParametriApplication.getMailMittenteAgrichim());
    String ind[]=new String[1];
    ind[0]=indirizzoEmail;
    eMail.setEMailReceiver(ind);
    eMail.setHost(beanParametriApplication.getHostServerPosta());
    eMail.preparaMail(etichettaCampioni,scarto,note);
    CuneoLogger.debug(this, "\nListaCampioni.jsp - invio mail: "+eMail.inviaMail());
  }
  if (note==null) note="";

  if ("B".equals(scarto))
    Utili.forward(request, response, "/jsp/controller/analisiRichieste.jsp");
  else
  {
%>

<html>
<head>
<script src="../layout/js/gestione.js"></script>
<script>
pop('../report/PdfListaCampioni.pdf?idRichiestaPDF=<%= idRichiesta%>&codLabConsegna=<%= codLabConsegna%>',647,480,'pdf');
window.location= '../controller/campioniLaboratorio.jsp';
</script>
</head>
<body>
</body>
</html>

<%
 }
%>


