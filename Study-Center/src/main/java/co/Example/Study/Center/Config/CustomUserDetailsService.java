package co.Example.Study.Center.Config;

import co.Example.Study.Center.Entity.Student;
import co.Example.Study.Center.UserRepo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(student.getEmail())
                .password(student.getPassword())
//                .roles(student.getRole())
                .build();
    }
}