export const enum QueueEnum {
  'NOC',
}
export const enum StateEnum {
  'open',
  'closed sucessful',
  'closed unsucessful',
  'closed with workaround',
  'pending auto close+',
  'pending auto close-',
  'pending reminder',
}
export const enum TypeEnum {
  'Incident',
  'Unclassified',
}
export class ArticleArr {
  constructor(
    public From?: string,
    public To?: string,
    public Subject?: string,
    public Body?: string,
    public ChangeTime?: string,
  ) {}
}
export class TicketManagement {
  constructor(
    public id?: number,
    public To?: string,
    public TicketID?: number,
    public Title?: string,
    public TicketNumber?: number,
    public Type?: TypeEnum,
    public Queue?: QueueEnum,
    public State?: StateEnum,
    public PriorityID?: number,
    public CustomerUser?: string,
    public Subject?: string,
    public Body?: string,
    public convertedAge?: string,
    public Created?: string,
    public Owner?: string,
    public Response?: string,
    public Article?: ArticleArr[],
    public Priority?: string,
    public Service?: string,
    public CustomerID?: string,
    public open?: number,
    public closed?: number,
    public Login?: string,
    public StateType?: string,
  ) {}
}

export class Customer {
  constructor(
    public id?: number,
    public name?: string,
    public contact?: string,
    public address?: string,
    public active?: string,
  ) {}
}
