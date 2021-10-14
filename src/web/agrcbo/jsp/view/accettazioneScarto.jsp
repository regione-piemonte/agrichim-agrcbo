<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/accettazioneScarto.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanTipoCampione"
     scope="application"
     class="it.csi.agrc.BeanTipoCampione"/>

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
   String idRichiesta=request.getParameter("idRichiestaSearch");
   aut.setIdRichiestaChecked(idRichiesta);
   String email=etichettaCampioni.select(idRichiesta);
   if (email==null)
   {
     templ.bset("controlloEmail","true");
   }
   else
   {
     templ.bset("controlloEmail","false");
     templ.bset("indirizzoEmail",email);
   }
   int size=etichettaCampioni.size();
   String descLabConsegna=null;
   String statoAnomalia=null;
   String pagamento=null;
   String note=null;
   String codLabAnalisi=null;
   String codLabConsegna=null;
   String iuv=null;
   if ( size>0 )
   {
    EtichettaCampione e;
    String materiale=null,etichetta=null;
    for(int i=0;i<size;i++)
    {
      /**
         * Le due righe seguenti servono per gestire i checkBox
         **/
      templ.newBlock("elencoCampioneBody");
      e=etichettaCampioni.get(i);
      materiale = e.getDescMateriale();
      etichetta=e.getDescrizioneEtichetta();
      templ.set("elencoCampioneBody.idRichiesta",e.getIdRichiesta());
      templ.set("elencoCampioneBody.descMateriale",materiale);
      templ.set("elencoCampioneBody.descrizioneEtichetta",etichetta);
      descLabConsegna=e.getDescLabConsegna();
      statoAnomalia=e.getStatoAnomalia();
      pagamento=e.getPagamento();
      note=e.getNote();
      iuv=e.getIuv();
      codLabAnalisi=e.getCodLabAnalisi();
      codLabConsegna=e.getCodLabConsegna();
      if(iuv!=null && !iuv.equals("")){
    	  if("S".equals(pagamento)){
    		  templ.bset("iuv","Pagata con IUV: "+e.getIuv());
              templ.bset("disabilitato","disabled");
              templ.bset("data_incasso", e.getData_incasso());
    	  }else if ("N".equals(pagamento)){
    		  templ.bset("iuv","Da pagare con IUV: "+e.getIuv());
    	  }
      }   
    }
    if (codLabConsegna==null) codLabConsegna="";
    if (descLabConsegna==null) descLabConsegna="";
    templ.bset("descLabConsegna",descLabConsegna);
    templ.bset("codLabConsegna",codLabConsegna);
  }
  if ("G".equals(pagamento))
	   templ.bset("checkedGratuita","checked");
  else if ("S".equals(pagamento))
	   templ.bset("checkedPagata","checked");
  else
	   templ.bset("checkedDaPagare","checked");
  
  templ.bset("ckPagamento",pagamento);
  
  if (size==1)
  {
    /**
    * Se è stato selezionato solo un campione devo anche caricare i dati
    * relativi allo scarto sospensione, lo stato pagamento dell'analisi e le
    * note
    */
   if (idRichiesta==null) idRichiesta=""+aut.getIdRichiestaCorrente();
   if (note!=null)  templ.bset("note",note);
   if (codLabAnalisi==null) codLabAnalisi="RPCN";
   if ("A".equals(statoAnomalia))
   {
    templ.bset("checkedAnomalo","checked");
   }
   else
   {
     if ("B".equals(statoAnomalia))
       templ.bset("checkedBloccante","checked");
     else
       templ.bset("checkedNo","checked");
   }
  }
  else
  {
    codLabAnalisi="RPCN";
    templ.bset("checkedNo","checked");
  }
  /**
    Carico i dati di tutti i laboratori per visualizzarli nella combo
   */
   String codStr[],descStr[];
   codStr=beanTipoCampione.getCodLaboratorio();
   descStr=beanTipoCampione.getDescLaboratorio();
   for(int i=0;i<codStr.length;i++)
   {
     if (codLabAnalisi.equals(codStr[i]))
       templ.set("laboratorio.selected","selected");
     else
       templ.set("laboratorio.selected","");
     templ.set("laboratorio.codice",codStr[i]);
     templ.set("laboratorio.descrizione",descStr[i]);
   }
   templ.bset("idRichiesta",idRichiesta);
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>

