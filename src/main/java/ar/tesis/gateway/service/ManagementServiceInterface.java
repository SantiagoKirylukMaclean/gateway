package ar.tesis.gateway.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ManagementServiceInterface {

	ResponseEntity<?> makeBackup();
	
	ResponseEntity<?> restoreBackup(String id);
	
	ResponseEntity<?> listBackup();
}
