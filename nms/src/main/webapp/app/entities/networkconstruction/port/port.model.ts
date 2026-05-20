

export const enum Direction {
  'TX',
  'RX'
}

export const enum Connector {
  'LCU',
  'SCA',
  'SCU'
}

export const enum StatusType {
  'FREE',
  'RESERVED',
  'ALLOCATED',
  'DECOMMISIONED',
  'UNKNOWN'
}
export class PortBatchChanges {
  constructor(
    addedRecords : any,
    deletedRecords : any,
    changedRecords : any
  ){}
}
export class Port {
  constructor(
    public id?: number,
    public roomlocation?: string,
    public position?: string,
    public vendor?: string,
    public comment?: string,
    public portproperty?: string,
    public thirdparty?: string,
    public portname?: string,
    public serviceid?: string,
    public portnumber?: number,
    public description?: string,
    public labelname?: string,
    public imagename?: string,
    public frequency?: string,
    public dls?: string,
    public wavelength?: string,
    public capacity?: number,
    public direction?: string,
    public connector?: string,
    public portstatus?: string,
    public createdby?: string,
    public lsiodf?: string,
    public site?: string,
    public node?: string,
    public shelf?: string,
    public slot?: string,
    public cardtype?: string,
    public room?: string,
    public mappedportname?: string,
    public updatedby?: string,
    public datecreated?: any,
    public dateupdated?: any,
    public patch1?: string,
    public patch1_comment?: string,
    public patch1_thirdparty?: string,
    public patch2?: string,
    public patch2_comment?: string,
    public patch2_thirdparty?: string,
    public patch3?: string,
    public patch3_comment?: string,
    public patch3_thirdparty?: string,
    public patch4?: string,
    public patch4_comment?: string,
    public patch4_thirdparty?: string,
    public thirdpartysegments?: any,
    public route?: string,
    public segment?: string,
  ) {
  }
}
