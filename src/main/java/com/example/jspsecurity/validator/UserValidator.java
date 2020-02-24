package com.example.jspsecurity.validator;

import com.example.jspsecurity.repository.MemberRepository;
import com.example.jspsecurity.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
  //  @Autowired
  //  private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return MemberDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MemberDto memberDto = (MemberDto) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (memberDto.getUsername().length() < 6 || memberDto.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        
        if (memberRepository.findByUsername(memberDto.getUsername()).isPresent()) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (memberDto.getPassword().length() < 8 || memberDto.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!memberDto.getPasswordConfirm().equals(memberDto.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }

    public void resetValidate(Object o, Errors errors) {
        MemberDto memberDto = (MemberDto) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (memberDto.getPassword().length() < 8 || memberDto.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!memberDto.getPasswordConfirm().equals(memberDto.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}