package ar.tesis.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.tesis.gateway.modelDTO.RequestBackupDTO;
import ar.tesis.gateway.service.ManagementServiceInterface;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/management")
public class ManagementController {
	
	
	@Autowired
    ManagementServiceInterface managementService;

    @RequestMapping(value = "/make-backup", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> makeBackup(@RequestHeader HttpHeaders headers) {

        return managementService.makeBackup();
    }
    
    @RequestMapping(value = "/restore-backup", method = RequestMethod.POST)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> restoreBackup(@RequestBody RequestBackupDTO requestBackupDTO, @RequestHeader HttpHeaders headers) {

        return managementService.restoreBackup(requestBackupDTO.getId());
    }
    
    @RequestMapping(value = "/list-backup", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> listBackup(@RequestHeader HttpHeaders headers) {

        return managementService.listBackup();
    }

}
