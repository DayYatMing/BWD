

export class TicketRequest {
    constructor(
        public Ticket?: Ticket,
        public Article?: Article
    ) {
    }
}
export class Ticket {
    constructor(
        public Title?: string,
        public Queue?: string,
        public State?: string,
        public PriorityID?: string,
        public CustomerUser?: string,
        public CustomerID?: string,
        public Type?: string,
        public Service?: string,
        public Bulk?: string

    ) {
    }
}

export class Article {
    constructor(
        public Subject?: string,
        public Body?: string,
        public ContentType?: string
    ) {
    }
}

export class TicketResponse{
    constructor(
        public id?: number,
        public ticketid?: string,
        public ticketnumber?: string,
        public service?: string,
        public status?: string,
        public statusmessage?: string,
        public title?: string,
        public eventdate?: any
    ) {
    }
}

