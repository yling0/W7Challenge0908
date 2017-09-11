package me.yling.w7challenge0908.controllers;

import me.yling.w7challenge0908.models.*;
import me.yling.w7challenge0908.repositories.*;
import me.yling.w7challenge0908.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    JobRepo jobRepo;

    //index page, shown to everyone
    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }

    //load roles (seekers and recruiters) in database
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

    //register page for seeker
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

    //register page for recruiter
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

    //login page
    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    //welcome page, shown to logged in user
    @RequestMapping("/welcome")
    public String secure( )
    {
        return "welcome";
    }

    //search home page
    @RequestMapping("/searchhome")
    public String showSearchHome ()
    {
        return "searchhome";
    }

    //search person by name
    @GetMapping("/searchperbyname")
    public String searchPerbyName( )
    {
        return "searchperbyname";
    }

    //search person by name
    @PostMapping("/searchperbyname")
    public String searchPerbyName(@RequestParam("searchName") String searchName, Model model)
    {
        System.out.println(searchName);

        Iterable<Person> searchPerResult = perRepo.findAllByFName(searchName);
        model.addAttribute("searchFNameResult", searchPerResult);

        System.out.println("-----"+searchPerResult+"-----");

//        model.addAttribute("searchLNameResult", searchNameResult2);

//        System.out.println(perRepo.findAllByFName(searchName));
//        System.out.println(perRepo.findAllByLName(searchName));

        return "searchpernameresult";
    }

    //search person by school
    @GetMapping("/searchperbysch")
    public String searchPerbySch()
    {
        return "searchperbysch";
    }

    //search person by school
    @PostMapping("/searchperbysch")
    public String searchPerbySch(@RequestParam ("searchSch") String searchSch, Model model)
    {
        System.out.println(searchSch);

        Iterable<Education> searchSchResult = eduRepo.findAllByUni(searchSch);
        model.addAttribute("searchSchResult", searchSchResult);

        System.out.println(perRepo.findAllByEducationSet_uni(searchSch));

        model.addAttribute("searchPerResultbySch", perRepo.findAllByEducationSet_uni(searchSch));

        return "searchperschresult";
    }

    //search person by company
    @GetMapping("/searchperbycom")
    public String searchPerbyCom()
    {
        return "searchperbycom";
    }

    //search person by company
    @PostMapping("/searchperbycom")
    public String searchPerbyCom(@RequestParam ("searchCom") String searchCom, Model model)
    {
        System.out.println(searchCom);

        Iterable<Experience> searchComResult = expRepo.findAllByCompany(searchCom);
        model.addAttribute("searchComResult", searchComResult);

        System.out.println(perRepo.findAllByExperienceSet_company(searchCom));

        model.addAttribute("searchPerResultbyCom", perRepo.findAllByExperienceSet_company(searchCom));

        return "searchpercomresult";
    }

    //search job by title
    @GetMapping("/searchjobbytit")
    public String searchJobbyTit()
    {
        return "searchjobbytit";
    }

    //search job by tit
    @PostMapping("/searchjobbytit")
    public String searchJobbyTit(@RequestParam ("searchTit") String searchTit, Model model)
    {
        System.out.println(searchTit);

        Iterable<Job> searchTitResult = jobRepo.findAllByJobTitleContains(searchTit);
        model.addAttribute("searchTitResult", searchTitResult);

        System.out.println(jobRepo.findAllByJobTitleContains(searchTit));

        return "searchjobtitresult";
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
    @RequestMapping("/updateperson")
    public String updatePerson (Principal principal, Model model)
    {
        model.addAttribute("newPerson", perRepo.findByUsername(principal.getName()));
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
        model.addAttribute("newEdu", eduRepo.findOne(id));
        return "addedutoper";
    }

    @RequestMapping("/deleteedu/{id}")
    public String delEdu(@PathVariable("id") long id)
    {
        Education eduTodel = eduRepo.findOne(id);
        Person perWithedu = eduTodel.getPerson();
        perWithedu.delEdu(eduTodel);
        eduTodel.setPerson(null);

        System.out.println("******Deleting:"+eduTodel);
        System.out.println("-------Education ID:-------: "+id);
        eduRepo.delete(id);
//        model.addAttribute(personRepo.findOne(perId));

        return "redirect:/listresume";
    }

    //add experience to current login person
    @GetMapping("/addexp")
    public String addExptoPer (Principal principal, Model model)
    {
        Experience oneexp = new Experience();
        oneexp.setPerson(perRepo.findByUsername(principal.getName()));
        model.addAttribute("addexpmessage", "Add experience here");
        model.addAttribute("newExp", oneexp);

        return "addexptoper";
    }

    @PostMapping("/addexptoper")
    public String postExptoPer (@Valid @ModelAttribute ("newExp") Experience oneexp, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "/addexptoper";
        }
        expRepo.save(oneexp);
        return "resultexp";
    }

    //update specific person's experience info
    @RequestMapping("/updateexp/{id}")
    public String updateExp (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newExp", expRepo.findOne(id));
        return "addexptoper";
    }

    @RequestMapping("/deleteexp/{id}")
    public String delExp(@PathVariable("id") long id)
    {
        Experience expTodel = expRepo.findOne(id);
        Person perWithexp = expTodel.getPerson();
        perWithexp.delExp(expTodel);
        expTodel.setPerson(null);
        expRepo.delete(id);
        return "redirect:/listresume";
    }

    //add skill to current login person
    @GetMapping("/addski")
    public String addSkitoPer (Principal principal, Model model)
    {
        Skill oneski = new Skill();
        oneski.setPerson(perRepo.findByUsername(principal.getName()));
        model.addAttribute("addskimessage", "Add skill here");
        model.addAttribute("newSki", oneski);

        return "addskitoper";
    }

    @PostMapping("/addskitoper")
    public String postSkitoPer (@Valid @ModelAttribute ("newSki") Skill oneski, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "/addskitoper";
        }
        skiRepo.save(oneski);
        return "resultski";
    }

    //update specific person's experience info
    @RequestMapping("/updateski/{id}")
    public String updateSki (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newSki", skiRepo.findOne(id));
        return "addskitoper";
    }

    @RequestMapping("/deleteski/{id}")
    public String delSki(@PathVariable("id") long id)
    {
        Skill skiTodel = skiRepo.findOne(id);
        Person perWithski = skiTodel.getPerson();
        perWithski.delSki(skiTodel);
        skiTodel.setPerson(null);
        skiRepo.delete(id);
        return "redirect:/listresume";
    }

    //for logged in recruiter to post job
    @GetMapping("/postjob")
    public String addJobtoPer (Principal principal, Model model)
    {
        String currentusername = principal.getName();
        Job onejob = new Job();
        onejob.setPerson(perRepo.findByUsername(currentusername));
        model.addAttribute("postjobmessage", "Post job here");
        model.addAttribute("newJob", onejob);

        return "postjobtoper";
    }

    @PostMapping("/postjobtoper")
    public String postJobtoPer (@Valid @ModelAttribute ("newJob") Job onejob, BindingResult result)
    {
        if (result.hasErrors())
        {
            return "/postjobtoper";
        }
        jobRepo.save(onejob);
        return "resultjob";
    }

    //update job's info
    @RequestMapping("/updatejob/{id}")
    public String updateJob (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newJob", jobRepo.findOne(id));
        return "postjobtoper";
    }

    //delete job
    @RequestMapping("/deletejob/{id}")
    public String delJob(@PathVariable("id") long id)
    {
        Job jobTodel = jobRepo.findOne(id);
        Person perWithjob = jobTodel.getPerson();
        perWithjob.delJob(jobTodel);
        jobTodel.setPerson(null);

        System.out.println("******Deleting:"+jobTodel);
        System.out.println("-------JOB ID:-------: "+id);
        jobRepo.delete(id);
//        model.addAttribute(personRepo.findOne(perId));
        return "redirect:/listjob";
    }

    //Show current loggin person's info
    @RequestMapping("/showpostjob")
    public String showPostJob (Principal principal, Model model)
    {
        String currentusername = principal.getName();
        model.addAttribute("recruiter", perRepo.findByUsername(currentusername));
        return "showpostjob";
    }

    //add skill to current posted job
    @GetMapping("/addskitojob/{id}")
    public String addSkitoJob (@PathVariable("id") long jobId, Model model)
    {

        Job postjob = jobRepo.findOne(new Long(jobId));
        model.addAttribute("job", postjob);

        System.out.println("+++++++jobID: "+jobId+"+++++++");
        model.addAttribute("skillsforjob", skiRepo.findAll());

        System.out.println(postjob);
        return "addskitojob";
    }

    @PostMapping("/addskitojob/{jobid}")
    public String postSkitoJob (@PathVariable("jobid") long id,
                                @RequestParam("job") String jobId,
                                @ModelAttribute("theSkill")Skill skill, Model model)
    {

        Skill jobskill = new Skill();
//        Skill s = skiRepo.findOne(new Long(skiId));
//        s.addJob(jobRepo.findOne(new Long(jobId)));
//        skiRepo.save(s);
//
//        model.addAttribute("alljob", jobRepo.findAll());
//        model.addAttribute("allskill", skiRepo.findAll());


        System.out.println(jobskill);

        Job postjob2 = jobRepo.findOne(new Long(id));
        System.out.println(id);
        System.out.println("postjob2: ---"+postjob2);

        postjob2.addSkitoJob(skiRepo.findOne(new Long(jobId)));
        System.out.println(jobId);
        jobRepo.save(postjob2);

        System.out.println("skiRepo.findOne(jobID):+++"+skiRepo.findOne(new Long(id)));
        System.out.println(postjob2.getJobskillSet());

        model.addAttribute("findjob",postjob2);
        model.addAttribute("findskill",postjob2.getJobskillSet());

        return "jobdetail";
    }

    @GetMapping("/notification")
    public String jobNotification(Principal principal, Skill skinot, Model model)
    {
        Iterable<Job> jobs = jobRepo.findAll();
        Set<Job> jobnot = new HashSet<>();
        for (Job job : jobs)
        {
            for (Skill skill : job.getJobskillSet())
            {
                Person person = perRepo.findAllByUsername(principal.getName());
                for (Skill skill1 : person.getSkillSet())
                {
                    if (skill.getSkiName().equals(skill1.getSkiName()))
                    {
                        System.out.println("A Job matching your skill");
                        System.out.println("*****Job title: "+job.getJobTitle());
                        jobnot.add(job);
                    }
                    else
                        {
                            System.out.println("No machting job!");
                        }
                }
            }
        }
        model.addAttribute("joblist",jobnot);
        return "notification";
    }

}
