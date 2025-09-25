package com.fit.ws.resource.controllers;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fit.ws.core.utils.ResponseAPI;
import com.fit.ws.resource.dto.RoutineCreateDto;
import com.fit.ws.resource.services.RoutineService;
import com.fit.ws.resource.utils.RoutineUtils;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
@Tag(name ="Routine", description = "Manage routine")
public class RoutineController {
	private final RoutineService routineService;
	private final RoutineUtils routineUtils;
	
	@PostMapping(path = "routine")
	public Object createRoutine(@RequestBody RoutineCreateDto createDto) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(routineUtils.toDocument(routineService.createNewRoutine(createDto)));
		return responseAPI.build();
	}
	
	@GetMapping(path = "routines")
	public Object getAllRoutines() {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(routineUtils.toListDocuments(routineService.getAll()));
		return responseAPI.build();
	}
	
	@GetMapping(path = "routine/{id}")
	public Object getInfoRoutine(@PathVariable String id) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(routineUtils.toDocument(routineService.getById(new ObjectId(id))));
		return responseAPI.build();
	}
	
	@PutMapping(path = "routine/{id}")
	public Object updateRoutine(@PathVariable String id,@RequestBody RoutineCreateDto routineCreateDto) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		responseAPI.setResutl(routineUtils.toDocument(routineService.updateRoutine(id, routineCreateDto)));
		return responseAPI.build();
	}
	
	@DeleteMapping(path = "routine/{id}")
	public Object deleteRoutine(@PathVariable String id) {
		ResponseAPI responseAPI = new ResponseAPI();
		responseAPI.setOk();
		try {
			
			routineService.delete(new ObjectId(id));
			responseAPI.setResutl("Xóa thành công");
		} catch (Exception e) {
			throw e;
		}
		return responseAPI.build();
	}

}
