package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Setting;
import com.amr.project.webapp.paypalsettings.CurrencySettingBag;
import com.amr.project.webapp.paypalsettings.PaymentSettingBag;


/**
 * User: Hajimurad Suleymanov
 * Date: 10.11.2021
 */

public interface SettingService {

     CurrencySettingBag getCurrencySettings();
     String getCurrencyCode();
     PaymentSettingBag getPaymentSettings();
     void saveAll(Iterable<Setting> settings);
}
