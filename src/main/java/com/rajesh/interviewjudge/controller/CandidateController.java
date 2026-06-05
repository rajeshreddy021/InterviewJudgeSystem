package com.rajesh.interviewjudge.controller;

import com.rajesh.interviewjudge.dto.CandidateDTO;
import com.rajesh.interviewjudge.entity.Candidate;
import com.rajesh.interviewjudge.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/register")
    public Candidate register(@RequestBody CandidateDTO dto) {
        return candidateService.registerCandidate(dto);
    }

    @PostMapping("/login")
    public Candidate login(
            @RequestParam String email,
            @RequestParam String password) {

        return candidateService.login(email, password);
    }

    @PostMapping("/uploadResume/{id}")
    public String uploadResume(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return candidateService.uploadResume(id, file);
    }

    @GetMapping("/downloadResume/{id}")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long id) {

        return candidateService.downloadResume(id);
    }

    @GetMapping("/profile/{id}")
    public Candidate getProfile(@PathVariable Long id) {
        return candidateService.getCandidateById(id);
    }

    @GetMapping("/all")
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/evaluate/{id}")
    public CandidateDTO evaluateCandidate(
            @PathVariable Long id,
            @RequestParam int technical,
            @RequestParam int communication,
            @RequestParam int aptitude) {

        return candidateService.evaluateCandidate(
                id,
                technical,
                communication,
                aptitude
        );
    }

    @GetMapping("/analyzeResume/{id}")
    public Candidate analyzeResume(@PathVariable Long id) {

        return candidateService.analyzeResume(id);
    }
}