export class Offnet {
  constructor(
    public id?: number,
    public vendorname?: string,
    public name?: string,
    public labelname?: string,
    public aend?: string,
    public aenddetails?: string,
    public bend?: string,
    public benddetails?: string,
    public customer?: string,
    public service?: string,
    public status?: string,
    public comment?: string,
    public capacity?: number,
    public frequency?: string,
    public protectedcircuit?: boolean,
    public segment?: string,
    public sites?: any[],
  ) {}
}

