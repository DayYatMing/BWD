export class Backhaul {
  constructor(
    public id?: number,
    public providerName?: string,
    public name?: string,
    public labelname?: string,
    public servicedetails?: string,
    public aend?: string,
    public bend?: string,
    public customer?: string,
    public service?: string,
    public status?: string,
    public comment?: string,
    public capacity?: number,
    public protectedcircuit?: boolean,
    public segment?: string,
    public sites?: any[],
  ) {}
}

