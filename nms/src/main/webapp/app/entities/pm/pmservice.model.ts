export class PMServiceModel {
  constructor(
    public id?: string,
    public serviceId?: string,
    public customerId?: string,
    public startDate?: string,
    public endDate?: string,
    public bandwidth?: string,
    public active?: string,
    public visibleToCustomer?: string,
    public masked?: string,
  ) {}
}
