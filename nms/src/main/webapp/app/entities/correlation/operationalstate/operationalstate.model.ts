export class OperationalStateData {
    constructor(
        public id?: number,
        public layerRateQualifier?: string,
        public downSince?: string,
        public lastUpdatedAdminStateTimeStamp?: string,
        public lastUpdatedOperationalStateTimeStamp?: string,
        public operationState?: string,
        public adminState?: string,
        public displayDeploymentState?: string,
        public serviceName?: string,
        public customerName?: string,
    ) {
    }
}
