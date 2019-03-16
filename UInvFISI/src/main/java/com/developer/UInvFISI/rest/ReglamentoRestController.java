package com.developer.UInvFISI.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.UInvFISI.entity.Reglamento;
import com.developer.UInvFISI.service.ReglamentoService;

@RestController
@RequestMapping("/api/reglamento")
public class ReglamentoRestController {

	@Autowired
	@Qualifier("reglamentoService")
	private ReglamentoService reglamentoService;
	
	@GetMapping("/reglamentos")
	public ResponseEntity<List<Reglamento>> findAll() {
		
		try {
			
			List<Reglamento> reglamentos = reglamentoService.findAll();
			if(reglamentos.isEmpty()) {
				
				return new ResponseEntity<List<Reglamento>>(HttpStatus.NO_CONTENT);
			}
			else {
				
				return new ResponseEntity<List<Reglamento>>(reglamentos, HttpStatus.OK);
			}
		}
		catch(Exception e) {
			
			return new ResponseEntity<List<Reglamento>>(HttpStatus.BAD_REQUEST);
		}
	}
}
