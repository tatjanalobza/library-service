package com.example.libraryservice.controller;

import com.example.libraryservice.model.Patron;
import com.example.libraryservice.service.PatronService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/library")
@Api(value="libraryservice", description="Operations for managing patrons in the library system")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @ApiOperation(value = "Lists all the available patrons")
    @RequestMapping(value = "/patrons", method = GET, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to list patrons"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    public List<Patron> getPatrons() {

        return patronService.getPatrons();
    }

    @ApiOperation(value = "Retrieves a single patron")
    @RequestMapping(value = "/patron/{id}", method = GET, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the patron"),
            @ApiResponse(code = 400, message = "The provided parameter(s) is/are invalid"),
            @ApiResponse(code = 401, message = "You are not authorized to list patrons"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")})
    public Patron getPatron(@PathVariable @ApiParam(value = "The patron id") Long id) {
        return patronService.getPatron(id);
    }

}
