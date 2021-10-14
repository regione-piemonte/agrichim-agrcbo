<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*, java.util.*" isThreadSafe="true" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
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

<%
  /**
   * Questi paramteri sono relativi al campione
   * */
  String richieste = request.getParameter("idRichiestaSearch");
  String richiesteIndirizzoEmail = request.getParameter("richiesteIndirizzoEmail");
  String indirizzoEmail = request.getParameter("indirizzoEmail");
  if (indirizzoEmail == null) indirizzoEmail = "";
  String pagamento = request.getParameter("pagamento");
  String pagamento_hidden=request.getParameter("pagamento_h");
  if(pagamento==null||pagamento.equals(""))
      pagamento = pagamento_hidden;
  String data_incasso = request.getParameter("data_incasso");
  String data_incasso_hidden=request.getParameter("data_incasso_h");
  if(data_incasso==null||data_incasso.equals(""))
      data_incasso = data_incasso_hidden;
  String scarto = request.getParameter("scarto");
  String note = request.getParameter("note");
  if (note == null) note = "";

  /**
   * Questi due parametri sono relativi alla firma del referto
   * */
  String firmaReferto = request.getParameter("firmaReferto");
  String noteFirma = request.getParameter("noteFirma");
	
	//Gestione indirizzi per invio email
  String[] aIdRichiestaIndirizzoEmail = null;
  String[] aIndirizzoEmail = null;
		  
  boolean mail = false;
  if ("S".equals(request.getParameter("mail")))
  {
  	mail = true;

	  if (richiesteIndirizzoEmail != null)
	  {
	  	if (richiesteIndirizzoEmail.contains(","))
	  	{
	  		//Più richieste
	  		aIdRichiestaIndirizzoEmail = richiesteIndirizzoEmail.split(",");
	  	}
	  	else
	  	{
				//Una sola richiesta
		  	aIdRichiestaIndirizzoEmail = new String[1];
		  	aIdRichiestaIndirizzoEmail[0] = richieste;  	
	  	}
			
	    if (indirizzoEmail.contains(","))
	  	{
	  		//Più indirizzi email
	  		aIndirizzoEmail = indirizzoEmail.split(",");
	  	}
	  	else
	  	{
				//Un solo indirizzo email
		  	aIndirizzoEmail = new String[1];
		  	aIndirizzoEmail[0] = indirizzoEmail;
	  	}  	
		}

	  if (aIndirizzoEmail != null && aIndirizzoEmail.length > 0)
	  {
		  	int indiceIndirizzoEmail = 0;
		  	for (String sIndirizzoEmail : aIndirizzoEmail)
		  	{
		  		if (! Utili.controllaMail(sIndirizzoEmail, 50))
		  		{
		  			aIndirizzoEmail[indiceIndirizzoEmail] = "";
		  		}
		  		
		  		indiceIndirizzoEmail++;
		  	}
	  }
  }

  /**
  *  Gestisco il passaggio dei campioni dallo stato di Referto da emettere
  * allo stato di Referto emesso o l'eventuale non accettazione
  */
  etichettaCampione.firmaScartoRefEmettere(richieste, pagamento, scarto, note, firmaReferto, noteFirma, data_incasso);

  /**
   * carico il bean con i dati che mi serviranno sia per l'ìnvio della mail
   * */
  if (mail && ! ("B".equals(scarto)))
  {
    /**
     * La parte di codice seguente è utilizzata per preparare e spedire l'email
     **/
    it.csi.cuneo.Mail eMail = new it.csi.cuneo.Mail();
    eMail.setEMailSender(beanParametriApplication.getMailMittenteAgrichim());
    
    HashMap mapIndMail=new HashMap();
    
    int indiceIndirizzoEmail = 0;
    for (String idRichiesta : aIdRichiestaIndirizzoEmail)
    {
    	String sIndirizzoEmail = aIndirizzoEmail[indiceIndirizzoEmail];
    	if (sIndirizzoEmail != null && ! "".equals(sIndirizzoEmail))
    	{
    		StringBuffer listIdRichieste=(StringBuffer)mapIndMail.get(sIndirizzoEmail);
    		if (listIdRichieste==null)
    			listIdRichieste=new StringBuffer(idRichiesta);
    		else listIdRichieste.append(",").append(idRichiesta);
    		mapIndMail.put(sIndirizzoEmail, listIdRichieste);
    	}	
	    indiceIndirizzoEmail++;
    }
    
    Iterator iterMail=mapIndMail.entrySet().iterator();
    
    while (iterMail.hasNext())
    {
    	Map.Entry mailMap=(Map.Entry)iterMail.next();
    	String ind[] = new String[1];
    	ind[0]=(String)mailMap.getKey();
		StringBuffer listIdRichieste=(StringBuffer)mailMap.getValue();
	    eMail.setEMailReceiver(ind);
	    eMail.setHost(beanParametriApplication.getHostServerPosta());
	    eMail.preparaMail(listIdRichieste);
	    CuneoLogger.debug(this, "\firmaScarto.jsp - invio mail: " + eMail.inviaMail());
    }
    
    /*
	    for (String idRichiesta : aIdRichiestaIndirizzoEmail)
	    {
	    	String sIndirizzoEmail = aIndirizzoEmail[indiceIndirizzoEmail];
	    	if (sIndirizzoEmail != null && ! "".equals(sIndirizzoEmail))
	    	{
			    String ind[] = new String[1];
			    ind[0] = sIndirizzoEmail;
			    eMail.setEMailReceiver(ind);
			    eMail.setHost(beanParametriApplication.getHostServerPosta());
			    eMail.preparaMail(idRichiesta);
			    CuneoLogger.debug(this, "\firmaScarto.jsp - invio mail: " + eMail.inviaMail());
		    }
	    }
    */
  }

  if ("B".equals(scarto))
  {
    Utili.forward(request, response, "/jsp/controller/analisiTerminate.jsp");
  }
  else
  {
    // Quando si mandano avanti i referti, si deve restare in "referti da emettere"
    Utili.forward(request, response, "/jsp/view/analisiTerminate.jsp");
	}
%>