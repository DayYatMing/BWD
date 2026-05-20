package com.bwd.nms.orientdbrepository;

import com.bwd.nms.orientdbdomain.ServiceCorrelationData;
import com.bwd.nms.service.mapper.ServiceCorrelationMapper;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ServiceCorrelationRepository {
    private final Logger log = LoggerFactory.getLogger(ServiceCorrelationRepository.class);

    @Autowired
    private ODatabasePool databasePool;

    @Autowired
    public ServiceCorrelationMapper mapper;

    private static final String DATA_CORELATION_QUERY = "SELECT EXPAND( $d ) "+
        "  LET $a = ( select in('HasCustomerServicePatch').in('HasOffnetService').vendorname as offnetvendor ,  in('HasCustomerServicePatch').in('HasOffnetService').name as offnetid ,  in('HasCustomerServicePatch').in('HasOffnetService').protectedcircuit as offnetprotected , " +
        " in('HasCustomerServicePatch').in('HasOffnetService').aenddetails as offnetaenddetails ,  in('HasCustomerServicePatch').in('HasOffnetService').out('HasOffnetAEnd').name as offnetaend ,  in('HasCustomerServicePatch').in('HasOffnetService').out('HasOffnetBEnd').name as offnetbend, in('HasCustomerServicePatch').in('HasOffnetService').benddetails as offnetbenddetails , in('HasCustomerServicePatch').in('HasOffnetService').out('HasOffnetSegment').name as offnetsegment , in('HasCustomerServicePatch').in('HasBackhaulService').name as backhaul,  in('HasCustomerServicePatch').in('HasBackhaulService').servicedetails as backhauldetails, " +
        " in('HasCustomerServicePatch').in('HasBackhaulService').providerName as backhaulvendor," +
        " in('HasCustomerServicePatch').in('HasBackhaulService').out('HasBackhaulAend').name as backhaulaend,  @rid, @rid as tblid , name ,in('HasPort').out('HasDeviceSegment').aendfiber as aendfibers , in('HasPort').out('HasDeviceSegment').bendfiber as bendfibers, "
        + " labelname as portnumber ,  direction, connector , comment , wavelength , frequency , capacity , portstatus , route , segment , customerserviceid, "+
        " in('HasCustomerServicePatch').name as service ,  in('HasPort').model as vendors ,  in('HasPort').in('HasDevice').shelf as shelfs , in('HasPort').in('HasDevice').slot as slots , in('HasPort').in('HasDevice').position as positions ,"+
        " in('HasPort').cardtype as cardtype ,  in('HasPort').dls as dls,     in('HasPort').in().labelname as nodename , in('HasPort').in().in().labelname as roomlocation ,in('HasPort').in().in().in().name as site ,"+
        " in('HasCustomerServicePatch').in('HasService').name as  customer, in('HasCustomerServicePatch').out('HasThirdPartySegment').name as thirdpartysegments , "+
        " out('HasLSIODFPort').name as lsiodf , out('HasLSIODFPort').in('HasPort').cardtype as lsi_odfcardtype ,  out('HasLSIODFPort').in('HasPort').in().in().labelname as  lsi_roomlocation "+
        ", out('HasLSIODFPort').in('HasPort').in('HasDevice').shelf as lsi_shelf , out('HasLSIODFPort').in('HasPort').in('HasDevice').position as lsi_position , out('HasLSIODFPort').labelname as lsi_port , "+
        " out('HasLSIODFPort').connector as lsi_connector ,"+
        "if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').name == []\"),out('HasODFServicePatch').name  ,  out('HasLSIODFPort').out('HasODFServicePatch').name) as patch1   , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').comment == []\"),out('HasODFServicePatch').comment  ,  out('HasLSIODFPort').out('HasODFServicePatch').comment) as patch1_comment , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').thirdparty == []\"),out('HasODFServicePatch').thirdparty  ,  out('HasLSIODFPort').out('HasODFServicePatch').thirdparty) as patch1_thirdparty , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').cardtype == []\"),out('HasODFServicePatch').in('HasPort').cardtype  ,  out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').cardtype ) as patch1_cardtype , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in().in().labelname == []\"),out('HasODFServicePatch').in('HasPort').in().in().labelname  , out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in().in().labelname ) as patch1_roomlocation , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf == []\"),out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf  , out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf ) as patch1_shelf , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').position == []\"),out('HasODFServicePatch').in('HasPort').in('HasDevice').position  , out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').position ) as patch1_position , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').labelname == []\"),out('HasODFServicePatch').labelname , out('HasLSIODFPort').out('HasODFServicePatch').labelname ) as patch1_port ,      "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').connector == []\"),out('HasODFServicePatch').connector , out('HasLSIODFPort').out('HasODFServicePatch').connector ) as patch1_connector , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').name == []\"),out('HasODFServicePatch').name  ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').name) as patch2   ,  "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').comment == []\"),out('HasODFServicePatch').comment  ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').comment) as patch2_comment ,  "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').thirdparty == []\"),out('HasODFServicePatch').thirdparty  ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').thirdparty) as patch2_thirdparty ,  "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').cardtype == []\"),out('HasODFServicePatch').in('HasPort').cardtype  ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').cardtype ) as patch2_cardtype ,  "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in().in().labelname == []\"),out('HasODFServicePatch').in('HasPort').in().in().labelname  , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in().in().labelname ) as patch2_roomlocation ,  "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').shelf == []\"),out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf  , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').shelf ) as patch2_shelf , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').position == []\"),out('HasODFServicePatch').in('HasPort').in('HasDevice').position  , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').position ) as patch2_position , "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').labelname == []\"),out('HasODFServicePatch').labelname , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').labelname ) as patch2_port ,      "
        +"if(eval(\"out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').connector == []\"),out('HasODFServicePatch').connector , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').connector ) as patch2_connector  "
        +" from port where "
        +" portstatus = 'ALLOCATED' and  in('HasCustomerServicePatch').state = ['IN_SERVICE'] and not name like '%ODF%' order by name ), "
        +" $b = ( select  in('HasOffnetService').name as offnetid , in('HasOffnetService').out('HasOffnetSegment').dls as dls , in('HasOffnetService').vendorname as offnetvendor ,  in('HasOffnetService').protectedcircuit as offnetprotected , in('HasOffnetService').out('HasOffnetAEnd').name  as offnetaend , in('HasOffnetService').aenddetails as offnetaenddetails ,   in('HasOffnetService').out('HasOffnetBEnd').name as offnetbend ,  in('HasOffnetService').benddetails as offnetbenddetails ,in('HasOffnetService').out('HasOffnetSegment').name as offnetsegment ,   name.asList() as service , in('HasService').name as customer , bandwidth  as capacity from service where out('HasCustomerServicePatch').name = [] and not in('HasOffnetService').name = [] and state = 'IN_SERVICE' ), "
        + "$c = ( select customername.asList() as customer , rr.asList() as roomlocation , portstatus as portstatus , @rid, @rid as tblid , "
        + " dls.asList() as dls , slot.asList() as slots , segmentnm as segment , name as name ,vendor.asList() as vendors , customercommentid as  customerserviceid ,"
        + " port as portnumber , comment as comment , site.asList() as site , capacity as capacity , frequency as frequency ,"
        + " shelf.asList() as shelfs , connectortype as connector , serviceid.asList() as service , "
        + " device.asList() as  cardtype  from `DacPort` where serviceid is not null  ) , "
        + "$d = UNIONALL( $a, $b , $c)";


    private static final String DATA_CORELATION_QUERY_BY_SERVICEID = " select  @rid , @rid as tblid, name , customerserviceid, labelname as portnumber ,  direction, connector , comment , wavelength , frequency , capacity , portstatus , route , segment , in('HasCustomerServicePatch').out('HasThirdPartySegment').name as thirdpartysegments , "+
        " in('HasCustomerServicePatch').name as service ,  in('HasPort').model as vendors ,  in('HasPort').in('HasDevice').shelf as shelfs , in('HasPort').in('HasDevice').slot as slots , in('HasPort').in('HasDevice').position as positions ,"+
        " in('HasPort').cardtype as cardtype ,  in('HasPort').dls as dls,     in('HasPort').in().labelname as nodename , in('HasPort').in().in().labelname as roomlocation ,in('HasPort').in().in().in().name as site ,"+
        " in('HasCustomerServicePatch').in('HasService').name as  customer,"+
        " out('HasLSIODFPort').name as lsiodf , out('HasLSIODFPort').in('HasPort').cardtype as lsi_odfcardtype ,  out('HasLSIODFPort').in('HasPort').in().in().labelname as  lsi_roomlocation "+
        ", out('HasLSIODFPort').in('HasPort').in('HasDevice').shelf as lsi_shelf , out('HasLSIODFPort').in('HasPort').in('HasDevice').position as lsi_position , out('HasLSIODFPort').labelname as lsi_port , "+
        " out('HasLSIODFPort').connector as lsi_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').name as patch1 , out('HasLSIODFPort').out('HasODFServicePatch').comment as patch1_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').thirdparty as patch1_thirdparty , out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').cardtype as patch1_cardtype ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in().in().labelname as  patch1_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf as patch1_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').position as patch1_position , out('HasLSIODFPort').out('HasODFServicePatch').labelname as patch1_port , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').connector as patch1_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').name as patch2  , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').comment as patch2_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').thirdparty as patch2_thirdparty , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').cardtype as patch2_cardtype , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in().in().labelname "+
        " as  patch2_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').shelf as patch2_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').position as patch2_position , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').labelname as "+
        " patch2_port , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').connector as patch2_connector "+
        " from port where portstatus = 'ALLOCATED'  and not name like '%ODF%' "
        + "  and in('HasCustomerServicePatch').name  == [':serviceid'] order by name ";

    private static final String ALL_NETWORKDATA_CORELATION_QUERY = " select in('HasCustomerServicePatch').in('HasBackhaulService').name as backhaul,"+
        " in('HasCustomerServicePatch').in('HasBackhaulService').providerName as backhaulvendor,"+
        " in('HasCustomerServicePatch').in('HasBackhaulService').out('HasBackhaulAend').name as backhaulaend,  @rid , @rid as tblid, name ,customerserviceid,  labelname as portnumber ,  direction, connector , comment , wavelength , frequency , capacity , portstatus , route , segment ,  in('HasPort').out('HasDeviceSegment').name as segmentnm , in('HasCustomerServicePatch').out('HasThirdPartySegment').name as thirdpartysegments , "+
        " in('HasCustomerServicePatch').name as service ,  in('HasPort').model as vendors ,  in('HasPort').in('HasDevice').shelf as shelfs , in('HasPort').in('HasDevice').slot as slots , in('HasPort').in('HasDevice').position as positions ,"+
        " in('HasPort').cardtype as cardtype ,  in('HasPort').dls as dls,     in('HasPort').in().labelname as nodename , in('HasPort').in().in().labelname as roomlocation ,in('HasPort').in().in().in().name as site ,"+
        " in('HasCustomerServicePatch').in('HasService').name as  customer,"+
        " out('HasLSIODFPort').name as lsiodf , out('HasLSIODFPort').in('HasPort').cardtype as lsi_odfcardtype ,  out('HasLSIODFPort').in('HasPort').in().in().labelname as  lsi_roomlocation "+
        ", out('HasLSIODFPort').in('HasPort').in('HasDevice').shelf as lsi_shelf , out('HasLSIODFPort').in('HasPort').in('HasDevice').position as lsi_position , out('HasLSIODFPort').labelname as lsi_port , "+
        " out('HasLSIODFPort').connector as lsi_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').name as patch1 , out('HasLSIODFPort').out('HasODFServicePatch').comment as patch1_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').thirdparty as patch1_thirdparty , out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').cardtype as patch1_cardtype ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in().in().labelname as  patch1_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf as patch1_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').position as patch1_position , out('HasLSIODFPort').out('HasODFServicePatch').labelname as patch1_port , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').connector as patch1_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').name as patch2  , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').comment as patch2_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').thirdparty as patch2_thirdparty , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').cardtype as patch2_cardtype , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in().in().labelname "+
        " as  patch2_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').shelf as patch2_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').position as patch2_position , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').labelname as "+
        " patch2_port , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').connector as patch2_connector "+
        " from port where not name like '%ODF%' and name is not null  order by portstatus desc ,  name ";


    private static final String ALL_ODF_CORELATION_QUERY = " select in('HasCustomerServicePatch').in('HasBackhaulService').name as backhaul,"+
        " in('HasCustomerServicePatch').in('HasBackhaulService').providerName as backhaulvendor,"+
        " in('HasCustomerServicePatch').in('HasBackhaulService').out('HasBackhaulAend').name as backhaulaend,  @rid , @rid as tblid,  name , customerserviceid, labelname as portnumber ,  direction, connector , comment , wavelength , frequency , capacity , portstatus , route , segment ,  in('HasPort').out('HasDeviceSegment').name as segmentnm , in('HasCustomerServicePatch').out('HasThirdPartySegment').name as thirdpartysegments , "+
        " in('HasCustomerServicePatch').name as service ,  in('HasPort').model as vendors ,  in('HasPort').in('HasDevice').shelf as shelfs , in('HasPort').in('HasDevice').slot as slots , in('HasPort').in('HasDevice').position as positions ,"+
        " in('HasPort').cardtype as cardtype ,  in('HasPort').dls as dls,     in('HasPort').in().labelname as nodename , in('HasPort').in().in().labelname as roomlocation ,in('HasPort').in().in().in().name as site ,"+
        " in('HasCustomerServicePatch').in('HasService').name as  customer,"+
        " out('HasLSIODFPort').name as lsiodf , out('HasLSIODFPort').in('HasPort').cardtype as lsi_odfcardtype ,  out('HasLSIODFPort').in('HasPort').in().in().labelname as  lsi_roomlocation "+
        ", out('HasLSIODFPort').in('HasPort').in('HasDevice').shelf as lsi_shelf , out('HasLSIODFPort').in('HasPort').in('HasDevice').position as lsi_position , out('HasLSIODFPort').labelname as lsi_port , "+
        " out('HasLSIODFPort').connector as lsi_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').name as patch1 , out('HasLSIODFPort').out('HasODFServicePatch').comment as patch1_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').thirdparty as patch1_thirdparty , out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').cardtype as patch1_cardtype ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in().in().labelname as  patch1_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').shelf as patch1_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').in('HasPort').in('HasDevice').position as patch1_position , out('HasLSIODFPort').out('HasODFServicePatch').labelname as patch1_port , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').connector as patch1_connector ,"+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').name as patch2  , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').comment as patch2_comment ,  out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').thirdparty as patch2_thirdparty , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').cardtype as patch2_cardtype , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in().in().labelname "+
        " as  patch2_roomlocation ,out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').shelf as patch2_shelf , "+
        " out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').in('HasPort').in('HasDevice').position as patch2_position , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').labelname as "+
        " patch2_port , out('HasLSIODFPort').out('HasODFServicePatch').out('HasInternalPortPatch').connector as patch2_connector "+
        " from port where not name like '%ODF%' and name is not null  order by portstatus desc ,  name ";


    private static final String PARTIAL_ALL_NETWORKDATA_CORELATION_QUERY = " SELECT EXPAND( $e ) "+
        "			  LET $a = ( select  name , frequency , capacity , portstatus , route , segment , customerserviceid, in('HasPort').out('HasDeviceSegment').name as segmentnm ," +
        "             in('HasCustomerServicePatch').name as service ,   in('HasPort').model as vendors ,  in('HasPort').out('HasDeviceSegment').networktype as networktype,    in('HasPort').cardtype as cardtype ,  in('HasPort').dls as dls,     in('HasPort').in().in().in().name as site ," +
        "             in('HasCustomerServicePatch').in('HasService').name as  customer ,  out('HasLSIODFPort').name as lsiodf " +
        "             from port where not name like '%ODF%' and name is not null  order by portstatus desc ,  name  )  , $b = ( SELECT FROM DACPORT ) , " +
        "			  $c = ( select out('HasOffnetService').in('HasService').name as customer , vendorname as  vendor , status as portstatus , name AS cardtype , out('HasOffnetService').name as service , out('HasOffnetAEnd').name as site ,  capacity , aenddetails +\"_OFF-NET\" as name , "+
        "			  frequency , out('HasOffnetSegment').name as segmentnm ,  out('HasOffnetSegment').networktype as networktype from offnet ) ,  "+
        "			  $d = ( select out('HasOffnetService').in('HasService').name as customer , vendorname as  vendor , status as portstatus ,  name AS cardtype , out('HasOffnetService').name as service , out('HasOffnetBEnd').name as site ,  capacity , benddetails +\"_OFF-NET\" as name , "+
        "			  frequency , out('HasOffnetSegment').name as segmentnm ,  out('HasOffnetSegment').networktype as networktype from offnet ) , "+
        "			  $e = UNIONALL( $a, $b , $c, $d)";


    private static final String ALL_PORT_DATA_QUERY = "select name , connector , comment , wavelength , frequency , capacity , portstatus, customerserviceid, "+
        ", route , segment , in('HasCustomerServicePatch').out('HasThirdPartySegment').name as thirdpartysegments ,  out('HasLSIODFPort').name as lsiodf , out('HasInternalPortPatch').name  as patch , "+
        " out('HasInternalPortPatch').out('HasInternalPortPatch').name  as patch1  , out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').name  as patch2 ,"+
        " out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').name  as patch3  , out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').out('HasInternalPortPatch').name  as patch4 "+
        ", in('HasCustomerServicePatch').name as service ,"
        + " in('HasPort').cardtype as cardtype , "+
        "in('HasPort').in().labelname as nodename , in('HasPort').in().in().labelname as roomlocation , "+
        "in('HasPort').in().in().in().name as site , in('HasCustomerServicePatch').in('HasService').name as "+
        "customer from port order by name ";

    private static final String FREE_PORTS_CHART_QUERY = "select in('HasPort').in().in().in().name as x  from port where portstatus = 'ALLOCATED' and  not name like '%ODF%'  unwind x ";

    private static final String ALL_CONNECTORS = "SELECT  set(connector) AS connector FROM port unwind connector";

    private static final String UPDATE_CUSTOMERSERVICEID = "UPDATE port SET customerserviceid = :customerserviceid where @rid = :tblid";
    private static final String UPDATE_CUSTOMERSERVICEIDDAC = "UPDATE dacport SET customercommentid = :customerserviceid where @rid = :tblid";




    public Flux<ServiceCorrelationData> findAll() {
        Map<Object, Object> args = new HashMap<>();
        return select(DATA_CORELATION_QUERY, args);
    }

    public Flux<ServiceCorrelationData> select(String query, Map<Object, Object> args) {

        log.debug("Select Query: {} \nArgs: {}", query, args);

        return Mono.fromCallable(() -> {
                try (ODatabaseSession db = databasePool.acquire()) {
                    return db
                        .query(query, args)
                        .stream()
                        .collect(Collectors.toList());
                }
            })
            .flatMapMany(results ->
                Flux.fromIterable(results)
                    .map(result -> mapper.oResultToData(result)))
            .subscribeOn(Schedulers.boundedElastic());
    }
}
