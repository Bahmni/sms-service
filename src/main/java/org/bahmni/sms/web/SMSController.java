package org.bahmni.sms.web;

import lombok.AllArgsConstructor;
import org.bahmni.sms.SMSSender;
import org.bahmni.sms.model.SMSContract;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notification/sms")
@AllArgsConstructor
public class SMSController {


    private final SMSSender smsSender;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity sendSMS(@RequestBody SMSContract smsContract, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) throws Exception {
        return smsSender.send(smsContract.getPhoneNumber(), smsContract.getMessage());
    }
}
