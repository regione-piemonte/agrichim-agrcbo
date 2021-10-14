<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/erbaceeDati.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="coltura"
     scope="page"
     class="it.csi.agrc.Coltura">
    <%
      coltura.setDataSource(dataSource);
      coltura.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="campioneVegetaliErbacee"
     scope="page"
     class="it.csi.agrc.CampioneVegetaliErbacee">
    <%
      campioneVegetaliErbacee.setDataSource(dataSource);
      campioneVegetaliErbacee.setAut(aut);
      campioneVegetaliErbacee.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>


<%
  /**
   * Se modifica non � valorizzato significa che sono arrivato a questa pagina
   * dal menu. Se modifica � uguale a si significa che ho appena modificato i
   * dati dell'etichetta. Se invece modifica � uguale a no significa che
   * ho tentato di modificare i dati ma qualcosa non � andato a buon fine
   * */
   String modifica=request.getParameter("modifica");
   if ("si".equals(modifica))
        templ.newBlock("modifica");
  /**
   * Poi devo vedere se sono qui perch� c'e stato un errore. In caso
   * contrario controllo se il parametro classeColtura � valorizzato.
   * Se � valorizzato vuol dire che sono in questa pagina perch� � stato
   * modificato il valore della combo relativa. Altrimenti se non � valorizzato
   * significa che sono appena arrivato in questa pagina, quindi devo
   * semplicemente controllare se sono in modifica (fase>4) o inserimento
   **/
   String idColtura,idSpecie=null;
   String campioneTerreno=null,
          giornoCampionamento=null,
          meseCampionamento=null,
          annoCampionamento=null,
          macinato=null;
   idColtura=request.getParameter("idColtura");
   if (idColtura!=null || errore!=null)
   {
     /**
     *  La combo � stata ricaricata quindi devo leggere ancge gli altri
     *  parametri e controllare che non siano null
     */
     idSpecie=request.getParameter("idSpecie");
     campioneTerreno=request.getParameter("campioneTerreno");
     giornoCampionamento=request.getParameter("giornoCampionamento");
     meseCampionamento=request.getParameter("meseCampionamento");
     annoCampionamento=request.getParameter("annoCampionamento");
     macinato=request.getParameter("macinato");
   }
   else
   {
     campioneVegetaliErbacee.select();
     idColtura=campioneVegetaliErbacee.getIdColtura();
     idSpecie=campioneVegetaliErbacee.getIdSpecie();
     campioneTerreno=campioneVegetaliErbacee.getCampioneTerreno();
     giornoCampionamento=campioneVegetaliErbacee.getGiornoCampionamento();
     meseCampionamento=campioneVegetaliErbacee.getMeseCampionamento();
     annoCampionamento=campioneVegetaliErbacee.getAnnoCampionamento();
     macinato=campioneVegetaliErbacee.getMacinato();
   }

   if (idColtura==null) idColtura="-1";
   if (idSpecie==null) idSpecie="";
   if (campioneTerreno==null) campioneTerreno="";
   if (giornoCampionamento==null) giornoCampionamento="";
   if (meseCampionamento==null) meseCampionamento="";
   if (annoCampionamento==null) annoCampionamento="";
   if (macinato==null) macinato="";

   Vector cod=new Vector(),desc=new Vector();
   /**
    * Carico i dati di tutte le classi coltura con tipo coltura "Erbacea" nella
    * combo
    **/
   coltura.selectCodDescClasse(coltura.COLTURA_ERBACEE,cod,desc);
   int size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idColtura.equals(cod.get(i).toString()))
       templ.set("classe.selected","selected");
     else
       templ.set("classe.selected","");
     templ.set("classe.codice",(String)cod.elementAt(i));
     templ.set("classe.descrizione",(String)desc.elementAt(i));
   }
   /**
   *
   * Carico i dati di tutti della specie coltura corrispondente alla classe scelta
   **/
   cod.clear();
   desc.clear();
   coltura.selectCodDescSpecie(coltura.IMPOSTA_QUERY_SPECIE,cod,desc,idColtura);
   size=cod.size();
   for (int i=0;i<size;i++ )
   {
     if (idSpecie.equals(cod.get(i).toString()))
       templ.set("specie.selected","selected");
     else
       templ.set("specie.selected","");
     templ.set("specie.codice",(String)cod.elementAt(i));
     templ.set("specie.descrizione",(String)desc.elementAt(i));
   }
   templ.bset("campioneTerreno",campioneTerreno);
   templ.bset("giornoCampionamento",giornoCampionamento);
   templ.bset("meseCampionamento",meseCampionamento);
   templ.bset("annoCampionamento",annoCampionamento);

  if (aut.isCoordinateGeografiche())
  {
    /**
    * Devo attivare il link relativo alle coordinate geografiche
    */
    templ.newBlock("SiGis");
  }
  else
  {
    /**
    * Devo inibire il link relativo alle coordinate geografiche
    */
    templ.newBlock("NoGis");
  }
  templ.bset("idRichiesta",aut.getIdRichiestaCorrente()+"");
  templ.bset("codiceMateriale",aut.getCodMateriale());
  if ("S".equals(macinato))
    templ.bset("checkedSi","checked");
  else
    templ.bset("checkedNo","checked");
%>
<%= templ.text() %>

