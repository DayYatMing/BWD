
export class Timeline {
  constructor(
    public id?: number,
    public feature?: string,
    public operation?: string,
    public login?: string,
    public oldvalue?: string,
    public newvalue?: string,
    public eventdate?: any
  ) {
  }
}
