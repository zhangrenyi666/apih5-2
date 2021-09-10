package com.apih5.service;

import com.apih5.framework.entity.ResponseEntity;

public interface SysCaptchaService {

    // --扩展
    public ResponseEntity getCaptchaCode();
    
    public ResponseEntity checkSysCaptchaCode(String captchaId, String captchaCode);
}

