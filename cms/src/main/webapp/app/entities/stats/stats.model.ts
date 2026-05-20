export class Stats {
  constructor(
    public time?: string,
    public metric?: string,
    public linkFailSecIn?: number | undefined,
    public linkFailSecOut?: number | undefined,
    public physicalErrCntIn?: number | undefined,
    public physicalErrCntOut?: number | undefined,
    public frameChkSeqErrCntIn?: number | undefined,
    public frameChkSeqErrCntOut?: number | undefined,
    public numOfSecInBinTxLineCard?: number | undefined,
    public numOfSecInBinRxLineCard?: number | undefined,
    public errSecIn?: number | undefined,
    public errSecOut?: number | undefined,
    public severelyErrSecIn?: number | undefined,
    public severelyErrSecOut?: number | undefined,
  ) {}
}

export class StatsInput {
  constructor(
    public customer?: string,
    public serviceId?: string,
    public pmSource?: string,
    public dtFr?: string,
    public dtTo?: string,
  ) {}
}
