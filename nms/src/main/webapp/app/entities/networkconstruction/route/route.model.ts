export class Route {
  constructor(
    public id?: number,
    public routename?: string,
    public labelname?: string,
    public aend?: string,
    public zend?: string,
    public segments?: any[],
    public comment?: string,
    public createdby?: string,
    public updatedby?: string,
    public datecreated?: any,
    public dateupdated?: any,
    public segmentsDetails?: any[],
  ) {}
}

