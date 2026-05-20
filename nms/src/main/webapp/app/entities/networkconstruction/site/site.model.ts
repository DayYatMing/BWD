export class Site {
  constructor(
    public id?: number,
    public streetnumber?: string,
    public streetname?: string,
    public sitename?: string,
    public labelname?: string,
    public comment?: string,
    public city?: string,
    public zipcode?: string,
    public totallevels?: number,
    public latitude?: number,
    public longitude?: number,
    public createdby?: string,
    public updatedby?: string,
    public buisneesowner?: string,
    public leasedcompany?: string,
    public datecreated?: any,
    public dateupdated?: any,
    public rooms?: any[],
    public segmentname?: any,
    public fibername?: any,
    public imageURL: string = 'https://www.amcharts.com/lib/images/weather/animated/day.svg',
  ) {}
}

export class SiteNetwork {
  constructor(
    public labelname?: string,
    public url?: string,
    public images?: any[],
    public dls?: string[],
  ) {}
}
