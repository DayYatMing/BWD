export class Chatbot {
  public response?: string | null;

  public context?: string | null;

  public method?: string | null;

  constructor(response?: string, context?: string, method?: string) {
    this.response = response ? response : null;
    this.context = context ? context : null;
    this.method = method ? method : null;
  }
}
