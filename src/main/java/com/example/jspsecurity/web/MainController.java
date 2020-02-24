package com.example.jspsecurity.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.example.jspsecurity.dto.MemberDto;
import com.example.jspsecurity.service.MemberService;
import com.example.jspsecurity.service.EmailService;
import com.example.jspsecurity.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class MainController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmailService emailService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }


    //회원가입 페이지
    @GetMapping("/signup")
    public String dispSignup(Model model){
        model.addAttribute("userForm", new MemberDto());
        return "signup";
    }

    //회원가입 처리
    @PostMapping("/signup")
    public String execSugnup(@ModelAttribute("userForm")MemberDto memberDto, BindingResult bindingResult) {
        userValidator.validate(memberDto, bindingResult);

        if(bindingResult.hasErrors()){
            return "signup";
        }
        
        memberService.joinMember(memberDto);
        
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    
    @GetMapping("/403error")
    public String error(){
        return "403error";
    }

    @GetMapping(value="/forgot")
    public String displayForgotPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping("/forgot")
    public ModelAndView processForgotPassword(ModelAndView modelAndView, @RequestParam("email")String email, HttpServletRequest request, RedirectAttributes redir){

        //lookup user in database by email
        List<MemberDto> member = memberService.findMemberByEmail(email);

        if(member.isEmpty())
        {
           modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        }else{

            // Generate random 36-character string token for reset password
            MemberDto memberDto = member.get(0);
            memberDto.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            memberService.saveMember(memberDto);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";

            //Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("bhsbhs235@gmail.com");
            passwordResetEmail.setTo(memberDto.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below: \n" + appUrl + "/reset?token="+memberDto.getResetToken());
            emailService.sendEmail(passwordResetEmail);

            // Add success message to view
            modelAndView.addObject("successMessage", "A password reset link has been sent to" + email);
        }
            modelAndView.setViewName("forgotPassword");
            return modelAndView;
    }

    @GetMapping("/reset")
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token){

        List<MemberDto> member = memberService.findMemberByResetToken(token);

        modelAndView.addObject("userForm", new MemberDto());

        if(member.isEmpty()){
            modelAndView.addObject("errorMessage","This is an invalid password reset link");
        }else{
            modelAndView.addObject("resetToken", token);
        }

        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }


    @PostMapping("/reset")
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String,String> requestParams, RedirectAttributes redir,
     @ModelAttribute("userForm")MemberDto memberDto, BindingResult bindingResult){
        
        // Find the member associated with the reset token
        List<MemberDto> members = memberService.findMemberByResetToken(requestParams.get("token"));

        // This should always be non-null but we check just in case
        if(members.isEmpty()){
            modelAndView.addObject("errorMessage", "This is an invalid password reset link.");
            modelAndView.setViewName("resetPassword");
        }else{

            //  memberDto.setUsername(member.getUsername());
            MemberDto member = members.get(0);
            userValidator.resetValidate(memberDto, bindingResult);

            if(bindingResult.hasErrors()){
                modelAndView.setViewName("resetPassword");
                return modelAndView;
            }

            //Set new password
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            member.setPassword(bCryptPasswordEncoder.encode(memberDto.getPassword()));

            //Set the reset token to null so it cannot be used again 
            member.setResetToken(null);

            //Save Member
            memberService.saveMember(member);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("successMessage", "You have successfully reset your password. You may now login.");

            modelAndView.setViewName("redirect:login");
        }

        return modelAndView;
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex){
        return new ModelAndView("redirect:login");
    }

}
    