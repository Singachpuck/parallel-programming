package com.kpi.multithreading.client_server.controller;


import com.kpi.multithreading.client_server.model.MultiplyMatrixModel;
import com.kpi.multithreading.client_server.model.ResultModel;
import com.kpi.multithreading.client_server.service.MatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
class MatrixController {

    @GetMapping
    String home() {
        return "home";
    }

    @GetMapping("/server")
    String server() {
        return "server";
    }

    @GetMapping("/client")
    String client() {
        return "client";
    }

    @RestController
    static class MatrixRestController {

        @Autowired
        private MatrixService matrixService;

        @PostMapping("/api/clientData")
        ResponseEntity<?> clientPost(@RequestBody MultiplyMatrixModel multiplyModel) {
            final ResultModel resultModel = matrixService.calculateMatrix(multiplyModel.nThread(),
                    multiplyModel.matrixFile1(),
                    multiplyModel.matrixFile2());
            return ResponseEntity.ok(resultModel);
        }

        @GetMapping("/api/serverData")
        ResponseEntity<?> serverPost(@RequestParam("size") int size, @RequestParam("nThread") int nThread) {
            final ResultModel resultModel = matrixService.extractMatricesLocallyAndCalculate(size, nThread);
            return ResponseEntity.ok(resultModel);
        }
    }
}
