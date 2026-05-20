export class PMSourceModel {
  constructor(
    public id?: string,
    public customer?: string,
    public customerSid?: string,
    public pmSource?: string,
    public visibleToCustomer?: string,
    public routeEnd?: string,
    public segmentEnd?: string,
    public source?: string,
    public nodeId?: string,
    public route?: string,
    public segment?: string,
  ) {}
}
