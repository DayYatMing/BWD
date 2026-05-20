export class Order {
  constructor(
    public id?: number,
    public name?: string,
    public entityName?: string,
    public entityId?: number,
    public status?: string,
    public duration?: number,
    public circuit?: Blob,
  ) {}
}
