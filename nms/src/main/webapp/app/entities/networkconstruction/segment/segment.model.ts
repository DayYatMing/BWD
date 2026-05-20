export class Segment {
  constructor(
    public id?: number,
    public segmentname?: string,
    public labelname?: string,
    public aend?: string,
    public bend?: string,
    public aendfiber?: string,
    public bendfiber?: string,
    public directionorder?: number,
    public dls?: string,
    public comment?: string,
    public networktype?: string,
    public createdby?: string,
    public updatedby?: string,
    public datecreated?: any,
    public dateupdated?: any,
    public sites?: any[],
    public routename?: any,
    public latitude?: any,
    public longitude?: any,
  ) {}
}

export const enum NetworkType {
  'ONNET' = 'ON-NET',
  'OFFNET' = 'OFF-NET',
  'NA' = 'N/A'
}
