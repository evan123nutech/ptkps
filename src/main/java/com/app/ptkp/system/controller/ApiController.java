package com.app.ptkp.system.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import com.app.ptkp.system.controller.logik.PanduanLogik;
import com.app.ptkp.system.controller.model.QrCodeRespon;
import com.app.ptkp.system.controller.model.Sertifikat;
import com.app.ptkp.system.controller.repo.SertifikatRepo;
import com.app.ptkp.system.controller.repo.t_trx_gateRepo;
import com.app.ptkp.system.controller.services.t_trx_gateService;
import com.app.ptkp.system.util.QRCodeGenerator;
import com.app.ptkp.system.util.ResponseHandler;
import com.google.zxing.WriterException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApiController {

	 @Autowired private PanduanLogik panduanLogik;
	 @Autowired private t_trx_gateService t_trx_gateService; 
	 @Autowired private t_trx_gateRepo tRepo;
     @Autowired	private SertifikatRepo sertifikatRepo;	 

    
    @RequestMapping(value = "/transaction/api/t_trx_gate", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> apiTransactionTrx(Model model){    	
        try {
            return ResponseHandler.generateResponse("OK", HttpStatus.OK, t_trx_gateService.findAllData());
        } catch (Exception e) {
        	return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
            
    }
    @RequestMapping(value = "/index/api", method = RequestMethod.GET)
    public ResponseEntity<Object> apiIndex(Model model){    	
        try {
            JSONArray arr = new JSONArray();
            Sertifikat obj = new Sertifikat();
            List<Sertifikat> listParam = (List<Sertifikat>) sertifikatRepo.findAll();
               if(listParam.size() > 0) {
                   for(int i=0; i<listParam.size(); i++) {
                       obj = (Sertifikat) listParam.get(i);               
                       ClassPathResource resource =  new ClassPathResource("QRCode.png");
                       byte[] image = new byte[0]; 
                       try {
                           image = QRCodeGenerator.getQRCodeImage(obj.getSnomor(),250,250);
                       } catch (WriterException | IOException e) {
                           e.printStackTrace();
                       }
                       obj.setSsertifikat("data:image/png;base64,"+Base64.getEncoder().encodeToString(image));
                       arr.add(new QrCodeRespon(i, obj.getSnama(), obj.getSnomor(), obj.getSjudul(), obj.getSlembaga(), obj.getSlembaga(), obj.getSsertifikat()));
                    }
               }
             
            return ResponseHandler.generateResponse("OK", HttpStatus.OK, arr.get(0).toString());
        } catch (Exception e) {
        	return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
            
    }

}
