package com.example.kurs.controller;

import com.example.kurs.dao.Role;
import com.example.kurs.dao.User;
import com.example.kurs.model.ResumeModel;
import com.example.kurs.repo.UserRepo;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.IOException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import static java.io.FileDescriptor.out;


@Controller
public class MainController {
    @Autowired
    SpringTemplateEngine templateEngine;
    private  final ResumeModel resume = new ResumeModel();;
    private final UserRepo userRepo;
    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/registration")
    public String addNew(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, Model model) {
        User check = userRepo.findByUsername(user.getUsername());
        if (check == null) {
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userRepo.save(user);
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("resume",new ResumeModel());
        model.addAttribute("role",userRepo.findByUsername(username).getRoles());

        return "user_home";
    }

    @GetMapping("/Start")
    public String user_home(Model model, Authentication auth) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if(auth.isAuthenticated()) {
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
            model.addAttribute("resume",new ResumeModel());
            return "baseInfo";
        }
        else
        {
            return "authorization";
        }
    }
    @PostMapping(value="/baseInfo")
    public String GetBaseInfo(@ModelAttribute ResumeModel resume, Model model) {
        this.resume.setFirstname(resume.getFirstname());
        this.resume.setLastname(resume.getLastname());
        this.resume.setCity(resume.getCity());
        this.resume.setCountry(resume.getCountry());
        this.resume.setPhone(resume.getPhone());
        this.resume.setEmail(resume.getEmail());
        this.resume.setLinks(resume.getLinks());
        model.addAttribute("resume",new ResumeModel());
        return "recommendations";
    }

    @PostMapping(value="/recomends")
    public String GetRecomends(@ModelAttribute ResumeModel resume, @RequestParam MultipartFile file, Model model) throws java.io.IOException {
        this.resume.setEducation(resume.getEducation());
        this.resume.setRecomends(resume.getRecomends());
        this.resume.setAbout(resume.getAbout());

       this.resume.setPhoto( file.getOriginalFilename());
        model.addAttribute("resume",new ResumeModel());
        return "exp";
    }

    @PostMapping(value="/expEnd")
    public String GetExp(@ModelAttribute ResumeModel resume, Model model) {
        this.resume.setExp(resume.getExp());
        this.resume.setOccupation(resume.getOccupation());
        this.resume.setSallary(resume.getSallary());
        this.resume.setAddInfo(resume.getAddInfo());
        this.resume.setDateOfBirth(resume.getDateOfBirth());

        if(this.resume.getLinks() != null)
            this.resume.setArrLinks(this.resume.getLinks().split(";"));

        if(this.resume.getRecomends() != null)
            this.resume.setArrrRcomends(this.resume.getRecomends().split(";"));

        if(this.resume.getEducation() != null)
            this.resume.setArrEducation(this.resume.getEducation().split(";"));

        if(this.resume.getExp() != null)
            this.resume.setArrExp(this.resume.getExp().split(";"));

        if(this.resume.getAddInfo() != null)
            this.resume.setArrAddInfo(this.resume.getAddInfo().split(";"));


        model.addAttribute("resume",this.resume);
        return "getResume";
    }

    @GetMapping(value="/downloadPDF")
    public String DownloadPDF(Model model) throws IOException, java.io.IOException {
//        File f = new File( "test.pdf");
//        var stream = HTMLtoString("showResume.html");
//        HtmlConverter.convertToPdf(stream, new FileOutputStream(f));
//        stream.close();
        model.addAttribute("resume",this.resume);
        generatePdfFromHtml(parseThymeleafTemplate());

        return "ending";
}


    public void generatePdfFromHtml(String html) throws java.io.IOException {
        OutputStream outputStream = new FileOutputStream(this.resume.getLastname()+ ".pdf");

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }
    private String parseThymeleafTemplate() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariable("resume", this.resume);

        return templateEngine.process("/templates/showResume", context);
    }

//    private InputStream HTMLtoString(String htmlFile)
//    {
//        try {
//            String val;
//            URL URL = new URL(
//                    "file:///C:\\Users\\Valerchik\\Desktop\\dzzz\\kurs\\src\\main\\resources\\templates\\"+htmlFile);
//            return URL.openStream();
//        }
//        catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        return null;
//    }
}
