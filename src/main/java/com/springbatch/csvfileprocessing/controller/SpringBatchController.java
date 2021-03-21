package com.springbatch.csvfileprocessing.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class SpringBatchController {
	@Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job processJob;
    
    @GetMapping
    public String main() {
        return "index";
    }
 
    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
 
        File fileToImport = new File(file.getOriginalFilename());

        OutputStream outputStream = new FileOutputStream(fileToImport);
        IOUtils.copy(file.getInputStream(), outputStream);
        outputStream.flush();
        outputStream.close();

        JobParameters jobParameters = new JobParametersBuilder().addString("fullPathFileName", fileToImport.getAbsolutePath()).addLong("time",System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);
        
        redirectAttributes.addFlashAttribute("message", "Batch job's are executed successfully !!!");
        
        return "redirect:/";
    }
}
