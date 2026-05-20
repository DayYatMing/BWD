export class PMConfigurationModel {
  constructor(
    public id?: string,
    public serviceId?: string,
    public sourceName?: string,
    public nodeId?: string,
    public vendor?: string,
    public labelName?: string,
    public routeDirection?: string,
    public segmentDirection?: string,
    public disableCollection?: string,
    public visibleToCustomer?: string,
    public frequency?: string,
  ) {}
}
