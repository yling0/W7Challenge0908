package me.yling.w7challenge0908.controllers;

import me.yling.w7challenge0908.models.Education;
import me.yling.w7challenge0908.models.Person;
import me.yling.w7challenge0908.models.Role;
import me.yling.w7challenge0908.repositories.*;
import me.yling.w7challenge0908.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PerRepo perRepo;

    @Autowired
    EduRepo eduRepo;

    @Autowired
    ExpRepo expRepo;

    @Autowired
    SkiRepo skiRepo;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }

    @RequestMapping("/addroles")
    public @ResponseBody
    String addRoles()
    {
        Role seekerrole = new Role();
        seekerrole.setRole("SEEKERS");
        roleRepo.save(seekerrole);

        Role recruiterrole = new Role();
        recruiterrole.setRole("RECRUITERS");
        roleRepo.save(recruiterrole);

        return "Roles added";
    }

    @RequestMapping(value = "/registerseeker", method = RequestMethod.GET)
    public String showRegistrationseekerPage (Model model)
    {
        model.addAttribute("person", new Person());
        return "registrationseeker";
    }

    @RequestMapping(value = "/registerseeker", method = RequestMethod.POST)
    public String processRegistrationPage (@Valid @ModelAttribute("person") Person person, BindingResult result, Model model)
    {
        model.addAttribute("person", person);

        if (result.hasErrors())
        {
            return "registrationseeker";
        } else
        {
            userService.saveSeeker(person);
            model.addAttribute("message", "Seeker Account Successfully Created.");
        }

        return "index";
    }

    @RequestMapping(value = "/registerrecruiter", method = RequestMethod.GET)
    public String showRegistrationrecruiterPage (Model model)
    {
        model.addAttribute("person", new Person());
        return "registrationrecruiter";
    }

    @RequestMapping(value = "/registerrecruiter", method = RequestMethod.POST)
    public String processRegistrationrecruiterPage (@Valid @ModelAttribute("person") Person person, BindingResult result, Model model)
    {
        model.addAttribute("person", person);

        if (result.hasErrors())
        {
            return "registrationrecruiter";
        } else
        {
            userService.saveRecruiter(person);
            model.addAttribute("message", "Recruiter Account Successfully Created.");
        }

        return "index";
    }

    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }


    @RequestMapping("/welcome")
    public String secure( )
    {
        return "welcome";
    }

    //Show current loggin person's info
    @RequestMapping("/showper")
    public String showPerson (Principal principal, Model model)
    {
        String currentusername = principal.getName();
        model.addAttribute("curperson", perRepo.findByUsername(currentusername));
        return "showper";
    }

    //show resume of current loggin user
    @RequestMapping("/listresume")
    public String listResumeofOne (Principal principal, Model model)
    {
        String currentusername = principal.getName();
        model.addAttribute("person",perRepo.findByUsername(currentusername));
        return"listresume";
    }

    //update person's info
    @RequestMapping("/updateperson/{id}")
    public String updatePerson (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newPerson", perRepo.findOne(id));
        return "registerationseeker";
    }

    //delete person
    @RequestMapping("/deleteperson/{id}")
    public String delPerson(@PathVariable("id") long id, Model model)
    {
        perRepo.delete(id);
        return "redirect:/showper";
    }


    //add education to current login person
    @GetMapping("/addedu")
    public String addEdutoPer (Principal principal, Model model)
    {
        String currentusername = principal.getName();
        Education oneedu = new Education();
        oneedu.setPerson(perRepo.findByUsername(currentusername));
        model.addAttribute("addedumessage", "Add education here");
        model.addAttribute("newEdu", oneedu);

        return "addedutoper";
    }

    @PostMapping("/addedutoper")
    public String postEdutoPer (@Valid @ModelAttribute ("newEdu") Education oneedu, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "/addedutoper";
        }
        eduRepo.save(oneedu);
        return "resultedu";
    }

    //update specific person's education info
    @RequestMapping("/updateedu/{id}")
    public String updateEdu (@PathVariable("id") long id, Model model)
    {
        // Person = personRepo.findOne(educationID);
//        Education updateedu = educationRepo.findOne(educationID);
//        Person theperson = updateedu.getPerson();
//        model.addAttribute("newEdu", updateedu);
//        model.addAttribute("theperson",theperson);
//
//        System.out.println("------educationID:"+educationID);
//        System.out.println("------getPerson:"+updateedu.getPerson());

        model.addAttribute("newEdu", eduRepo.findOne(id));
        return "addedutoper";
    }

//    @RequestMapping("/deleteedu/{id}")
//    public String delEdu(@PathVariable("id") long id)
//    {
//        Education eduTodel = eduRepo.findOne(id);
//        Person perWithedu = eduTodel.getPerson();
//        perWithedu.delEdu(eduTodel);
//        eduTodel.setPerson(null);
//
//        System.out.println("******Deleting:"+eduTodel);
//        System.out.println("-------Education ID:-------: "+id);
//        educationRepo.delete(id);
////        model.addAttribute(personRepo.findOne(perId));
//
//        return "redirect:/showper";
//    }




}
