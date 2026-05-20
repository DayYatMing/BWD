package service;

import config.ConfigData;

public class OtpService {

    public static String getOtp() {
        return ConfigData.getUserOtp();
    }

}
