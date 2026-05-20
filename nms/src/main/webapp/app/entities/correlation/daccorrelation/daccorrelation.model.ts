
export class DaccorrelationData  {
  constructor(
    public fromtblid?: string,
    public fromsite?: string,
    public fromdls?: string,
    public fromfrequency?: string,
    public fromport?: string,
    public fromcomment?: string,
    public fromshelf?: string,
    public fromslot?: string,
    public fromserviceid?: string,
    public fromrr?: string,
    public fromname?: string,
    public fromdevice?: string,
    public fromconnectortype?: string,

    public totblid?:String,
    public tosite?: string,
    public todls?: string,
    public tofrequency?: string,
    public toport?: string,
    public tocomment?: string,
    public toshelf?: string,
    public toslot?: string,
    public torr?: string,
    public toname?: string,
    public todevice?: string,
    public toserviceid?: string,
    public toconnectortype?: string,
  ) {
  }
}


