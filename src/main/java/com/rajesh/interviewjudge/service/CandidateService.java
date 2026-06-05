package com.rajesh.interviewjudge.service;

import com.rajesh.interviewjudge.dto.CandidateDTO;
import com.rajesh.interviewjudge.entity.Candidate;
import com.rajesh.interviewjudge.repository.CandidateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate registerCandidate(CandidateDTO dto) {

        Candidate candidate = new Candidate();

        candidate.setName(dto.getName());
        candidate.setEmail(dto.getEmail());
        candidate.setPhone(dto.getPhone());

        return candidateRepository.save(candidate);
    }

    public Candidate login(String email, String password) {

        Candidate candidate = candidateRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!candidate.getPassword().equals(password)) {
            throw new RuntimeException("Invalid Password");
        }

        return candidate;
    }

    public String uploadResume(Long id, MultipartFile file) {

        try {

            Candidate candidate = candidateRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Candidate not found"));

            String uploadDir = "uploads/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis()
                            + "_"
                            + file.getOriginalFilename();

            Path filePath =
                    Paths.get(uploadDir + fileName);

            Files.write(filePath, file.getBytes());

            candidate.setResumePath(filePath.toString());

            candidateRepository.save(candidate);

            return "Resume Uploaded Successfully";

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("File Upload Failed");
        }
    }

    public ResponseEntity<Resource> downloadResume(Long id) {

        try {

            Candidate candidate = candidateRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Candidate not found"));

            Path path =
                    Paths.get(candidate.getResumePath());

            Resource resource =
                    new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\""
                                    + resource.getFilename()
                                    + "\"")
                    .body(resource);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("Download Failed");
        }
    }

    public Candidate getCandidateById(Long id) {

        return candidateRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public CandidateDTO evaluateCandidate(Long id,
                                          int technical,
                                          int communication,
                                          int aptitude) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        CandidateDTO dto = new CandidateDTO();

        dto.setName(candidate.getName());
        dto.setEmail(candidate.getEmail());
        dto.setPhone(candidate.getPhone());

        dto.setTechnicalScore(technical);
        dto.setCommunicationScore(communication);
        dto.setAptitudeScore(aptitude);

        double finalScore =
                (technical * 0.5)
                        + (communication * 0.3)
                        + (aptitude * 0.2);

        dto.setFinalScore(finalScore);

        if (finalScore >= 70) {
            dto.setResult("SELECTED");
        } else {
            dto.setResult("REJECTED");
        }

        return dto;
    }

    public Candidate analyzeResume(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        String sampleSkills = "";

        String resumeName = "";

        if (candidate.getResumePath() != null) {
            resumeName = candidate.getResumePath().toLowerCase();
        }

        if (resumeName.contains("java")) {
            sampleSkills += "Java, ";
        }

        if (resumeName.contains("python")) {
            sampleSkills += "Python, ";
        }

        if (resumeName.contains("sql")) {
            sampleSkills += "SQL, ";
        }

        if (resumeName.contains("spring")) {
            sampleSkills += "Spring Boot, ";
        }

        if (sampleSkills.isEmpty()) {
            sampleSkills = "Java, Python, SQL";
        }

        candidate.setSkills(sampleSkills);

        candidate.setExtractedText(
                "Resume uploaded successfully. Skills detected: "
                        + sampleSkills);

        return candidateRepository.save(candidate);
    }

    public Candidate scoreResume(Long id) {

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Candidate not found"));

        String skills = candidate.getSkills();

        int score = 0;

        if (skills != null) {

            String s = skills.toLowerCase();

            if (s.contains("java")) score += 20;
            if (s.contains("python")) score += 20;
            if (s.contains("sql")) score += 20;
            if (s.contains("spring")) score += 20;
            if (s.contains("machine learning")) score += 20;
        }

        candidate.setResumeScore((double) score);

        if (score >= 60) {
            candidate.setResult("SELECTED");
        } else {
            candidate.setResult("REJECTED");
        }

        return candidateRepository.save(candidate);
    }
}
